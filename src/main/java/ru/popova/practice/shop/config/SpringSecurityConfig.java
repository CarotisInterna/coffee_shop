package ru.popova.practice.shop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.popova.practice.shop.entity.code.RoleCode;
import ru.popova.practice.shop.service.security.CustomUserDetailsService;

import static ru.popova.practice.shop.util.constants.PathConstants.PATTERNS_FOR_GET;

@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private CustomUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/v2/api-docs", "/register", "/login", "/swagger-ui.html", "/account", "/js/**", "/api/login")
                .permitAll()
                .antMatchers(HttpMethod.GET, PATTERNS_FOR_GET)
                .permitAll()
                .antMatchers("/api/cart", "/api/cart/**").hasAnyRole(RoleCode.VENDOR.toString(), RoleCode.USER.toString())
                .antMatchers(HttpMethod.POST, "/api/drinks", "/api/categories", "/api/toppings").hasRole(RoleCode.VENDOR.toString())
                .antMatchers(HttpMethod.PUT, "/api/drinks", "/api/categories", "/api/toppings").hasRole(RoleCode.VENDOR.toString())
                .antMatchers(HttpMethod.DELETE, "/api/drinks", "/api/categories", "/api/toppings").hasRole(RoleCode.VENDOR.toString())
                .anyRequest().authenticated()
                .and()
                .logout()
                .permitAll()
                .and()
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring().
                antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources",
                        "/configuration/security", "/swagger-ui.html/**", "/webjars/**", "/resources/**",
                        "/swagger-resources/configuration/ui", "/js/**");
    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Autowired
    public void setUserDetailsService(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
