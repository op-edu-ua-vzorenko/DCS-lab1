package ua.edu.lab.lab1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class AppConfig {

    // 1. Переносимо сюди наших юзерів
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails bender = User.builder()
                .username("bender")
                .password(passwordEncoder().encode("beer"))
                .roles("ADMIN")
                .build();

        UserDetails fry = User.builder()
                .username("fry")
                .password(passwordEncoder().encode("slurm"))
                .roles("USER")
                .build();

        UserDetails zapp = User.builder()
                .username("zapp")
                .password(passwordEncoder().encode("kif"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(bender, fry, zapp);
    }

    // 2. Переносимо сюди PasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 3. Переносимо сюди провайдери
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}