package com.ccrt.onlineshop.security;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ccrt.onlineshop.enums.Role;

@EnableWebSecurity
@Configuration
public class SecurityConfigurer {

  public SecurityConfigurer() {
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    AuthenticationConfiguration authenticationConfiguration = httpSecurity
        .getSharedObject(AuthenticationConfiguration.class);
    httpSecurity.cors().and().csrf().disable().authorizeRequests()
        .antMatchers(HttpMethod.POST, "/users/admin").hasAnyAuthority(Role.ADMIN.name())
        .antMatchers(HttpMethod.POST, "/categories").hasAnyAuthority(Role.ADMIN.name())
        .antMatchers(HttpMethod.POST, "/categories/{categoryId}/sub-categories").hasAnyAuthority(Role.ADMIN.name())
        .antMatchers(HttpMethod.PUT, "/categories/{categoryId}").hasAnyAuthority(Role.ADMIN.name())
        .antMatchers(HttpMethod.PUT, "/orders/{orderId}").hasAnyAuthority(Role.ADMIN.name())
        .antMatchers(HttpMethod.PUT, "/categories/{categoryId}/sub-categories/{subCategoryId}")
        .hasAnyAuthority(Role.ADMIN.name())
        .antMatchers(HttpMethod.POST, "/promoted-categories").hasAnyAuthority(Role.ADMIN.name())
        .antMatchers(HttpMethod.PUT, "/donation-requests/{requestId}").hasAnyAuthority(Role.ADMIN.name())
        .antMatchers(HttpMethod.GET, "/donation-requests").hasAnyAuthority(Role.ADMIN.name())
        .antMatchers(HttpMethod.DELETE, "/campaigns/{campaignId}/products/{productId}")
        .hasAnyAuthority(Role.ADMIN.name())
        .antMatchers(HttpMethod.POST, "/campaigns/{campaignId}/products")
        .hasAnyAuthority(Role.ADMIN.name())
        .antMatchers(HttpMethod.POST, "/campaigns")
        .hasAnyAuthority(Role.ADMIN.name())
        .antMatchers(HttpMethod.POST, "/covers").hasAnyAuthority(Role.ADMIN.name())
        .antMatchers(HttpMethod.DELETE, "/covers/{coverId}").hasAnyAuthority(Role.ADMIN.name())
        .antMatchers(HttpMethod.POST, "/products").hasAnyAuthority(Role.ADMIN.name())
        .antMatchers(HttpMethod.PUT, "/products/{productId}").hasAnyAuthority(Role.ADMIN.name())
        .antMatchers(HttpMethod.PUT, "/products/{productId}/image").hasAnyAuthority(Role.ADMIN.name())
        .antMatchers(HttpMethod.PUT, "/products/{productId}/stock").hasAnyAuthority(Role.ADMIN.name())
        .antMatchers(HttpMethod.POST, SecurityConstants.LOG_IN_URL).permitAll()
        .antMatchers(HttpMethod.POST, "/users").permitAll()
        .antMatchers(HttpMethod.GET, "/users").permitAll()
        .antMatchers(HttpMethod.POST, "/users/{userId}/password-reset-verification-code").permitAll()
        .antMatchers(HttpMethod.PUT, "/users/{userId}/password-reset").permitAll()
        .antMatchers(HttpMethod.POST, "/otp").permitAll()
        .antMatchers(HttpMethod.POST, "/otp/validation").permitAll()
        .antMatchers(HttpMethod.GET, "/categories").permitAll()
        .antMatchers(HttpMethod.GET, "/categories/{categoryId}/sub-categories").permitAll()
        .antMatchers(HttpMethod.GET, "/promoted-categories").permitAll()
        .antMatchers(HttpMethod.GET, "/covers").permitAll()

        .antMatchers(HttpMethod.GET, "/products").permitAll()
        .antMatchers(HttpMethod.GET, "/misc/product-search").permitAll()
        .antMatchers(HttpMethod.GET, "/products/{productId}").permitAll()
        .antMatchers(HttpMethod.POST, "/donation-requests").permitAll()
        .antMatchers(HttpMethod.GET, "/campaigns").permitAll()

        .antMatchers(HttpMethod.GET, "/campaigns/{campaignId}/products").permitAll()

        .anyRequest()
        .authenticated().and()
        .addFilter(new AuthenticationFilter(authenticationManager(authenticationConfiguration)))
        .addFilter(new AuthorizationFilter(authenticationManager(authenticationConfiguration)))
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    return httpSecurity.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
      throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    final CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
    corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT", "OPTIONS"));
    corsConfiguration.setAllowCredentials(false);
    corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
    corsConfiguration.setExposedHeaders(Arrays.asList("Authorization", "UserId"));

    Map<String, CorsConfiguration> mapping = new HashMap<>();
    mapping.put("/**", corsConfiguration);
    UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
    urlBasedCorsConfigurationSource.setCorsConfigurations(mapping);
    return urlBasedCorsConfigurationSource;
  }

}
