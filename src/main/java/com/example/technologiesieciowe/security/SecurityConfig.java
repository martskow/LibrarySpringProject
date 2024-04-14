package com.example.technologiesieciowe.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class responsible for configuring security settings.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // w bazie danych role zapisuje się ROLE_NAZWA
    // w konfiguracji role zapisuje się NAZWA
    @Value("${jwt.token.key}")
    private String key;

    /**
     * Configures security filters and authorization rules and configures authorization rules for various endpoints.
     *
     * @param http The HttpSecurity object used to configure security settings.
     * @return The SecurityFilterChain object.
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(new JWTTokenFilter(key), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(
                        authorizationManagerRequestMatcherRegistry ->
                                authorizationManagerRequestMatcherRegistry
                                        .requestMatchers("/login").permitAll()
                                        .requestMatchers("/book/getAll").hasAnyRole("ADMIN", "LIBRARIAN")
                                        .requestMatchers("/book/getAllTitles").hasAnyRole("ADMIN", "LIBRARIAN", "USER")
                                        .requestMatchers("/book/add").hasAnyRole("ADMIN", "LIBRARIAN")
                                        .requestMatchers("/book/edit/{id}").hasAnyRole("ADMIN", "LIBRARIAN")
                                        .requestMatchers("/book/getOne/{id}").hasAnyRole("ADMIN", "LIBRARIAN", "USER")
                                        .requestMatchers("/book/delete/{id}").hasAnyRole("ADMIN", "LIBRARIAN")
                                        .requestMatchers("/book/isAvailable/{id}").hasAnyRole("ADMIN", "LIBRARIAN", "USER")
                                        .requestMatchers("/user/add").hasAnyRole("ADMIN", "LIBRARIAN")
                                        .requestMatchers("/user/delete/{id}").hasRole("ADMIN")
                                        .requestMatchers("/user/getAll").hasRole("ADMIN")
                                        .requestMatchers("/user/edit/{id}").hasAnyRole("ADMIN", "LIBRARIAN", "USER")
                                        .requestMatchers("/user/newPassword/{id}").hasAnyRole("ADMIN", "USER")
                                        .requestMatchers("/review/add").hasRole("USER")
                                        .requestMatchers("/review/edit/{id}").hasRole("USER")
                                        .requestMatchers("/review/getAll").hasAnyRole("ADMIN", "LIBRARIAN", "USER")
                                        .requestMatchers("review/delete/{id}").hasAnyRole("ADMIN", "LIBRARIAN", "USER")
                                        .requestMatchers("/review/getOne/{id}").hasAnyRole("ADMIN", "LIBRARIAN", "USER")
                                        .requestMatchers("/review/getByBook/{bookId}").hasAnyRole("ADMIN", "LIBRARIAN", "USER")
                                        .requestMatchers("/review/getByUser/{userId}").hasAnyRole("ADMIN", "LIBRARIAN", "USER")
                                        .requestMatchers("/loan/add").hasRole("LIBRARIAN")
                                        .requestMatchers("/loan/delete/{id}").hasRole("ADMIN")
                                        .requestMatchers("/loan/getAll").hasAnyRole("ADMIN", "LIBRARIAN")
                                        .requestMatchers("/loan/getOne/{id}").hasAnyRole("ADMIN", "LIBRARIAN", "USER")
                                        .requestMatchers("/loan/extendDueDate/{id}").hasRole("LIBRARIAN")
                                        .requestMatchers("/loan/returnBook/{id}").hasRole("LIBRARIAN")
                                        .requestMatchers("/loan/getAllBookLoans/{id}").hasAnyRole("ADMIN","LIBRARIAN")
                                        .requestMatchers("/loan/getAllUserLoans/{id}").hasAnyRole("ADMIN", "LIBRARIAN", "USER")
                                        .requestMatchers("/loan/getAllDelays").hasAnyRole("ADMIN", "LIBRARIAN")
                                        .requestMatchers("/loan/getUserDelays/{id}").hasAnyRole("ADMIN", "LIBRARIAN", "USER")
                                        .requestMatchers("/loanArchive/delete/{id}").hasRole("ADMIN")
                                        .requestMatchers("/loanArchive/getAll").hasAnyRole("ADMIN", "LIBRARIAN")
                                        .requestMatchers("/loanArchive/getOne/{id}").hasAnyRole("ADMIN", "LIBRARIAN", "USER")
                                        .requestMatchers("/queue/add").hasAnyRole("ADMIN", "LIBRARIAN", "USER")
                                        .requestMatchers("/queue/delete/{id}").hasAnyRole("ADMIN", "LIBRARIAN", "USER")
                                        .requestMatchers("/queue/getAll").hasAnyRole("ADMIN", "LIBRARIAN")
                                        .requestMatchers("/queue/getOne/{id}").hasAnyRole("ADMIN", "LIBRARIAN", "USER")
                                        .requestMatchers("/queue/endWaiting/{id}").hasRole("LIBRARIAN")
                                        .requestMatchers("/queue/getAllBookQueues/{id}").hasAnyRole("ADMIN", "LIBRARIAN")
                                        .requestMatchers("/queue/getAllUserQueues/{id}").hasAnyRole("ADMIN", "LIBRARIAN", "USER")
                                        .requestMatchers("/bookDetails/add").hasAnyRole("ADMIN", "LIBRARIAN")
                                        .requestMatchers("/bookDetails/delete/{id}").hasAnyRole("ADMIN", "LIBRARIAN")
                                        .requestMatchers("/bookDetails/getAll").hasAnyRole("ADMIN", "LIBRARIAN")
                                        .requestMatchers("/bookDetails/getOne/{id}").hasAnyRole("ADMIN", "LIBRARIAN")
                                        .requestMatchers("/bookDetails/getByGenre/{genre}").hasAnyRole("ADMIN", "LIBRARIAN")
                                        .requestMatchers("/bookDetails/getListByGenre/{genre}").hasAnyRole("ADMIN", "LIBRARIAN", "USER")
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

}

