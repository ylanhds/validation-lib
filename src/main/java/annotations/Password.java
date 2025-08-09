// Password.java
package annotations;

import java.lang.annotation.*;
/**
 * 文密码复杂度校验注解
 * 使用位置：类的字段
 *
 * @Author：zhangbaosheng
 * @Package：annotations
 * @Project：validation-lib
 * @name：Password
 * @Date：2025/8/9 13:50
 * @Filename：Password
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Password {
    boolean requireUppercase() default true;
    boolean requireLowercase() default true;
    boolean requireDigit() default true;
    boolean requireSpecial() default true;
    int minLength() default 8;
    int maxLength() default 64;
    String message() default "密码复杂度不符合要求";
}