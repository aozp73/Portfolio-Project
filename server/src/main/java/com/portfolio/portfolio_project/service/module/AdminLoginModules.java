package com.portfolio.portfolio_project.service.module;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


public class AdminLoginModules {
    
    public static void setRemeberEmail(HttpServletResponse response, String remember, String email){

        if (remember.equals("on")) {
            Cookie cookie = new Cookie("remember", email);
            response.addCookie(cookie);
        } else {
            Cookie cookie = new Cookie("remember", "");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
        
    }
}
