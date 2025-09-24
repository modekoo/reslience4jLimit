package koo.test.reslience4jLimit;

import com.google.gson.Gson;
import io.github.resilience4j.ratelimiter.RateLimiter;
import koo.test.reslience4jLimit.dto.LimitDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class Reslience4jLimitApplicationTests {

	@Autowired
	RateLimiter limiter;

	@Test
	void test() {
		log.debug(limiter.getName());
		for (int i = 1; i <= 10; i++) {
			boolean permitted = limiter.acquirePermission();
			System.out.println("요청 " + i + " : " + (permitted ? "허용" : "차단"));
		}
	}

	@Test
	void test2(){
		String ttt = "{\"limitForPeriod\":10,\"limitRefreshPeriod\":30}";



		LimitDTO limitDTO = new Gson().fromJson(ttt, LimitDTO.class);

		Assertions.assertEquals(10, limitDTO.getLimitForPeriod());

	}

}
