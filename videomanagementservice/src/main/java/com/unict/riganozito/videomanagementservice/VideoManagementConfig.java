package com.unict.riganozito.videomanagementservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class VideoManagementConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserVMSDetailsService service;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
         * http .authorizeRequests().antMatchers("/videos/").permitAll()
         * .antMatchers("")
         */
        /*
         * http.authorizeRequests().antMatchers("/videos").permitAll().anyRequest().
         * authenticated().and() .antMatcher("/register");
         */
        http.httpBasic();// .and().authorizeRequests().antMatchers("/videos").authenticated().and().csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service);
    }
}
