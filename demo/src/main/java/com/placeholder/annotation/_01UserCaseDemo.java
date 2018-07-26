package com.placeholder.annotation;

import java.lang.annotation.*;
import java.lang.reflect.Method;

public class _01UserCaseDemo {
    public static void main(String[] args) {
        UserCaseProcessor.trackUseCase(PasswordUtils.class);
    }
}

class PasswordUtils {
    @UserCase(id = 47, description = "Passwords ust contain at least one numeric")
    public boolean validatePassword(String password) {
        return password.matches("\\w*\\d\\w");
    }

    @UserCase(id = 48)
    public String encryptPassword(String password) {
        return new StringBuilder(password).reverse().toString();
    }
}

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface UserCase {
    int id();
    String description() default "no description";
}

class UserCaseProcessor {
    public static <T> void trackUseCase(Class<T> clazz) {
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method method : declaredMethods) {
            UserCase userCase = method.getAnnotation(UserCase.class);
            if (userCase != null) {
                System.out.println(method.getName() + ": " + userCase.id() + ", " + userCase.description());
            }
        }
    }
}
