package com.example.cellphoneweb.configurations;

import com.example.cellphoneweb.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class Security {

    @Autowired
    private CustomAuthenProvider customAuthenProvider;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(customAuthenProvider)
                .build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .cors(Customizer.withDefaults())
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/login/test").permitAll()
                                .requestMatchers("/swagger-ui/**", "/v3/**").permitAll()
                                .requestMatchers("/user/register").hasAnyRole("USER")
//                                .requestMatchers("/product/**","/rawMaterial/**").hasAnyRole("ADMIN","USER","STAFF")
//                                .requestMatchers("/room/**").hasAnyRole("ADMIN","USER","STAFF")
//                                .requestMatchers("/quoteDetail/**").hasAnyRole("ADMIN","STAFF","USER")
//                                .requestMatchers("/user/**").hasAnyRole("USER","ADMIN","STAFF")
                                .anyRequest().authenticated()


                )
//                .httpBasic(withDefaults());
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);



        return http.build();

    }
}
