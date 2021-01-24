package com.ticketingapp.config;

import com.ticketingapp.service.SecurityService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private SecurityService securityService;
    private AuthSuccessHandler authSuccessHandler;

    public SecurityConfig(SecurityService securityService, AuthSuccessHandler authSuccessHandler) {
        this.securityService = securityService;
        this.authSuccessHandler = authSuccessHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/", "/login", "/fragments/**", "/assets/**", "/images/**").permitAll()
            .antMatchers("/user/**").hasAuthority("Admin")
            .antMatchers("/project/**").hasAuthority("Manager")
            .antMatchers("/task/employee/**").hasAuthority("Employee")
            .antMatchers("/task/**").hasAuthority("Manager")
            .and()
                .formLogin()
                .loginPage("/login")
//            .defaultSuccessUrl("/welcome")
                .successHandler(authSuccessHandler)
                .failureUrl("/login?error=true")
                .permitAll()
            .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout=true")
            .and()
                .rememberMe()
                .tokenValiditySeconds(120)
                .key("ticketingAppSecurity")
                .userDetailsService(securityService);
    }

}
