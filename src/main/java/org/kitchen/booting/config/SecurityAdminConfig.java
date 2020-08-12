package org.kitchen.booting.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(1)
@EnableWebSecurity
public class SecurityAdminConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        http.csrf().disable().authorizeRequests()
//                .antMatchers("/admin/*").access("hasRole('ROLE_ADMIN')")
//                .and().formLogin().loginPage("/admin/login").permitAll()
//                .defaultSuccessUrl("/admin/index").failureUrl("/admin/login?param=error");
        http
                .antMatcher("/admin/**")
                .authorizeRequests()
                .anyRequest()
                .hasRole("ADMIN")

                .and()
                .formLogin()
                .loginPage("/admin/login").permitAll().usernameParameter("userId")
                .loginProcessingUrl("/admin/login")
                .failureUrl("/admin/login?param=error")
                .defaultSuccessUrl("/admin/")

                .and()
                .logout()
                .logoutUrl("/admin/logout")
                .logoutSuccessUrl("/admin/login?param=logout")

                .and()
                .exceptionHandling()
                .accessDeniedPage("/403")

                .and()
                .csrf().disable();
    }
}