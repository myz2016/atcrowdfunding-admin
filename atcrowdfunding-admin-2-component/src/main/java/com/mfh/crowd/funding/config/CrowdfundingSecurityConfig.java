package com.mfh.crowd.funding.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author mfh
 * @date 2020/12/25 21:30
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CrowdfundingSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security
            .authorizeRequests()
            .antMatchers("/index.html","/bootstrap/**","/css/**","/fonts/**","/img/**","/jquery/**","/layer/**","/script/**","/ztree/**")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .formLogin()
            .loginPage("/admin/to/login/page.html")
            .permitAll()
            .loginProcessingUrl("/admin/secruty/do/login.html")
            .permitAll()
            .usernameParameter("loginAcct")
            .passwordParameter("userPswd")
            .defaultSuccessUrl("/admin/to/main/page.html")
            .and()
            .logout()
            .logoutUrl("/admin/secruty/do/logout.html")
            .logoutSuccessUrl("/index.html")
            .and()
            .csrf()
            .disable();

    }
}
