package com.Transaction.transaction.config;

import com.Transaction.transaction.security.CustomUserDetailsService;
import com.Transaction.transaction.security.JwtEntryPoint;
import com.Transaction.transaction.security.JwtFilterChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtFilterChain jwtAuthenticationFilter;
    private final JwtEntryPoint jwtEntryPoint;
    private final UserDetailsService userDetailsService;
    public SecurityConfig(@Lazy JwtFilterChain jwtAuthenticationFilter, JwtEntryPoint jwtEntryPoint, UserDetailsService userDetailsService) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtEntryPoint = jwtEntryPoint;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/user/**","/busStop/**","/distance/**","/bookSeats/**").permitAll()
                .antMatchers("/ticket/**").permitAll()
                .antMatchers("/role/**").permitAll()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/route/**").permitAll()
                .antMatchers("/booking/**").permitAll()
                .antMatchers("/seat/**","/route/**","/bus/**","/tickets/**","/reserve/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .exceptionHandling(ex->ex.authenticationEntryPoint(jwtEntryPoint))
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }
    @Bean
    public UserDetailsService userDetailsService(){
        return new CustomUserDetailsService();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){

        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


}
