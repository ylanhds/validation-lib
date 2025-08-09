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
     */
    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    /**
     * 手机号校验（中国大陆）
     */
    public static boolean isValidPhone(String phone) {
        return phone != null && phone.matches("^1[3-9]\\d{9}$");
    }

    /**
     * 身份证号码校验（15位/18位）
     */
    public static boolean isValidIdCard(String idCard) {
        return idCard != null && idCard.matches("(^\\d{15}$)|(^\\d{17}([0-9Xx])$)");
    }

    /**
     * 金额范围校验
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
     */
    public static boolean isValidDecimal(BigDecimal value, int integerPart, int fractionPart) {
        if (value == null) {
            return false;
        }
        String[] parts = value.toPlainString().split("\\.");
        if (parts[0].length() > integerPart) {
            return false;
        }
        if (parts.length > 1 && parts[1].length() > fractionPart) {
            return false;
        }
        return true;
    }

    /**
     * 日期时间格式 + 范围校验
     */
    public static boolean isValidDateTime(String dateTime, String pattern, String min, String max) {
        if (dateTime == null) {
            return false;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            LocalDateTime dt;
            if (pattern.contains("HH") || pattern.contains("mm")) {
                dt = LocalDateTime.parse(dateTime, formatter);
            } else {
                dt = java.time.LocalDate.parse(dateTime, formatter).atStartOfDay();
            }
            if (!min.isEmpty() && dt.isBefore(parseToLocalDateTime(min, pattern))) {
                return false;
            }
            if (!max.isEmpty() && dt.isAfter(parseToLocalDateTime(max, pattern))) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static LocalDateTime parseToLocalDateTime(String date, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        if (pattern.contains("HH") || pattern.contains("mm")) {
            return LocalDateTime.parse(date, formatter);
        } else {
            return java.time.LocalDate.parse(date, formatter).atStartOfDay();
        }
    }

    /**
     * URL格式校验
     */
    public static boolean isValidUrl(String url) {
        return url != null && url.matches("^(https?|ftp)://[^\\s/$.?#].[^\\s]*$");
    }

    public static boolean isValidFile(byte[] content, long maxSizeKB, String[] types, String mimeType) {
        if (content == null) {
            return false;
        }
        if (content.length > maxSizeKB * 1024) {
            return false;
        }
        if (types != null && types.length > 0 && mimeType != null) {
            for (String t : types) {
                if (t.equalsIgnoreCase(mimeType)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    /**
     * 密码复杂度校验
     */
    public static boolean isValidPassword(String password, boolean requireUppercase, boolean requireLowercase,
                                          boolean requireDigit, boolean requireSpecial,
                                          int minLength, int maxLength) {
        if (password == null) {
            return false;
        }
        if (password.length() < minLength || password.length() > maxLength) {
            return false;
        }
        if (requireUppercase && !password.matches(".*[A-Z].*")) {
            return false;
        }
        if (requireLowercase && !password.matches(".*[a-z].*")) {
            return false;
        }
        if (requireDigit && !password.matches(".*\\d.*")) {
            return false;
        }
        if (requireSpecial && !password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
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