// EnumValue.java
package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 枚举定义范围校验注解
 * 使用位置：类的字段
 *
 * @Author：zhangbaosheng
 * @Package：annotations
 * @Project：validation-lib
 * @name：EnumValue
 * @Date：2025/8/9 13:50
 * @Filename：EnumValue
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EnumValue {
    Class<? extends Enum<?>> enumClass();

    String message() default "值不在枚举定义范围内";
}