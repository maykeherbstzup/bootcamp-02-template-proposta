package com.nossocartao.proposta.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .antMatchers(HttpMethod.GET, "/api/proposal/**").hasAuthority("nossoCartao:read")
                                .antMatchers(HttpMethod.POST, "/api/proposal").hasAuthority("nossoCartao:write")
                                .antMatchers(HttpMethod.POST, "/api/credit-card/{id}/biometry").hasAuthority(
                                        "nossoCartao:write")
                                .antMatchers(HttpMethod.POST, "/api/credit-card/{id}/block").hasAuthority(
                                        "nossoCartao:write")
                                .antMatchers("/actuator/prometheus*").permitAll()
                                .anyRequest().authenticated()
                )
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
    }
}
