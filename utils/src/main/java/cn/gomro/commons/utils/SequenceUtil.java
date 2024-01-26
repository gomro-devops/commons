package cn.gomro.commons.utils;


import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * @Author Ivan.Zhao
 * <p>
 * 分布式ID生成器 （基于Twitter的Snowflake）
 * <p>
 * |<------------------------------ 64位 Java Long 长度 ----------------------------->|
 * 0 -- 0000000000 0000000000 0000000000 0000000000 0 -- 00000 -- 00000 -- 000000000000
 * <p>
 * 首位：未使用，默认为0，作为long的符号
 * 41位：毫秒时间戳
 * 5位：数据中心标识（由注册中心产生或Redis原子性产生）
 * 5位：机器标识，线程标识
 * 12位：序号标识（毫秒内的计数器）
 */
public class SequenceUtil {

    private static final long Epoch = 1539754886168L; // 起始时间戳，用于计算当前时间偏移量，选择离当前最近的时间，确定后不可更改
    private static final long WorkerIdBits = 5L; // 机器标识位数
    private static final long DataCenterIdBits = 5L; // 数据中心标识位数
    private static final long SequenceBits = 12L; // 序号标识位数
    private static final long WorkerIdShift = SequenceBits; // 机器ID左偏12位
    private static final long DataCenterIdShift = SequenceBits + WorkerIdBits; // 数据中心ID左偏17位
    private static final long TimestampShift = SequenceBits + WorkerIdBits + DataCenterIdBits; // 序号左偏22位
    private static final long SequenceMask = ~(-1L << SequenceBits); // 防溢出，保证位与运算结果范围为0-4095

    public static final long MaxWorkerId = ~(-1L << WorkerIdBits); // 机器最大ID
    public static final long MaxDataCenterId = ~(-1L << DataCenterIdBits); // 数据中心最大ID

    private static long LastTimestamp = -1L; // 上次产生ID的时间戳
    private long sequence = 0L; // 0:并发控制
    private final long workerId;
    private final long dataCenterId;

    public SequenceUtil(long dataCenterId, long workerId) {

        if (workerId > MaxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", MaxWorkerId));
        }
        if (dataCenterId > MaxDataCenterId || dataCenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", MaxDataCenterId));
        }

        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }

    /**
     * 获取序列
     *
     * @return 生成的序列
     */
    public synchronized long nextId() {

        long timestamp = timeGen();

        if (timestamp < LastTimestamp) {
            throw new RuntimeException(String.format("clock moved backwards. refusing to generate id for %d milliseconds", LastTimestamp - timestamp));
        }
        if (LastTimestamp == timestamp) {
            sequence = (sequence + 1) & SequenceMask; // 当前毫秒内，则+1
            if (sequence == 0) timestamp = tilNextMillis(LastTimestamp); // 当前毫秒内计数满了，则等待下一秒
        } else {
            sequence = 0L;
        }

        LastTimestamp = timestamp;

        /*
         * ID偏移组合生成最终的ID，并返回ID
         *
         * 1.左移运算是为了将数值移动到对应的段(41、5、5，12那段因为本来就在最右，因此不用左移)
         * 2.然后对每个左移后的值(la、lb、lc、sequence)做位或运算，是为了把各个短的数据合并起来，合并成一个二进制数
         * 3.最后转换成10进制，就是最终生成的id
         */
        return ((timestamp - Epoch) << TimestampShift) | (dataCenterId << DataCenterIdShift) | (workerId << WorkerIdShift) | sequence;
    }

    /**
     * 保证返回的毫秒数在参数之后（阻塞到下一个毫秒，直到获得新的时间戳）
     *
     * @param lastTimestamp 最后时间戳
     * @return 下一个毫秒数
     */
    private long tilNextMillis(final long lastTimestamp) {

        long timestamp = timeGen();

        while (timestamp <= lastTimestamp) {

            timestamp = timeGen();
        }

        return timestamp;
    }

    /**
     * 获得系统当前毫秒数
     *
     * @return 当前毫秒数
     */
    private long timeGen() {

        // return isClock ? Clock.now() : System.currentTimeMillis(); // 解决高并发下获取时间戳的性能问题
        return System.currentTimeMillis();
    }

    /**
     * 获取机器标识
     *
     * @param dataCenterId 数据中心ID
     * @param maxWorkerId  最大允许的机器ID
     * @return 机器ID
     */
    public static long getWorkerId(long dataCenterId, long maxWorkerId) {

        StringBuffer jvmPid = new StringBuffer();

        jvmPid.append(dataCenterId);
        String name = ManagementFactory.getRuntimeMXBean().getName();
        if (!name.isEmpty()) {
            jvmPid.append(name.split("@")[0]);
        }
        return (jvmPid.toString().hashCode() & 0xFFFF) % (maxWorkerId + 1); // MAC + PID 的 hashcode 获取16个低位
    }

    /**
     * 获取数据中心标识
     *
     * @param maxDatCenterId 最大允许的数据中心ID
     * @return 数据中心ID
     */
    public static long getDataCenterId(long maxDatCenterId) throws UnknownHostException, SocketException {

        long id;
        InetAddress ip = InetAddress.getLocalHost();
        NetworkInterface network = NetworkInterface.getByInetAddress(ip);
        if (network == null) {
            id = 1L;
        } else {
            byte[] mac = network.getHardwareAddress();
            id = ((0x000000FF & (long) mac[mac.length - 1]) | (0x0000FF00 & (((long) mac[mac.length - 2]) << 8))) >> 6;
            id = id % (maxDatCenterId + 1);
        }

        return id;
    }
}
