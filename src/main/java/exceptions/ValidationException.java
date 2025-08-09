package exceptions;

import java.util.List;

/**
 * 自定义校验异常
 * 保存所有错误信息，便于统一处理
 *
 * @Author：zhangbaosheng
 * @Package：exceptions
 * @Project：validation-lib
 * @name：ValidationException
 * @Date：2025/8/9 13:51
 * @Filename：ValidationException
 */
public class ValidationException extends RuntimeException {
    private final List<String> errors;

    public ValidationException(List<String> errors) {
        super("字段校验失败");
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}