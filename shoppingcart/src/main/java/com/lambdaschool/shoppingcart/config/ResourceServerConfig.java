package com.lambdaschool.shoppingcart.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    private String RESOURCE_ID = "resource_id";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(RESOURCE_ID)
                .stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // cross-site request forgery -- "difficult to deal with"
        http.csrf().disable();

        // required for the H2 console
        http.headers().frameOptions().disable();

        // the built-in logout is "clunky" -- we'll implement our own
        http.logout().disable();

        http.authorizeRequests()
                .antMatchers("/",
                        "/h2-console/**",
                        "/swagger-resources/**",
                        "/swagger-resource/**",
                        "/swagger-ui.html",
                        "/v2/api-docs",
                        "/webjars/**",
                        "/createnewuser").permitAll()
                .antMatchers("/roles/**").hasAnyRole("ADMIN")
                .antMatchers("/products/**").hasAnyRole("ADMIN")
                .antMatchers("/users/user/**").hasAnyRole("ADMIN")
                .antMatchers("/users/**").authenticated()
                .antMatchers("/carts/**").authenticated()
                .anyRequest()
                .denyAll()
                .and().exceptionHandling()
                .accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }
}