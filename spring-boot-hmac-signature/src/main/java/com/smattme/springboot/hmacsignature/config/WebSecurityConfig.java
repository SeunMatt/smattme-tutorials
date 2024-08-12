package com.smattme.springboot.hmacsignature.config;

import com.smattme.springboot.hmacsignature.entities.User;
import com.smattme.springboot.hmacsignature.filters.HmacSignatureFilter;
import com.smattme.springboot.hmacsignature.repositories.UserRepository;
import com.smattme.springboot.hmacsignature.repositories.UserSecretKeyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Collections;
import java.util.Objects;

@Configuration
@Slf4j
public class WebSecurityConfig {

    private final UserRepository userRepository;
    private final UserSecretKeyRepository userSecretKeyRepository;
    private final HmacSignatureFilterConfig hmacSignatureFilterConfig;

    public WebSecurityConfig(UserRepository userRepository, UserSecretKeyRepository userSecretKeyRepository,
            HmacSignatureFilterConfig hmacSignatureFilterConfig) {
        this.userRepository = userRepository;
        this.userSecretKeyRepository = userSecretKeyRepository;
        this.hmacSignatureFilterConfig = hmacSignatureFilterConfig;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        //since this is an API, configure it to be stateless
        http.httpBasic(withDefaults());
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.csrf(AbstractHttpConfigurer::disable);

        //configure the security chain to authenticate all endpoints
        // except the /error.
        http.authorizeHttpRequests(requests ->
                requests.requestMatchers(new AntPathRequestMatcher("/error"))
                        .permitAll()
                        .anyRequest().authenticated()

        );


        return http.build();
    }



    @Bean
    public UserDetailsService userDetailsService() {
        return (username) -> {
            User user = userRepository.findByEmail(username);
            if(Objects.isNull(user)) throw new UsernameNotFoundException("User " + username + " not found");
            return new org.springframework.security.core.userdetails.User(user.getEmail(),
                    user.getPassword(), Collections.emptyList());
        };
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
