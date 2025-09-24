package koo.test.reslience4jLimit.component;

import com.google.gson.Gson;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import koo.test.reslience4jLimit.dto.LimitDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class FlowControlListener implements MessageListener {

    private final ProxyRateLimiter proxy;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String cmd = new String(message.getBody(), StandardCharsets.UTF_8).trim();
        log.debug(cmd);
        LimitDTO limitDTO = new Gson().fromJson(cmd, LimitDTO.class);
        apiFlowRateLimiter(limitDTO);
    }

    public void apiFlowRateLimiter(LimitDTO limitDTO) {
        // 설정값 직접 정의
        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitForPeriod(limitDTO.getLimitForPeriod())                // 주기당 허용 횟수 (테스트: 5번)
                .limitRefreshPeriod(Duration.ofSeconds(limitDTO.getLimitRefreshPeriod())) // 주기 길이 (10초마다 리셋)
                .timeoutDuration(Duration.ZERO)   // 대기 시간 (0 → 바로 차단)
                .build();

        // 레지스트리에 등록 후 인스턴스 생성
        RateLimiter limiter = RateLimiter.of("newApiFlow", config);
        proxy.swap(limiter);
    }
}
