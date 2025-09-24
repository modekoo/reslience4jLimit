package koo.test.reslience4jLimit.config;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import koo.test.reslience4jLimit.component.ProxyRateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class LimitConfig {
    @Bean
    public ProxyRateLimiter apiFlowRateLimiter() {
        // 설정값 직접 정의
        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitForPeriod(5)                // 주기당 허용 횟수 (테스트: 5번)
                .limitRefreshPeriod(Duration.ofSeconds(10)) // 주기 길이 (10초마다 리셋)
                .timeoutDuration(Duration.ZERO)   // 대기 시간 (0 → 바로 차단)
                .build();

        // 레지스트리에 등록 후 인스턴스 생성
        RateLimiter limit = RateLimiter.of("apiFlow", config);
        return new ProxyRateLimiter(limit);
    }
}
