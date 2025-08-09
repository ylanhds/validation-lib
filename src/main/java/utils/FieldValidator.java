package utils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 字段校验工具类
 * 所有判断逻辑集中在这里，便于复用和单元测试
 *
 * @Author：zhangbaosheng
 * @Package：utils
 * @Project：validation-lib
 * @name：FieldValidator
 * @Date：2025/8/9 13:51
 * @Filename：FieldValidator
 */
public class FieldValidator {

    /**
     * 邮箱校验
     *
     * @param email 邮箱地址
     * @return 是否为有效邮箱格式
     */
    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    /**
     * 手机号校验（中国大陆）
     *
     * @param phone 手机号码
     * @return 是否为有效的中国大陆手机号
     */
    public static boolean isValidPhone(String phone) {
        return phone != null && phone.matches("^1[3-9]\\d{9}$");
    }

    /**
     * 身份证号码校验（15位/18位）
     *
     * @param idCard 身份证号码
     * @return 是否为有效的身份证号码格式
     */
    public static boolean isValidIdCard(String idCard) {
        return idCard != null && idCard.matches("(^\\d{15}$)|(^\\d{17}([0-9Xx])$)");
    }

    /**
     * 金额范围校验
     *
     * @param price 金额
     * @param min   最小值
     * @param max   最大值
     * @return 是否在指定范围内
     */
    public static boolean isValidPrice(BigDecimal price, double min, double max) {
        if (price == null) {
            return false;
        }
        return price.compareTo(BigDecimal.valueOf(min)) >= 0
                && price.compareTo(BigDecimal.valueOf(max)) <= 0;
    }

    public static boolean isValidQuantity(Integer qty, int min, int max) {
        if (qty == null) {
            return false;
        }
        return qty >= min && qty <= max;
    }

    public static boolean isValidPostalCode(String code) {
        return code != null && code.matches("^\\d{6}$");
    }

    public static boolean isValidName(String name, int minLength, int maxLength) {
        return name != null && name.length() >= minLength && name.length() <= maxLength;
    }

    public static boolean isValidAddress(String address, int minLength, int maxLength) {
        return address != null && address.length() >= minLength && address.length() <= maxLength;
    }

    /**
     * 数值精度校验
     *
     * @param value        数值
     * @param integerPart  整数部分最大位数
     * @param fractionPart 小数部分最大位数
     * @return 是否符合精度要求
     */
    public static boolean isValidDecimal(BigDecimal value, int integerPart, int fractionPart) {
        if (value == null) {
            return false;
        }
        // 使用BigDecimal的precision和scale进行校验
        // 获取整数部分位数
        BigDecimal stripped = value.stripTrailingZeros();
        int integerDigits = stripped.precision() - stripped.scale();
        if (integerDigits < 0) {
            integerDigits = 0; // 处理纯小数情况
        }

        // 获取小数部分位数
        int fractionDigits = Math.max(stripped.scale(), 0);

        return integerDigits <= integerPart && fractionDigits <= fractionPart;
    }

