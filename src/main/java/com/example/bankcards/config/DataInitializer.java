package com.example.bankcards.config;

import com.example.bankcards.model.Card;
import com.example.bankcards.model.User;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initDatabase(UserRepository userRepository, CardRepository cardRepository) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                // Admin user
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword("admin123"); // без шифрования на этом этапе
                admin.setRole("ADMIN");
                userRepository.save(admin);

                Card adminCard = new Card();
                adminCard.setNumber("1111 2222 3333 4444");
                adminCard.setExpirationDate(LocalDate.now().plusYears(2));
                adminCard.setStatus("ACTIVE");
                adminCard.setBalance(new BigDecimal("10000.00"));
                adminCard.setUser(admin);
                cardRepository.save(adminCard);
            }

            if (userRepository.findByUsername("user").isEmpty()) {
                // Regular user
                User user = new User();
                user.setUsername("user");
                user.setPassword("user123");
                user.setRole("USER");
                userRepository.save(user);

                Card userCard = new Card();
                userCard.setNumber("5555 6666 7777 8888");
                userCard.setExpirationDate(LocalDate.now().plusYears(1));
                userCard.setStatus("ACTIVE");
                userCard.setBalance(new BigDecimal("5000.00"));
                userCard.setUser(user);
                cardRepository.save(userCard);
            }
        };
    }
}