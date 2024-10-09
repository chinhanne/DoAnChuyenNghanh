package com.dacn.WebsiteBanDoCongNghe.validator;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Xác định annotation này apply ở đâu
@Target({ElementType.FIELD})
// Xác định annotation này sẽ được xử lý lúc nào (lúc chạy)
@Retention(RetentionPolicy.RUNTIME)
// Nơi chứa class chịu trách nhiệm validate cho annotation này
@Constraint(
        validatedBy = {DobConstrain.class}
)
public @interface DobConstraint {
    String message() default "{Invalid day of birthday}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int min() default 0;
}
