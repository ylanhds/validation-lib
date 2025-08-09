// FileConstraint.java
package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 文件校验注解
 * 使用位置：类的字段
 *
 * @Author：zhangbaosheng
 * @Package：annotations
 * @Project：validation-lib
 * @name：FileConstraint
 * @Date：2025/8/9 13:50
 * @Filename：FileConstraint
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FileConstraint {
    long maxSizeKB() default Long.MAX_VALUE;

    String[] allowedTypes() default {};

    String message() default "文件不符合上传要求";
}