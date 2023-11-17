package com.binarfud.challenge7.Config;//package com.binarfud.challenge6.Config;
//
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//public class SecurityServletFilter extends HttpFilter {
//
//    @Override
//    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        UsernamePasswordAuthenticationToken token = extractUsernamePasswordToken(request);
//        if(notAuthenticated(token)) {
//            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//            return;
//        }
//        if(notAuthorized(token, request)) {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            return;
//        }
//        chain.doFilter(request, response);
//    }
//
//    private UsernamePasswordAuthenticationToken extractUsernamePasswordToken(HttpServletRequest request) {
//        return checkVariousLoginOptions(request);
//    }
//
//    private UsernamePasswordAuthenticationToken checkVariousLoginOptions(HttpServletRequest request) {
//    }
//
//    private boolean notAuthenticated(UsernamePasswordAuthenticationToken token) {
//        return false;
//    }
//
//    private boolean notAuthorized(UsernamePasswordAuthenticationToken token, HttpServletRequest request) {
//        return false;
//    }
//}
