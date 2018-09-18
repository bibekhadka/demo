package com.example.orchapi.common.config;

import com.example.orchapi.filter.ApiGuardFilter;
import com.example.orchapi.filter.JWTVerifyingFilter;
import com.example.orchapi.filter.LimitedAccessJWTVerifyingFilter;
import com.example.orchapi.logic.security.AdminRealm;
import java.util.Map;
import javax.servlet.Filter;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

@Configuration
public class ShiroConfig {

    @Bean
    public Realm realm() {
        AdminRealm adminRealm = new AdminRealm();
        adminRealm.setCredentialsMatcher(sha256Matcher());
        adminRealm.setPermissionsLookupEnabled(true);
        adminRealm.setSaltStyle(JdbcRealm.SaltStyle.COLUMN);
        return adminRealm;
    }

    @Bean
    public HashedCredentialsMatcher sha256Matcher() {
        HashedCredentialsMatcher sha256Matcher = new HashedCredentialsMatcher();
        sha256Matcher.setHashAlgorithmName("SHA-256");
        return sha256Matcher;
    }

    @Bean
    public CacheManager cacheManager() {
        return new MemoryConstrainedCacheManager();
    }

    @Bean
    public Filter jwtg() {
        return new ApiGuardFilter();
    }

    @Bean
    public Filter jwtv() {
        return new JWTVerifyingFilter();
    }

    @Bean
    public Filter ljwtv() {
        return new LimitedAccessJWTVerifyingFilter();
    }

    @Bean
    public FilterRegistrationBean logout() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setRedirectUrl("/login.jsp");
        filterRegistrationBean.setFilter(logoutFilter);
        filterRegistrationBean.addUrlPatterns("/logout.htm");
        return filterRegistrationBean;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();

        chainDefinition.addPathDefinition("/login.jsp", "authc");
        chainDefinition.addPathDefinition("/swagger-ui.html", "authc");
        chainDefinition.addPathDefinition("/api-docs", "authc");
        chainDefinition.addPathDefinition("/logout.htm", "logout");
        chainDefinition.addPathDefinition("/api/test/**", "anon");
        chainDefinition.addPathDefinition("/api/users/signup", "anon");
        chainDefinition.addPathDefinition("/api/users/signin", "anon");
        chainDefinition.addPathDefinition("/api/users/**/agreement", "ljwtv");
        chainDefinition.addPathDefinition("/api/users/**/policy", "ljwtv");
        chainDefinition.addPathDefinition("/api/**", "jwtv");

        return chainDefinition;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Value("#{ @environment['shiro.loginUrl'] ?: '/login.jsp' }") String loginUrl,
            @Value("#{ @environment['shiro.successUrl'] ?: '/' }") String successUrl,
            @Value("#{ @environment['shiro.unauthorizedUrl'] ?: null }") String unauthorizedUrl,
            SecurityManager securityManager,
            ShiroFilterChainDefinition shiroFilterChainDefinition,
            Map<String, Filter> filterMap) {

        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();

        filterFactoryBean.setLoginUrl(loginUrl);
        filterFactoryBean.setSuccessUrl(successUrl);
        filterFactoryBean.setUnauthorizedUrl(unauthorizedUrl);

        filterFactoryBean.setSecurityManager(securityManager);
        filterFactoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition.getFilterChainMap());
        filterFactoryBean.setFilters(filterMap);

        return filterFactoryBean;
    }

}