    /**
     * 日期时间格式 + 范围校验
     *
     * @param dateTime 日期时间字符串
     * @param pattern  日期时间格式
     * @param min      最小日期时间
     * @param max      最大日期时间
     * @return 是否符合格式和范围要求
     */
    public static boolean isValidDateTime(String dateTime, String pattern, String min, String max) {
        if (dateTime == null || pattern == null) {
            return false;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            LocalDateTime dt = parseDateTime(dateTime, pattern, formatter);

            if (min != null && !min.isEmpty()) {
                LocalDateTime minDt = parseDateTime(min, pattern, formatter);
                if (dt.isBefore(minDt)) {
                    return false;
                }
            }

            if (max != null && !max.isEmpty()) {
                LocalDateTime maxDt = parseDateTime(max, pattern, formatter);
                if (dt.isAfter(maxDt)) {
                    return false;
                }
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static LocalDateTime parseDateTime(String date, String pattern, DateTimeFormatter formatter) {
        // 更准确地判断是否包含时间部分
        boolean hasTime = pattern.contains("H") || pattern.contains("m") || pattern.contains("s");

        if (hasTime) {
            return LocalDateTime.parse(date, formatter);
        } else {
            return java.time.LocalDate.parse(date, formatter).atStartOfDay();
        }
    }
//    public static boolean isValidDateTime(String dateTime, String pattern, String min, String max) {
//        if (dateTime == null || pattern == null) {
//            return false;
//        }
//        try {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
//            LocalDateTime dt;
//            if (pattern.contains("HH") || pattern.contains("mm")) {
//                dt = LocalDateTime.parse(dateTime, formatter);
//            } else {
//                dt = java.time.LocalDate.parse(dateTime, formatter).atStartOfDay();
//            }
//            if (!min.isEmpty() && dt.isBefore(parseToLocalDateTime(min, pattern))) {
//                return false;
//            }
//            if (!max.isEmpty() && dt.isAfter(parseToLocalDateTime(max, pattern))) {
//                return false;
//            }
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    private static LocalDateTime parseToLocalDateTime(String date, String pattern) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
//        if (pattern.contains("HH") || pattern.contains("mm")) {
//            return LocalDateTime.parse(date, formatter);
//        } else {
//            return java.time.LocalDate.parse(date, formatter).atStartOfDay();
//        }
//    }

    /**
     * URL格式校验
     */
    public static boolean isValidUrl(String url) {
        return url != null && url.matches("^(https?|ftp)://[^\\s/$.?#].[^\\s]*$");
    }

    /**
     * 文件校验
     *
     * @param content   文件内容
     * @param maxSizeKB 最大大小(KB)
     * @param types     允许的MIME类型数组
     * @param mimeType  文件实际MIME类型
     * @return 是否符合要求
     */
    public static boolean isValidFile(byte[] content, long maxSizeKB, String[] types, String mimeType) {
        if (content == null) {
            return false;
        }

        // 校验文件大小
        if (maxSizeKB > 0 && content.length > maxSizeKB * 1024) {
            return false;
        }

        // 如果没有指定类型限制，则不校验类型
        if (types == null || types.length == 0) {
            return true;
        }

        // 校验MIME类型
        if (mimeType == null) {
            return false;
        }

        for (String type : types) {
            if (type != null && type.equalsIgnoreCase(mimeType)) {
                return true;
            }
        }

        return false;
    }
//    public static boolean isValidFile(byte[] content, long maxSizeKB, String[] types, String mimeType) {
//        if (content == null) {
//            return false;
//        }
//        if (content.length > maxSizeKB * 1024) {
//            return false;
//        }
//        if (types != null && types.length > 0 && mimeType != null) {
//            for (String t : types) {
//                if (t.equalsIgnoreCase(mimeType)) {
//                    return true;
//                }
//            }
//            return false;
//        }
//        return true;
//    }

    /**
     * 密码复杂度校验
     *
     * @param password         密码
     * @param requireUppercase 是否需要大写字母
     * @param requireLowercase 是否需要小写字母
     * @param requireDigit     是否需要数字
     * @param requireSpecial   是否需要特殊字符
     * @param minLength        最小长度
     * @param maxLength        最大长度
     * @return 是否符合复杂度要求
     */
    public static boolean isValidPassword(String password, boolean requireUppercase, boolean requireLowercase,
                                          boolean requireDigit, boolean requireSpecial,
                                          int minLength, int maxLength) {
        if (password == null) {
            return false;
        }

        // 长度校验
        int length = password.length();
        if (length < minLength || length > maxLength) {
            return false;
        }

        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        // 一次遍历完成所有字符检查
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowercase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if ("!@#$%^&*(),.?\":{}|<>".indexOf(c) != -1) {
                hasSpecial = true;
            }
        }

        // 检查是否满足各项要求
        if (requireUppercase && !hasUppercase) {
            return false;
        }
        if (requireLowercase && !hasLowercase) {
            return false;
        }
        if (requireDigit && !hasDigit) {
            return false;
        }
        if (requireSpecial && !hasSpecial) {
            return false;
        }

        return true;
    }

    /**
     * 枚举值校验
     */
    public static boolean isValidEnumValue(Object value, Class<? extends Enum<?>> enumClass) {
        if (value == null) {
            return false;
        }
        for (Enum<?> e : enumClass.getEnumConstants()) {
            if (e.name().equals(value.toString())) {
                return true;
            }
        }
        return false;
    }
}