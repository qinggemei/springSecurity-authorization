package com.star.config;

import com.star.config.handler.*;
import com.star.filter.LoginFilter;
import com.star.service.StarPersistentTokenBasedRememberMeServices;
import com.star.service.StarUserDetailsServce;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.UrlAuthorizationConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.UUID;

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

    @Resource
    private DataSource dataSource;



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ApplicationContext applicationContext = http.getSharedObject(ApplicationContext.class);
        // 配置授权
        http.apply(new UrlAuthorizationConfigurer<>(applicationContext))
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setSecurityMetadataSource(metadataSource);
                        object.setRejectPublicInvocations(false);
                        return object;
                    }
                });
        http.authorizeHttpRequests()
                .mvcMatchers("/admin", "/user", "/getInfo","/vc.jpg").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutSuccessHandler(new StarLogoutSuccessHandler())
                .and()
                .exceptionHandling().accessDeniedHandler(new StarAccessDeniedHandler())
                .and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .and()
                .csrf().disable()
                .sessionManagement()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true)
                .expiredSessionStrategy(new StarSessionInformationExpiredStrategy())
        ;
        http.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);
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

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }



    @Bean
    public LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setAuthenticationManager(authenticationManagerBean());
        loginFilter.setRememberMeServices(rememberMeServices());
        loginFilter.setAuthenticationFailureHandler(new StarAuthenticationFailureHandler());
        loginFilter.setAuthenticationSuccessHandler(new StarAuthenticationSuccessHandler());
        loginFilter.setFilterProcessesUrl("/doLogin");
        return loginFilter;
    }


    /**
     * 持久化
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        // 只需要没有表时设置为 true
        jdbcTokenRepository.setCreateTableOnStartup(false);
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    @Bean
    public RememberMeServices rememberMeServices() {
        // 自定义，前后端分离
        return new StarPersistentTokenBasedRememberMeServices(
                UUID.randomUUID().toString()
                , starUserDetailsServce
                , new JdbcTokenRepositoryImpl());
    }
}
