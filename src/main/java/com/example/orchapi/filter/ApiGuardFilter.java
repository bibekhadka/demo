package com.example.orchapi.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.springframework.stereotype.Component;

@Component 
public class ApiGuardFilter extends AuthenticationFilter {
 
    @Override
    protected boolean onAccessDenied(ServletRequest req, ServletResponse res) throws Exception {
        HttpServletResponse httpResponse = (HttpServletResponse) res;
        httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        httpResponse.setHeader("message", "Authentication failed. Invalid Token.");
        return false;
    }
}