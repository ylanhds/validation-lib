package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 金额范围校验注解
 * min/max 单位为元
 * 使用位置：类的字段
 * @Author：zhangbaosheng
 * @Package：annotations
 * @Project：validation-lib
 * @name：Price
 * @Date：2025/8/9 13:50
 * @Filename：Price
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Price {
    double min() default 0;
    double max() default Double.MAX_VALUE;
    String message() default "价格不在有效范围";
}