// DateTime.java
package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日期/时间格式校验注解
 * 使用位置：类的字段
 *
 * @Author：zhangbaosheng
 * @Package：annotations
 * @Project：validation-lib
 * @name：DateTime
 * @Date：2025/8/9 13:50
 * @Filename：DateTime
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DateTime {
    String pattern() default "yyyy-MM-dd";

    String min() default "";

    String max() default "";

    String message() default "日期/时间格式错误或超出范围";
}