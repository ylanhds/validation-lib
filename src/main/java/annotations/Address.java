package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 地址长度校验注解
 * 使用位置：类的字段
 * @Author：zhangbaosheng
 * @Package：annotations
 * @Project：validation-lib
 * @name：Address
 * @Date：2025/8/9 13:51
 * @Filename：Address
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Address {
    int minLength() default 5;
    int maxLength() default 200;
    String message() default "地址长度不合法";
}