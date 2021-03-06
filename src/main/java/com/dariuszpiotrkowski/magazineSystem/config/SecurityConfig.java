package com.dariuszpiotrkowski.magazineSystem.config;

import com.dariuszpiotrkowski.magazineSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/elements/**").hasAnyRole("Magazynier", "Logistyk")
                .antMatchers("/projects/**").hasAnyRole("Logistyk", "Manager", "Magazynier")
                .antMatchers("/orders/**").hasAnyRole("Magazynier", "Logistyk", "Manager")
                .antMatchers("/customers/**").hasAnyRole("Logistyk", "Manager")
                .antMatchers("/pcb/**").hasAnyRole("Logistyk", "Magazynier")
                .antMatchers("/register/list").hasAnyRole("Administrator")
                .antMatchers("/register/showRegistrationForm").hasAnyRole("Administrator")
                .antMatchers("/register/processRegistrationForm").hasAnyRole("Administrator")
                .antMatchers("/register/updateUser").hasAnyRole("Administrator")
                .antMatchers("/register/save").hasAnyRole("Administrator")
                .antMatchers("/register/deleteUser").hasAnyRole("Administrator")
                .antMatchers("/register/updatePassword").hasAnyRole("Administrator", "Magazynier", "Logistyk", "Manager")
                .antMatchers("/register/savePassword").hasAnyRole("Administrator", "Magazynier", "Logistyk", "Manager")
                .and()
                .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/authenticateTheUser")
                    .successHandler(customAuthenticationSuccessHandler)
                    .permitAll()
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/login-error");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

}
