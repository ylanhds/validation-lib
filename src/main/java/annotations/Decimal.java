package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数值精度（整数位/小数位）校验注解
 * 使用位置：类的字段
 * @Author：zhangbaosheng
 * @Package：annotations
 * @Project：validation-lib
 * @name：Decimal
 * @Date：2025/8/9 13:50
 * @Filename：Decimal
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Decimal {
    int integerPart() default Integer.MAX_VALUE;

    int fractionPart() default Integer.MAX_VALUE;

    String message() default "数值精度不符合要求";
}