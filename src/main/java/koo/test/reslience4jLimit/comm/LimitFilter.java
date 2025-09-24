package koo.test.reslience4jLimit.comm;

import io.github.resilience4j.ratelimiter.RateLimiter;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import koo.test.reslience4jLimit.component.ProxyRateLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class LimitFilter implements Filter {

    private final ProxyRateLimiter limiter;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        log.debug(req.getMethod());
        log.debug(limiter.getName());

        if(!limiter.acquirePermission()) {
            res.setStatus(429);
            return;
        }

        filterChain.doFilter(servletRequest, res);
    }
}
