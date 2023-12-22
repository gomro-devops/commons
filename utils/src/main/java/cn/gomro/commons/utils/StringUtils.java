package cn.gomro.commons.utils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringUtils {

    /**
     * 转小写字母
     */
    public static String lowerCase(final String str) {
        return org.apache.commons.lang3.StringUtils.lowerCase(str);
    }

    /**
     * @author Adam 2023/12/1 14:42 说明: 判断两个字符串是否相等
     */
    public static boolean equalsIgnoreCase(CharSequence str1, CharSequence str2) {
        return org.apache.commons.lang3.StringUtils.equalsIgnoreCase(str1, str2);
    }

    /**
     * 判断是否为空白字符
     */
    public static boolean isNotBlank(final CharSequence cs) {
        return org.apache.commons.lang3.StringUtils.isNotBlank(cs);
    }

    /**
     * 逗号分割转Set
     * a,b,c,d    ==>  [a,b,c,d]
     * a,,c,d     ==>  [a,c,d]
     * ,a,,c,d,    ==>  [a,c,d]
     */
    public static Set<String> commaDelimitedTrimEmptySet(String str) {
        Set<String> strings = org.springframework.util.StringUtils.commaDelimitedListToSet(str);
        return strings.stream().filter(StringUtils::isNotBlank).collect(Collectors.toSet());
    }

    /**
     * 逗号分割转Set
     * a,b,c,d    ==>  [a,b,c,d]
     * a,,c,d     ==>  [a,c,d]
     * ,a,,c,d,    ==>  [a,c,d]
     */
    public static LinkedHashSet<String> commaDelimitedTrimEmptyLinkedHashSet(String str) {

        String[] strings = org.springframework.util.StringUtils.commaDelimitedListToStringArray(str);
        List<String> collect = Arrays.stream(strings).filter(StringUtils::isNotBlank).collect(Collectors.toList());
        return new LinkedHashSet<>(collect);
    }

    /**
     * 逗号分割转List
     * 1,2,3,4    ==>  [1,2,3,4]
     * 1,,3,4     ==>  [1,3,4]
     * ,1,,3,4,   ==>  [1,3,4]
     */
    public static List<String> commaDelimitedTrimEmptyList(String str) {
        String[] strings = org.springframework.util.StringUtils.commaDelimitedListToStringArray(str);
        return Arrays.stream(strings).filter(StringUtils::isNotBlank).collect(Collectors.toList());
    }

    public static String[] split(String str, String delimiter) {
        return org.apache.commons.lang3.StringUtils.split(str, delimiter);
    }

    /**
     * 逗号分割转List<Integer>
     * 1,2,3,4    ==>  [1,2,3,4]
     * 1,,3,4     ==>  [1,3,4]
     * ,1,,3,4,   ==>  [1,3,4]
     * ,abc,      ==>  []
     */
    public static List<Integer> commaDelimitedTrimEmptyListInteger(String str) {

        if (RegexUtils.check(RegexUtils.Type.ONLY_NUMBER_OR_EMPTY, str)) {
            // 不为空时，必须为逗号分割的数字才能继续
            String id = commaDelimitedTrimEmptyString(str);
            if (StringUtils.isNotBlank(id)) {
                return Arrays.stream(id.split(","))
                        .mapToInt(Integer::valueOf).boxed().collect(Collectors.toList());
            }
        }
        return Collections.emptyList();
    }

    /**
     * 逗号分割转List<Long>
     * 1,2,3,4    ==>  [1,2,3,4]
     * 1,,3,4     ==>  [1,3,4]
     * ,1,,3,4,   ==>  [1,3,4]
     * abc,       ==>  []
     */
    public static List<Long> commaDelimitedTrimEmptyListLong(String str) {

        if (RegexUtils.check(RegexUtils.Type.ONLY_NUMBER_OR_EMPTY, str)) {
            // 不为空时，必须为逗号分割的数字才能继续
            String id = commaDelimitedTrimEmptyString(str);

            if (StringUtils.isNotBlank(id)) {
                return Arrays.stream(id.split(","))
                        .mapToLong(Long::valueOf).boxed().collect(Collectors.toList());
            }
        }

        return Collections.emptyList();
    }

    /**
     * 逗号分割去空去重
     * <p>
     * ,           ==>  ""
     * a,b,c,d     ==>  a,b,c,d
     * a,,c,d      ==>  a,c,d
     * ,a,,c,d,    ==>  a,c,d
     * ,a,c,c,d,   ==>  a,c,d
     */
    public static String commaDelimitedTrimEmptyString(String str) {
        Set<String> strings = org.springframework.util.StringUtils.commaDelimitedListToSet(str);
        return collectionToCommaDistinctString(strings);
    }

    /**
     * 逗号分割，每个元素前拼接单引号
     * 无序
     * <p>
     * 1,2,3,4     ==>  '1','2','3','4'
     */
    public static String stringToCommaDistinctString(String str) {
        Set<String> strings = org.springframework.util.StringUtils.commaDelimitedListToSet(str);
        return org.springframework.util.StringUtils.collectionToDelimitedString(strings, ",", "'", "'");
    }

    /**
     * 逗号分割去空去重
     * 逗号分割，每个元素前拼接单引号
     * null        ==>  ''
     * ''          ==>  ''
     * 1           ==>  '1'
     * 1,ab        ==>  '1','ab'
     * 1,2,3,4,,   ==>  '1','2','3','4'
     *
     * @param str
     * @return
     */
    public static String toSqlInString(String str) {
        if (StringUtils.isBlank(str)) {
            return "";
        }
        str = StringUtils.escapeSql(str);
        String str1 = StringUtils.commaDelimitedTrimEmptyString(str);
        return StringUtils.stringToCommaDistinctString(str1);
    }

    public static String escapeSql(String str) {
        return str == null ? null : org.apache.commons.lang3.StringUtils.replace(str, "'", "''");
    }

    /**
     * 集合字符串转去重后的文本
     * 乱序 去重
     * [1,2,3,4]     ==>  1,2,3,4
     * [1,,3,4]      ==>  1,3,4
     * [,1,,3,4,]    ==>  1,3,4
     * [,1,3,3,4,]   ==>  1,3,4
     */
    public static String collectionToCommaDistinctString(Collection<?> coll) {
        if (coll == null || coll.isEmpty()) {
            return "";
        }
        coll = coll.stream().filter(c -> {
            if (c instanceof CharSequence) {
                return StringUtils.isNotBlank((CharSequence) c);
            }
            return c != null;
        }).collect(Collectors.toSet());
        return org.springframework.util.StringUtils.collectionToCommaDelimitedString(coll);
    }

    /**
     * 集合字符串转文本
     * 有序 不去重
     * [1,2,3,4]     ==>  1,2,3,4
     * [1,,3,4]      ==>  1,3,4
     * [,1,,3,4,]    ==>  1,3,4
     * [,1,3,3,4,]   ==>  1,3,4
     */
    public static String collectionToCommaString(Collection<?> coll) {
        if (coll == null || coll.isEmpty()) {
            return "";
        }
        coll = coll.stream().filter(c -> {
            if (c instanceof CharSequence) {
                return StringUtils.isNotBlank((CharSequence) c);
            }
            return c != null;
        }).collect(Collectors.toList());
        return org.springframework.util.StringUtils.collectionToCommaDelimitedString(coll);
    }

    public static boolean startsWithIgnoreCase(String str, String prefix) {
        return org.springframework.util.StringUtils.startsWithIgnoreCase(str, prefix);
    }

    public static boolean endsWithIgnoreCase(String str, String prefix) {
        return org.springframework.util.StringUtils.endsWithIgnoreCase(str, prefix);
    }

    /**
     * 在开头和结尾处附加逗号
     */
    public static String appendCommaAtStartAndEnd(String str) {
        if (str == null) {
            return ",";
        }
        boolean startsWith = StringUtils.startsWithIgnoreCase(str, ",");
        if (!startsWith) {
            str = ",".concat(str);
        }
        boolean endWith = StringUtils.endsWithIgnoreCase(str, ",");
        if (!endWith) {
            str = str.concat(",");
        }
        return str;
    }

    public static String strip(String str, final String stripChars) {
        return org.apache.commons.lang3.StringUtils.strip(str, stripChars);
    }

    /**
     * 分号分割转List<Long>
     * 1;2;3;4    ==>  [1,2,3,4]
     * 1;;3;4     ==>  [1,3,4]
     * ;1;;3,;4;   ==>  [1,3,4]
     * abc;       ==>  []
     */
    public static List<Long> semicolonDelimitedTrimEmptyListLong(String str) {

        if (StringUtils.isNotBlank(str)) {
            // 不为空时，必须为逗号分割的数字才能继续
            String id = commaDelimitedTrimEmptyString(str);

            if (StringUtils.isNotBlank(id)) {
                return Arrays.stream(id.split(";"))
                        .mapToLong(Long::valueOf).boxed().collect(Collectors.toList());
            }
        }

        return Collections.emptyList();
    }

    /**
     * 将驼峰式命名的字符串转换为下划线大写方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。</br>
     * 例如：HelloWorld->HELLO_WORLD
     *
     * @param name 转换前的驼峰式命名的字符串
     * @return 转换后下划线大写方式命名的字符串
     */
    public static String underscoreName(String name) {
        StringBuilder result = new StringBuilder();
        if (name != null && name.length() > 0) {
            // 将第一个字符处理成大写
            result.append(name.substring(0, 1).toUpperCase());
            // 循环处理其余字符
            for (int i = 1; i < name.length(); i++) {
                String s = name.substring(i, i + 1);
                // 在大写字母前添加下划线
                if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {
                    result.append("_");
                }
                // 其他字符直接转成大写
                result.append(s.toUpperCase());
            }
        }
        return result.toString();
    }

    /**
     * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。</br>
     * 例如：HELLO_WORLD->HelloWorld
     *
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String camelName(String name) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (name == null || name.isEmpty()) {
            // 没必要转换
            return "";
        } else if (!name.contains("_")) {
            // 不含下划线，仅将首字母小写
            return name.substring(0, 1).toLowerCase() + name.substring(1);
        }
        // 用下划线将原始字符串分割
        String[] camels = name.split("_");
        for (String camel : camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty()) {
                continue;
            }
            // 处理真正的驼峰片段
            if (result.length() == 0) {
                // 第一个驼峰片段，全部字母都小写
                result.append(camel.toLowerCase());
            } else {
                // 其他的驼峰片段，首字母大写
                result.append(camel.substring(0, 1).toUpperCase());
                result.append(camel.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

    /**
     * 下划线转驼峰法(默认小驼峰)
     *
     * @param line       源字符串
     * @param smallCamel 大小驼峰,是否为小驼峰(驼峰，第一个字符是大写还是小写)
     * @return 转换后的字符串
     */
    public static String underline2Camel(String line, boolean... smallCamel) {
        if (line == null || "".equals(line)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile("([A-Za-z\\d]+)(_)?");
        Matcher matcher = pattern.matcher(line);
        //匹配正则表达式
        while (matcher.find()) {
            String word = matcher.group();
            //当是true 或则是空的情况
            if ((smallCamel.length == 0 || smallCamel[0]) && matcher.start() == 0) {
                sb.append(Character.toLowerCase(word.charAt(0)));
            } else {
                sb.append(Character.toUpperCase(word.charAt(0)));
            }

            int index = word.lastIndexOf('_');
            if (index > 0) {
                sb.append(word.substring(1, index).toLowerCase());
            } else {
                sb.append(word.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }

    public static boolean contains(CharSequence str, CharSequence searchStr) {
        return org.apache.commons.lang3.StringUtils.containsIgnoreCase(str, searchStr);
    }

    public static String join(Iterable<?> iterable, String s) {
        return org.apache.commons.lang3.StringUtils.join(iterable, s);
    }

    public static String trim(final String str) {
        return str == null ? null : str.trim();
    }

    //########
    //## ivan add


    public enum CaseMode {
        Capitalize, // 首字母大写
        Lower, // 小写
        Upper, // 大写
    }

    public static final String UTF8 = "UTF-8";
    public static final String GB2312 = "GB2312";
    public static final String GBK = "GBK";

    public static final String PatternUsername = "^[a-zA-Z0-9]{1}([a-zA-Z0-9]|[_]){2,18}[a-zA-Z0-9]{1}$"; // 账号
    public static final String PatternEmail = "^^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\" +
            ".[A-Za-z0-9]+$"; // 邮箱
    public static final String PatternPhone = "(^(\\d{2,4}[-_－—]?)?\\d{3,8}([-_－—]?\\d{3,8})?([-_－—]?\\d{1,7})?$)|" +
            "(^0?1[35]\\d{9}$)"; // 电话
    public static final String PatternMobile = "^((13[0-9])|(14[579])|(15([0-3]|[5-9]))|(16[56])|(17[0-8])|(18[0-9])|(19[1589]))\\d{8}$"; // 手机
    public static final String PatternIp = "((?:(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d)\\.){3}" +
            "(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d))"; // IP
    public static final String PatternUrl = "^(http|www|ftp|)?(:\\)?(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/" +
            "(\\w+(-\\w+)*))*(\\.?(\\w)*)(\\?)?(((\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*" +
            "(\\w*=)*(\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*)*(\\w*)*)$"; // 网址
    public static final String PatternIdCard = "((11|12|13|14|15|21|22|23|31|32|33|34|35|36|37|41|42|43|44|45|46|50" +
            "|51|52|53|54|61|62|63|64|65)[0-9]{4})(([1|2][0-9]{3}[0|1][0-9][0-3][0-9][0-9]{3}[Xx0-9])|" +
            "([0-9]{2}[0|1][0-9][0-3][0-9][0-9]{3}))"; // 身份证
    public static final String PatternAge = "^(?:[1-9][0-9]?|1[01][0-9]|120)$"; // 年龄 0-120岁
    public static final String PatternPostal = "[1-9]\\d{5}(?!\\d)"; // 国内邮编
    public static final String PatternSmsAuthCode = "^\\d{6}$"; // 短信验证码
    public static final String PatternInt = "^([+-]?)\\d*\\.?\\d+$"; // 整数
    public static final String PatternIntPositive = "^[1-9]\\d*$"; // 正整数
    public static final String PatternIntNegative = "^-[1-9]\\d*|0$"; // 负整数
    public static final String PatternDouble = "^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$"; // 双精度
    public static final String PatternDoublePositive = "^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0$"; // 正双精度
    public static final String PatternDoubleNegative = "^(-([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*))|0?\\.0+|0$"; // 负双精度
    public static final String PatternEnglish = "^[A-Za-z]+$"; // 英文字符
    public static final String PatternEnglishNumeric = "^\\w+$"; // 英文字符、数字
    public static final String PatternEnglishNumericUnderline = "^\\w+$"; // 英文字母、数字、下划线
    public static final String PatternChinese = "^[/u0391-/uffe5]+$"; // 中文字符
    public static final String PatternChineseEnglishNumeric = "^[a-zA-Z0-9/u4e00-/u9fa5]+$"; // 中文字符、英文字符、数字
    public static final String PatternSpecial = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
    // 特殊字符
    public static final String PatternDate = "[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}"; // 日期
    // YYYY-MM-DD YYYY/MM/DD YYYY_MM_DD YYYYMMDD YYYY.MM.DD
    public static final String PatternDatetime = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|" +
            "(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|" +
            "([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|" +
            "([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|((" +
            "(0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8])))" +
            ")))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$"; // 日期时间 yyyy-MM-dd
    // HH:mm:ss
    public final static String PatternScientificA = "^[-+]?(\\d+(\\.\\d*)?|\\.\\d+)([eE]([-+]?([012]?\\d{1," +
            "2}|30[0-7])|-3([01]?[4-9]|[012]?[0-3])))$"; // 1.匹配科学计数 e或者E必须出现有且只有一次 不含Dd
    public final static String PatternScientificB = "^[-+]?(\\d+(\\.\\d*)?|\\.\\d+)([eE]([-+]?([012]?\\d{1," +
            "2}|30[0-7])|-3([01]?[4-9]|[012]?[0-3])))[dD]?$"; // 2.匹配科学计数 e或者E必须出现有且只有一次 结尾包含Dd
    public final static String PatternScientificC = "^[-+]?(\\d+(\\.\\d*)?|\\.\\d+)([eE]([-+]?([012]?\\d{1," +
            "2}|30[0-7])|-3([01]?[4-9]|[012]?[0-3])))?[dD]?$"; // 3.匹配科学计数 是否含有E或者e都通过 结尾含有Dd的也通过（针对Double类型）
    public final static String PatternScientificD = "^[-+]?(\\d+(\\.\\d*)?|\\.\\d+)([eE]([-+]?([012]?\\d{1," +
            "2}|30[0-7])|-3([01]?[4-9]|[012]?[0-3])))?$"; // 4.匹配科学计数 是否含有E或者e都通过 结尾不含Dd

    //*****************************************************************************************************************

    /**
     * 封装双引号
     *
     * @param val
     * @return
     */
    public static String escapeQuotes(String val) {

        return val == null ? null : org.apache.commons.lang3.StringUtils.replace(val, "\"", "“");
    }

    //*****************************************************************************************************************

    /**
     * 字符串长度
     *
     * @param val
     * @return
     */
    public static int len(String val) {

        return isBlank(val) ? 0 : val.trim().length();
    }

    /**
     * 字符串最大长度
     *
     * @param val
     * @param len
     * @return
     */
    public static boolean max(String val, int len) {

        return len(val) > len;
    }

    /**
     * 字符串长度区间
     *
     * @param val
     * @param minLen
     * @param maxLen
     * @return
     */
    public static boolean between(String val, int minLen, int maxLen) {

        return minLen <= len(val) && len(val) <= maxLen;
    }

    /**
     * 过滤特殊字符串
     *
     * @param val
     * @return
     */
    public static String filterSpecial(String val) {

        return Pattern.compile(PatternSpecial).matcher(val).replaceAll("").trim();
    }

    /**
     * 判断字符串是否NULL或空
     *
     * @param val
     * @return
     */
    public static boolean isBlank(String val) {

        return org.apache.commons.lang3.StringUtils.isBlank(val);
    }

    /**
     * 判断字符串是否为NULL
     *
     * @param val
     * @return
     */
    public static boolean isNull(String val) {

        return null == val || val.trim().length() <= 0;
    }

    /**
     * 判断字符串非NULL
     *
     * @param val
     * @return
     */
    public static boolean notNull(String val) {

        return !isNull(val);
    }

    /**
     * NULL转空
     *
     * @param val
     * @return
     */
    public static String nullToStr(String val) {

        return isNull(val) ? "" : val;
    }

    /**
     * NULL转默认值
     *
     * @param val
     * @param def
     * @return
     */
    public static String nullToStr(String val, String def) {
        return isNull(val) ? def : val;
    }


    /**
     * 生成指定位数的字符串，不够补0
     *
     * @param str       字符串
     * @param strLength 指定长度
     * @return
     */

    public static String addZeroForNum(String str, int strLength) {
        int strLen = str.length();
        if (strLen < strLength) {
            while (strLen < strLength) {
                StringBuffer sb = new StringBuffer();
                sb.append("0").append(str);// 左补0
                // sb.append(str).append("0");//右补0
                str = sb.toString();
                strLen = str.length();
            }
        }

        return str;


    }

    /**
     * 字符串去重
     *
     * @param name 字符串
     * @param s    分隔条件
     * @return
     */
    public static String repeatString(String name, String s) {
        String[] str = name.split(s);
        if (str.length == 0) {
            return null;
        }
        List<String> list = new ArrayList();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length; i++) {
            if (!list.contains(str[i])) {
                list.add(str[i]);
                sb.append(str[i] + ",");
            }
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

    /**
     * 字符串转List<Integer>
     */

    public static List<Integer> stringToListInteger(String val, String s) {
        String[] arr = val.split(s);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            list.add(Integer.parseInt(arr[i]));
        }

        return list;

    }

    /**
     * String to Double
     *
     * @param val
     * @param defaultValue
     * @return
     */
    public static Double stringToDouble(String val, Double defaultValue) {

        if (val.isEmpty() || val == null) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(val);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * 字符串转List<Long>
     */

    public static List<Long> stringToListLong(String val, String s) {
        String[] arr = val.split(s);
        List<Long> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            list.add(Long.parseLong(arr[i]));
        }

        return list;

    }

    /**
     * List<Integer>1,2,3 - > String"1,2,3"
     *
     * @param ids
     * @return
     */
    public static String listToString(List<Integer> ids) {
        return ids.stream().filter(Objects::nonNull).map(Object::toString).collect(Collectors.joining(","));
    }

    /**
     * 随机生成8位字符串
     *
     * @return
     */
    public static String randomStr8() {
        return String.valueOf(new Random().nextLong()).substring(1, 8 + 1);
    }

    public static boolean isLetterOrDigits(String str) {
        return str.chars().allMatch(t -> Character.isLowerCase(t) || Character.isUpperCase(t) || Character.isDigit(t));
    }

    public static boolean containsAny(final Collection<String> coll1, final Collection<String> coll2) {
        if (coll1.size() < coll2.size()) {
            for (final String aColl1 : coll1) {
                if (coll2.contains(StringUtils.lowerCase(aColl1))) {
                    return true;
                }
            }
        } else {
            for (final String aColl2 : coll2) {
                if (coll1.contains(StringUtils.lowerCase(aColl2))) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean containsAnyIgnoreCase(final Collection<String> coll1, final Collection<String> coll2) {
        if (coll1.size() < coll2.size()) {
            Collection<String> coll2LowerCase = coll2.stream().map(StringUtils::lowerCase).collect(Collectors.toSet());
            for (final String aColl1 : coll1) {
                if (coll2LowerCase.contains(StringUtils.lowerCase(aColl1))) {
                    return true;
                }
            }
        } else {
            Collection<String> coll1LowerCase = coll1.stream().map(StringUtils::lowerCase).collect(Collectors.toSet());
            for (final String aColl2 : coll2) {
                if (coll1LowerCase.contains(StringUtils.lowerCase(aColl2))) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean verifyToSqlInString(String param) {
        return StringUtils.isNotBlank(StringUtils.toSqlInString(param));
    }

    /**
     * 验证数组中是否有相同字符串
     *
     * @param strs
     * @return
     */
    public static boolean compareIsRepeat(String[] strs) {
        Set<String> set = new HashSet<>();
        boolean result = false;
        for (int i = 0; i < strs.length; i++) {
            for (int j = i + 1; j < strs.length; j++) {
                if (strs[i].equals(strs[j])) {
                    set.add(strs[i]);
                    result = true;
                }
            }
        }
        return result;
    }


}
