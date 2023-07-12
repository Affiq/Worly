package com.example.worly.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;



import com.example.worly.service.UserService;

// IMPORT USER SERVICE HERE import com.worly.service.UserService;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


	// Non-Jasleen's configuration

	@Autowired
    private  UserService userService;
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

	//tell which endpoints are secured and which ones are not
	// Configuration temporary

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/**")
                    .permitAll()
                .anyRequest()
                .authenticated().and()
                .formLogin();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    //one way to implement the configure auth provider
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(userService);
        return provider;
    }


// Jasleens configuration
//	@Autowired
//    private  UserService userService;
//	@Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//
//	//tell which endpoints are secured and which ones are not
//	// Configuration temporary
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .authorizeRequests()
//                    .antMatchers("/api/quiztemplates/**", "/api/attemptquiz/**","/")
//                    .authenticated()
//                .anyRequest().permitAll()
//				.and()
//                .formLogin()
//					.loginPage("/api/sign/in")
//					.usernameParameter("email")
//					.defaultSuccessUrl("/api/quiztemplates")
//					.permitAll()
//					.and()
//				.logout()
//					//.logoutUrl("/api/sign/out")
//					.permitAll();
//    }
//
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(daoAuthenticationProvider());
//    }
//
//    //one way to implement the configure auth provider
//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider() {
//        DaoAuthenticationProvider provider =
//                new DaoAuthenticationProvider();
//        //provider.setPasswordEncoder(bCryptPasswordEncoder);
//        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
//        provider.setUserDetailsService(userService);
//        return provider;
//    }


}
