package koo.test.reslience4jLimit.component;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;

import java.time.Duration;
import java.util.Map;

public class ProxyRateLimiter implements RateLimiter {

    private volatile RateLimiter delegate;

    public ProxyRateLimiter(RateLimiter newLimiter){
        this.delegate = newLimiter;
    }

    public void swap(RateLimiter newLimiter){
        this.delegate = newLimiter;
    }

    @Override
    public void changeTimeoutDuration(Duration timeoutDuration) {
        delegate.changeTimeoutDuration(timeoutDuration);
    }

    @Override
    public void changeLimitForPeriod(int limitForPeriod) {
        delegate.changeLimitForPeriod(limitForPeriod);
    }

    @Override
    public boolean acquirePermission(int permits) {
        return delegate.acquirePermission(permits);
    }

    @Override
    public long reservePermission(int permits) {
        return delegate.reservePermission(permits);
    }

    @Override
    public void drainPermissions() {
        delegate.drainPermissions();
    }

    @Override
    public String getName() {
        return delegate.getName();
    }

    @Override
    public RateLimiterConfig getRateLimiterConfig() {
        return delegate.getRateLimiterConfig();
    }

    @Override
    public Map<String, String> getTags() {
        return delegate.getTags();
    }

    @Override
    public Metrics getMetrics() {
        return delegate.getMetrics();
    }

    @Override
    public EventPublisher getEventPublisher() {
        return delegate.getEventPublisher();
    }
}
