package com.example.bankcards.util;

import com.example.bankcards.model.User;
import com.example.bankcards.security.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    private SecurityUtil() {
        // static utility class
    }

    public static User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetailsImpl userDetails) {
            return userDetails.getUser();
        }

        throw new IllegalStateException("Пользователь не найден в контексте безопасности");
    }
}
