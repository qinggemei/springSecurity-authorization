package com.star.config;

import com.star.config.handler.*;
import com.star.service.StarUserDetailsServce;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.UrlAuthorizationConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.annotation.Resource;

/**
 * @author liuxing
 * @description spring security配置
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class StarSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private StarUserDetailsServce starUserDetailsServce;

    @Resource
    private StarFilterInvocationSecurityMetadataSource metadataSource;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ApplicationContext applicationContext = http.getSharedObject(ApplicationContext.class);
        http.apply(new UrlAuthorizationConfigurer<>(applicationContext))
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setSecurityMetadataSource(metadataSource);
                        object.setRejectPublicInvocations(true);
                        return object;
                    }
                });

        http.authorizeHttpRequests()
                .mvcMatchers("/admin", "/user", "/getInfo").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .successHandler(new StarAuthenticationSuccessHandler())
                .failureHandler(new StarAuthenticationFailureHandler())
                .and()
                .logout()
                .logoutSuccessHandler(new StarLogoutSuccessHandler())
                .and()
                .exceptionHandling().accessDeniedHandler(new StarAccessDeniedHandler())
                .and()
                .rememberMe()
                .rememberMeServices()
                .and()
                .csrf().disable()
                .sessionManagement()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true)
                .expiredSessionStrategy(new StarSessionInformationExpiredStrategy())
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(starUserDetailsServce).passwordEncoder(delegatingPasswordEncoder());
    }

    @Bean
    public PasswordEncoder delegatingPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }


}
