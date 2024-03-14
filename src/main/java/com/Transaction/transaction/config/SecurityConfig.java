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
    public SecurityConfig(@Lazy JwtFilterChain jwtAuthenticationFilter, JwtEntryPoint jwtEntryPoint) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtEntryPoint = jwtEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                /*ADMIN AUTHORIZATION */
                .antMatchers("/admin/post").hasAuthority("ADMIN_CREATE")
                .antMatchers("/admin/updateBusStop/{id}").hasAuthority("ADMIN_UPDATE")
                .antMatchers("/admin/deleteBusStop/{id}").hasAuthority("ADMIN_DELETE")
                .antMatchers("/admin/postSeat/{id}").hasAuthority("ADMIN_CREATE")
                .antMatchers("/admin/updateSeat/{id}").hasAuthority("ADMIN_UPDATE")
                .antMatchers("/admin/deleteSeat/{id}").hasAuthority("ADMIN_DELETE")
               .antMatchers("/admin/deleteRoute/{id}").hasAuthority("ADMIN_DELETE")
               .antMatchers("/admin/updateRoute/{id}").hasAuthority("ADMIN_UPDATE")
               .antMatchers("/admin/busStopRoute/{id}/{id1}").hasAuthority("ADMIN_CREATE")
                .antMatchers("/admin/bus/{id}/route/{routeId}").hasAuthority("ADMIN_UPDATE")
                .antMatchers("/admin/routeBus/{id}").hasAuthority("ADMIN_CREATE")
                .antMatchers("/admin/deleteBus/{id}").hasAuthority("ADMIN_DELETE")
               .antMatchers("/auth/create_user").permitAll()
               .antMatchers("/auth/login").permitAll()
                /*USER CAN ACCESS EVERY ENDPOINT */
                .antMatchers("/booking/**","/payment/**","/bookSeats/**","/secret/**","/tickets/**","/user/**",
                        "/busStop/**","/route/**","/bus/**","/seat/**").permitAll()
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
