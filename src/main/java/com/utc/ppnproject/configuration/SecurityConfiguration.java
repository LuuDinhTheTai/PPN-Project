package com.utc.ppnproject.configuration;

import com.utc.ppnproject.constant.Constant;
import com.utc.ppnproject.security.jwt.CustomJwtDecoder;
import com.utc.ppnproject.security.jwt.JwtAuthenticationEntryPoint;
import com.utc.ppnproject.security.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
  
  private final CustomJwtDecoder customJwtDecoder;
  
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
            .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                                                                    // public endpoints
                                                                    .requestMatchers(HttpMethod.GET,
                                                                            "/accounts/{id}",
                                                                            "/products/",
                                                                            "/products/s").permitAll()
                                                                    .requestMatchers(HttpMethod.POST,
                                                                            "/accounts/register",
                                                                            "/auth/login",
                                                                            "/auth/logout").permitAll()
                                                                    
                                                                    // private endpoints with ADMIN or USER role
                                                                    .requestMatchers(HttpMethod.GET,
                                                                            "/receipts/",
                                                                            "/receipts/s").hasAnyRole(Constant.ROLE_ADMIN, Constant.ROLE_USER)
                                                                    .requestMatchers(HttpMethod.POST,
                                                                            "/receipts/",
                                                                            "/receipts/cancel").hasAnyRole(Constant.ROLE_ADMIN, Constant.ROLE_USER)
                                                                    .requestMatchers(HttpMethod.PUT,
                                                                            "/receipts/").hasAnyRole(Constant.ROLE_ADMIN, Constant.ROLE_USER)
                                                                    
                                                                    // private endpoints with ADMIN role only
                                                                    .requestMatchers(HttpMethod.GET,
                                                                            "/role-based/admin").hasRole(Constant.ROLE_ADMIN)
                                                                    .requestMatchers(HttpMethod.POST,
                                                                            "/products/",
                                                                            "/customers/").hasRole(Constant.ROLE_ADMIN)
                                                                    .requestMatchers(HttpMethod.PUT,
                                                                            "/products/").hasRole(Constant.ROLE_ADMIN)
                                                                    .requestMatchers(HttpMethod.DELETE,
                                                                            "/products/").hasRole(Constant.ROLE_ADMIN)
                                                                    
                                                                    // private endpoints with USER role only
                                                                    .requestMatchers(HttpMethod.GET,
                                                                            "/role-based/user").hasRole(Constant.ROLE_USER)
                                                                    .requestMatchers(HttpMethod.POST,
                                                                            "/customers/").hasRole(Constant.ROLE_USER)
                                                                    
                                                                    .anyRequest().authenticated())
            
            .oauth2ResourceServer(oauth2 -> oauth2
                                                    .jwt(jwtConfigurer -> jwtConfigurer
                                                                                  .decoder(customJwtDecoder)
                                                                                  .jwtAuthenticationConverter(jwtAuthenticationConverter()))
                                                    .authenticationEntryPoint(new JwtAuthenticationEntryPoint()))
            .formLogin(Customizer.withDefaults())
            .csrf(AbstractHttpConfigurer::disable);
    
    return httpSecurity.build();
  }
  
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    return configuration.getAuthenticationManager();
  }
  
  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService());
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    
    return authenticationProvider;
  }
  
  @Bean
  public UserDetailsService userDetailsService() {
    return new CustomUserDetailsService();
  }
  
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  
  @Bean
  JwtAuthenticationConverter jwtAuthenticationConverter() {
    JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");
    
    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
    
    return jwtAuthenticationConverter;
  }
}
