package com.example.orchapi.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.stereotype.Component;

@Component
public class JWTVerifyingFilter extends AccessControlFilter{    
    
    
    String message;

    @Override
    protected boolean isAccessAllowed(ServletRequest req, ServletResponse res, Object o) throws Exception {
        boolean accessAllowed = false;
        HttpServletRequest request = (HttpServletRequest) req;
        String jwtToken = request.getHeader("Authorization");
        if (jwtToken != null) {
            accessAllowed = true;
        } else {
             this.message = "NO TOKEN!";
        }
        return accessAllowed;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest req, ServletResponse res) throws Exception {
         HttpServletResponse response = (HttpServletResponse) res;
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setHeader("message", message);
        return false;
    }
    
}
