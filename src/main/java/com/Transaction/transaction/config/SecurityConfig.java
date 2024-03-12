package com.Transaction.transaction.config;

import com.Transaction.transaction.entity.Permission;
import com.Transaction.transaction.entity.Role1;
import com.Transaction.transaction.security.CustomUserDetailsService;
import com.Transaction.transaction.security.JwtEntryPoint;
import com.Transaction.transaction.security.JwtFilterChain;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

import static com.Transaction.transaction.entity.Permission.*;
import static com.Transaction.transaction.entity.Role1.ADMIN;
import static com.Transaction.transaction.entity.Role1.SUPER_ADMIN;
import static org.springframework.http.HttpMethod.*;

@Configuration
public class SecurityConfig {

    private final JwtFilterChain jwtAuthenticationFilter;
    private final JwtEntryPoint jwtEntryPoint;
    public SecurityConfig(@Lazy JwtFilterChain jwtAuthenticationFilter, JwtEntryPoint jwtEntryPoint) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtEntryPoint = jwtEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
//                .antMatchers("/busStop/get").permitAll()
//                .antMatchers("/busStop/get/{id}").permitAll()
//                .antMatchers("/busStop/post").hasAuthority("ADMIN_CREATE")
//                .antMatchers("/busStop/update/{id}").hasAuthority("ADMIN_UPDATE")
//                .antMatchers("/busStop/delete/{id}").hasAuthority("ADMIN_DELETE")
//                .antMatchers("/bus/post").hasAuthority("ADMIN_CREATE").antMatchers("/busStop/route/{id}").hasAuthority("ADMIN_CREATE")
//                .antMatchers("/busStop/route").permitAll()
//                .antMatchers("/busStop/bus/{id}/route/{routeId}").hasAuthority("ADMIN_CREATE")
//                .antMatchers("/busStop/search").permitAll()
//                .antMatchers("/busStop/delete/{id}").hasAuthority("ADMIN_DELETE")
//                .antMatchers("/seat/get").permitAll()
//                .antMatchers("/seat/get/{id}").permitAll()
//                .antMatchers("/seat/post1/{id}").hasAuthority("ADMIN_CREATE")
//                .antMatchers("/seat/update/{id}").hasAuthority("ADMIN_UPDATE")
//                .antMatchers("/seat/delete/{id}").hasAuthority("ADMIN_DELETE")
//                .antMatchers("/seat/post/{id}").hasAuthority("ADMIN_CREATE")
//                .antMatchers("/seat/name").permitAll()
//                .antMatchers("/ticket/generate").permitAll()
//               .antMatchers("/ticket/seat/{id}/book/{id}").permitAll()
//                .antMatchers("/ticket/book/{id}").permitAll()
//                .antMatchers("/ticket/get/{id}").permitAll()
//                .antMatchers("/route/get").permitAll()
//               .antMatchers("/route/delete/{id}").hasAuthority("ADMIN_DELETE")
//               .antMatchers("/route/update/{id}").hasAuthority("ADMIN_UPDATE")
//               .antMatchers("/route/get/{id}").permitAll()
//               .antMatchers("/route/source/{id}/destination/{id1}").hasAuthority("ADMIN_CREATE")
//               .antMatchers("/auth/create_user").permitAll()
//               .antMatchers("/auth/login").permitAll()
//               .antMatchers("/bookSeats/{id}").permitAll()
//                .antMatchers("/booking/**").permitAll()
//                .antMatchers( "/bus1/allocateSeat").permitAll()
//             .antMatchers("/user/**").permitAll()
                .antMatchers("/busStop/**","/bus/**","/seat/**","/tickets/**","/route/**","/auth/**","/bookSeats/**","/booking/**","/bus1/**","/user/**","/price/**","/secret/**","/admin/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
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
