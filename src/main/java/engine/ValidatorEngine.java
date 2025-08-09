package engine;

import annotations.*;
import exceptions.ValidationException;
import utils.FieldValidator;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 校验引擎
 * 通过反射扫描对象字段及自定义注解，并调用对应的工具方法进行校验
 *
 * @Author：zhangbaosheng
 * @Package：engine
 * @Project：validation-lib
 * @name：ValidatorEngine
 * @Date：2025/8/9 13:51
 * @Filename：ValidatorEngine
 */
public class ValidatorEngine {

    public static void validate(Object target) {
        List<String> errors = new ArrayList<>();

        // 遍历对象所有字段
        for (Field field : target.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value;
            try {
                value = field.get(target);
            } catch (IllegalAccessException e) {
                continue;
            }

            // 邮箱校验
            if (field.isAnnotationPresent(Email.class)) {
                if (!FieldValidator.isValidEmail((String) value)) {
                    errors.add(field.getAnnotation(Email.class).message());
                }
            }
            // 手机号
            if (field.isAnnotationPresent(Phone.class)) {
                if (!FieldValidator.isValidPhone((String) value)) {
                    errors.add(field.getAnnotation(Phone.class).message());
                }
            }
            // 身份证
            if (field.isAnnotationPresent(IdCard.class)) {
                if (!FieldValidator.isValidIdCard((String) value)) {
                    errors.add(field.getAnnotation(IdCard.class).message());
                }
            }
            // 金额
            if (field.isAnnotationPresent(Price.class)) {
                Price conf = field.getAnnotation(Price.class);
                if (!FieldValidator.isValidPrice((BigDecimal) value, conf.min(), conf.max())) {
                    errors.add(conf.message());
                }
            }

            if (field.isAnnotationPresent(Quantity.class)) {
                Quantity conf = field.getAnnotation(Quantity.class);
                if (!FieldValidator.isValidQuantity((Integer) value, conf.min(), conf.max())) {
                    errors.add(conf.message());
                }
            }

            if (field.isAnnotationPresent(PostalCode.class) &&
                    !FieldValidator.isValidPostalCode((String) value)) {
                errors.add(field.getAnnotation(PostalCode.class).message());
            }

            if (field.isAnnotationPresent(Name.class)) {
                Name conf = field.getAnnotation(Name.class);
                if (!FieldValidator.isValidName((String) value, conf.minLength(), conf.maxLength())) {
                    errors.add(conf.message());
                }
            }

            if (field.isAnnotationPresent(Address.class)) {
                Address conf = field.getAnnotation(Address.class);
                if (!FieldValidator.isValidAddress((String) value, conf.minLength(), conf.maxLength())) {
                    errors.add(conf.message());
                }
            }

            // 数值精度
            if (field.isAnnotationPresent(Decimal.class)) {
                Decimal conf = field.getAnnotation(Decimal.class);
                if (!FieldValidator.isValidDecimal((BigDecimal) value, conf.integerPart(), conf.fractionPart())) {
                    errors.add(conf.message());
                }
            }
            // 其他类型校验（日期、URL、密码、枚举等）... 按同样模式添加
            if (field.isAnnotationPresent(DateTime.class)) {
                DateTime conf = field.getAnnotation(DateTime.class);
                if (!FieldValidator.isValidDateTime((String) value, conf.pattern(), conf.min(), conf.max())) {
                    errors.add(conf.message());
                }
            }

            if (field.isAnnotationPresent(Url.class) &&
                    !FieldValidator.isValidUrl((String) value)) {
                errors.add(field.getAnnotation(Url.class).message());
            }

            if (field.isAnnotationPresent(FileConstraint.class)) {
                FileConstraint conf = field.getAnnotation(FileConstraint.class);
                if (value instanceof FileData) {
                    FileData fd = (FileData) value;
                    if (!FieldValidator.isValidFile(fd.getContent(), conf.maxSizeKB(), conf.allowedTypes(), fd.getMimeType())) {
                        errors.add(conf.message());
                    }
                } else {
                    errors.add(conf.message());
                }
            }

            if (field.isAnnotationPresent(Password.class)) {
                Password conf = field.getAnnotation(Password.class);
                if (!FieldValidator.isValidPassword(
                        (String) value,
                        conf.requireUppercase(),
                        conf.requireLowercase(),
                        conf.requireDigit(),
                        conf.requireSpecial(),
                        conf.minLength(),
                        conf.maxLength())) {
                    errors.add(conf.message());
                }
            }

            if (field.isAnnotationPresent(EnumValue.class)) {
                EnumValue conf = field.getAnnotation(EnumValue.class);
                if (!FieldValidator.isValidEnumValue(value, conf.enumClass())) {
                    errors.add(conf.message());
                }
            }
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
