package com.mfh.crowd.funding.config;

import com.mfh.crowd.funding.exception.CrowdFundingAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

    /**
     * Spring 在真正调用这个方法前会检查，IOC 容器中是否已经有了对应的 bean，如果有，则不会真正调用这个方法，而是直接把 IOC 容器中的 bean 返回
     * @return
     */
    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security
            .authorizeRequests()
            .antMatchers("/index.html","/bootstrap/**","/css/**","/fonts/**","/img/**","/jquery/**","/layer/**","/script/**","/ztree/**")
            .permitAll()
            .antMatchers("/admin/query/for/search.html")
            .hasRole("PM - 项目经理")
            .anyRequest()
            .authenticated()
            .and()
            .exceptionHandling()
            .accessDeniedHandler(new CrowdFundingAccessDeniedHandler())
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
