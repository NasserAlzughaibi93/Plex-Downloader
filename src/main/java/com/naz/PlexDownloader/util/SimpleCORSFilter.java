package com.naz.PlexDownloader.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SimpleCORSFilter implements Filter {

    private final Logger log = LoggerFactory.getLogger(SimpleCORSFilter.class);

    private List<String> allowedOrigins;

    public SimpleCORSFilter() {
        log.info("SimpleCORSFilter init");
        allowedOrigins = new ArrayList<>();
//        allowedOrigins.add("https://localhost:8081");
        allowedOrigins.add("http://localhost:4200");//TODO update GUI port.
        allowedOrigins.add("https://localhost:4200");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String allowedOrigin = getOriginToAllow(request.getHeader("Origin"));

        if (allowedOrigin != null) {
            response.setHeader("Access-Control-Allow-Origin", allowedOrigin);
            response.setHeader("Access-Control-Allow-Credentials", "true");
        }

        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");

        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }

    public String getOriginToAllow(String incomingOrigin) {
        if (allowedOrigins.contains(incomingOrigin.toLowerCase())) {
            return incomingOrigin;
        } else {
            return null;
        }
    }
}