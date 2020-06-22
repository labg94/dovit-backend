package com.dovit.backend.config;

import com.dovit.backend.security.*;
import com.microsoft.azure.spring.autoconfigure.aad.AADAppRoleStatelessAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;

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

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter() {
    return new JwtAuthenticationFilter();
  }

  @Value("${api.ldap.server}")
  public String LDAP_SERVER;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    //    auth.ldapAuthentication()
    //        .userDnPatterns("uid={0},ou=people")
    //        .userSearchFilter("(|(uid={0})(mail={0}))")
    //        .groupSearchBase("ou=groups")
    //        .contextSource(contextSource())
    //        .userDetailsContextMapper(userDetailsContextMapper())
    //        .passwordCompare()
    //        // .passwordEncoder(passwordEncoder()) // TODO activar cuando conecte real
    //        .passwordAttribute("userPassword");

    auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
  }

  @Bean
  public DefaultSpringSecurityContextSource contextSource() {
    return new DefaultSpringSecurityContextSource(
        Collections.singletonList(LDAP_SERVER), "dc=springframework,dc=org");
  }

  public UserDetailsContextMapper userDetailsContextMapper() {
    return new CustomLdapMapper();
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

    http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    http.addFilterBefore(aadAuthFilter, UsernamePasswordAuthenticationFilter.class);
  }
}
