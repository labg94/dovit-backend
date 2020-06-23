package com.dovit.backend.config;

import com.dovit.backend.security.CustomUserDetailsService;
import com.dovit.backend.security.JwtAccessDeniedHandler;
import com.dovit.backend.security.JwtAuthenticationEntryPoint;
import com.dovit.backend.security.JwtAuthenticationFilter;
import com.microsoft.azure.spring.autoconfigure.aad.AADAppRoleStatelessAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Ramón París
 * @since 02-10-2019
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final CustomUserDetailsService customUserDetailsService;
  private final JwtAuthenticationEntryPoint unauthorizedHandler;
  private final JwtAccessDeniedHandler accessDeniedHandler;
  private final AADAppRoleStatelessAuthenticationFilter aadAuthFilter;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
  }

  @Bean(BeanIds.AUTHENTICATION_MANAGER)
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors()
        .and()
        .csrf()
        .disable()
        .exceptionHandling()
        .accessDeniedHandler(accessDeniedHandler)
        .authenticationEntryPoint(unauthorizedHandler)
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers(
            "/",
            "/favicon.ico",
            "/**/*.png",
            "/**/*.gif",
            "/**/*.svg",
            "/**/*.jpg",
            "/**/*.html",
            "/**/*.css",
            "/images/**",
            "/**/*.js")
        .permitAll()
        .antMatchers("/api/auth/**")
        .permitAll()
        .antMatchers("/actuator/**")
        .permitAll()
        .anyRequest()
        .authenticated();

    http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    http.addFilterBefore(aadAuthFilter, UsernamePasswordAuthenticationFilter.class);
  }
}
