package com.spring.sakura.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf
                .ignoringRequestMatchers(
                    "/addProductInWishlist/**",
                    "/removeProductInWishlist/**",
                    "/auth/google"
                )
            )
            .authorizeHttpRequests(auth -> auth
                // Public GET endpoints
                .requestMatchers(HttpMethod.GET, "/**").permitAll()

                // Wishlist requires authentication
                .requestMatchers(HttpMethod.POST, "/addProductInWishlist/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/removeProductInWishlist/**").authenticated()
                // Cart requires permit
                .requestMatchers(HttpMethod.POST, "/cart/add/**").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/cart/remove/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/cart/increase/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/cart/decrease/**").permitAll()
                //Address Adding
                

                // Auth & registration public
                .requestMatchers(
                    "/registerUser",
                    "/process_register",
                    "user-profile",
                    "/addressForm",
                    "/loginUser",
                    "/auth/google",
                    "/logout"
                ).permitAll()

                // Everything else requires login
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            );

        return http.build();
    }
}
