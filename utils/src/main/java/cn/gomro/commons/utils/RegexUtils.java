package cn.gomro.commons.utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author AdamYao
 */
public class RegexUtils {
    private RegexUtils() {
    }

    public interface Type {
        //------------------    正则定义区    ------------------//
        String IS_LINK = "^http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?$";
        String IS_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        String IS_IDCARD = "^\\d{17}[\\d|X]|\\d{15}$";//身份证
        String IS_POST = "^\\d{6}$";//邮政编码
        String IS_NUMBER = "^([+-]?)\\d*\\.?\\d+$";//数字
        String IS_CHAR = "^[A-Za-z]+$";//字母
        String IS_UPPER_CHAR = "^[A-Za-z]+$";//大写字母
        String IS_LOWER_CHAR = "^[a-z]+$";//小写字母
        String IS_NUMBER_OR_CHAR = "^[A-Za-z0-9]+$";//字母和数字
        String IS_MONTH = "^(0?[1-9]|1[0-2])$";//一年的12个月
        String IS_DATE_OF_MONTH = "^((0?[1-9])|((1|2)[0-9])|30|31)$";//一个月的31天
        String IS_DOUBLE = "^([+-]?)\\d*\\.\\d+$";//浮点数
        String IS_DOUBLE_POSITIVE = "^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*$";//正浮点数
        String IS_DOUBLE_NEGATIVE = "^-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*)$";//负浮点数
        String NOT_DOUBLE_NEGATIVE = "^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*|0?.0+|0$";//非负浮点数
        String NOT_DOUBLE_POSITIVE = "^(-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*))|0?.0+|0$";//非正浮点数
        String IS_INTEGER = "^-?[1-9]\\d*$";//整数
        String IS_INTEGER_POSITIVE = "^[1-9]\\d*$";//正整数
        String IS_INTEGER_NEGATIVE = "^-[1-9]\\d*$";//负整数
        String IS_NUMBER_POSITIVE = "^[+]{0,1}(\\d+)$|^[+]{0,1}(\\d+\\.\\d+)$";//正数
        String IS_NUMBER_NEGATIVE = "^[-]{0,1}(\\d+)$|^[-]{0,1}(\\d+\\.\\d+)$";//负数
        String IS_ACSII = "^[\\x00-\\xFF]+$";//ACSII字符
        String IS_CHINESE = "^[\\u4e00-\\u9fa5]+$";//中文
        String IS_COLOR = "^[a-fA-F0-9]{6}$";//颜色
        String IS_DATE = "^\\d{4}(\\-|\\/|\\.)\\d{1,2}\\1\\d{1,2}$";//日期
        String IS_IP = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)$";//IP
        String IS_MOBILE = "^(1)[0-9]{10}$";//手机 以1开头 11位数字即可
        String NOT_NULL = "^\\S+$";//非空
        String IS_USERNAME = "^[A-Za-z]+[A-Za-z0-9_\\-.]{3,}$";//用户名
        String IS_FRIENDLY_URI = "^[a-z/#&?]+[a-z0-9\\-/#&?]*$";// 友好的 URL 内容
        String IS_FRIENDLY_URI_NAME = "^[a-z]+[a-z0-9\\-]*$";// 友好的 URL NAME 内容
        // 强密码最短8位，最长16位 {8,16} 必须包含 1个数字 1个小写字母 1个大写字母
        String IS_PASSWD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[^\\t\\n]{8,16}$";
        String IS_IMAGE = "(.*)\\.(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga)$";//图片
        String IS_QQ = "^[1-9]*[1-9][0-9]*$";//QQ号
        String IS_ZIP_FILE = "(.*)\\.(rar|zip|7zip|tgz)$";//压缩文件
        String IS_TELEPHONE = "^[0-9\\-()（）]{7,18}$";//电话号码
        String IS_COMPANY_NAME = "^[A-Za-z0-9_()（）\\-\\u4e00-\\u9fa5]+$";//单位名
        String IS_PRICE = "^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$";//金额验证小数点两位

        String ONLY_NUMBER_OR_EMPTY = "^[\\d,]+$";// 仅限数字及逗号
        String IS_NEGATIVE_NUMBER= "([0-9]\\d*\\.?\\d*)|(0\\.\\d*)";//非负数
    }

    //------------------    验证方法区        ----------------//

    /**
     * 非空验证频率最高直接调用
     */
    public static boolean notNull(String str) {
        return org.apache.commons.lang3.StringUtils.isNotBlank(str);
    }

    /**
     * 匹配是否符合正则表达式pattern 匹配返回true
     */
    public static boolean check(String pattern, String str) {
        boolean result = false;
        if (StringUtils.isNotBlank(str)) {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(str);
            result = m.matches();
        }
        return result;
    }

}
