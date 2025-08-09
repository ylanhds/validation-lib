import annotations.*;
import engine.FileData;
import engine.ValidatorEngine;
import exceptions.ValidationException;

import java.math.BigDecimal;

public class ValidationTest {

    enum Gender {MALE, FEMALE}

    static class UserDTO {
        @Email
        private String email;
        @Phone
        private String phone;
        @IdCard
        private String idCard;
        @Price(min = 0.01, max = 999999.99)
        private BigDecimal price;
        @Quantity(min = 1, max = 100)
        private Integer qty;
        @PostalCode
        private String post;
        @Name(minLength = 2, maxLength = 10)
        private String name;
        @Address(minLength = 5, maxLength = 50)
        private String address;
        @Decimal(integerPart = 5, fractionPart = 2)
        private BigDecimal numberField;
        @DateTime(pattern = "yyyy-MM-dd", min = "2020-01-01", max = "2030-12-31")
        private String regDate;
        @Url
        private String website;
        @FileConstraint(maxSizeKB = 10, allowedTypes = {"image/png"})
        private FileData avatar;
        @Password(minLength = 8, requireSpecial = false)
        private String password;
        @EnumValue(enumClass = Gender.class)
        private Gender gender;

        @DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
        private String createTime;
        // setters omitted for brevity
    }

    public static void main(String[] args) {
        UserDTO u = new UserDTO();
        //不符合校验测试
//        u.email = "bad";
//        u.phone = "123";
//        u.idCard = "999";
//        u.price = new BigDecimal("-1.00");
//        u.qty = 200;
//        u.post = "abc";
//        u.name = "A";
//        u.address = "abc";
//        u.numberField = new BigDecimal("123456.789");
//        u.regDate = "2019-01-01";
//        u.website = "badurl";
//        u.avatar = new FileData(new byte[20*1024], "image/jpg");
//        u.password = "12345";
//        u.gender = null;


        //正常测试
        u.email = "163adb@163.com";
        u.phone = "13433334555";
        u.idCard = "440951199503788034";
        u.price = new BigDecimal("1.00");
        u.qty = 80;
        u.post = "518035";
        u.name = "james";
        u.address = "广东省深圳市区";
        u.numberField = new BigDecimal("99999.89");
        u.regDate = "2025-01-01";
        u.createTime = "2025-01-01 23:20:14";
        u.website = "http://www.baidu.com";
        u.avatar = new FileData(new byte[1024], "image/png");
        u.password = "DO27^dsfHJKEWA";
        u.gender = Gender.FEMALE;
        try {
            ValidatorEngine.validate(u);
        } catch (ValidationException e) {
            System.out.println("错误列表:");
            e.getErrors().forEach(System.out::println);
        }
    }
}