package koo.test.reslience4jLimit.config;

import koo.test.reslience4jLimit.component.FlowControlListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class RedisConfig {
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory cf, FlowControlListener listener) {
        RedisMessageListenerContainer c = new RedisMessageListenerContainer();
        c.setConnectionFactory(cf);
        c.addMessageListener(new MessageListenerAdapter(listener, "onMessage"),
                new PatternTopic("koo:apiFlow"));
        return c;
    }
}
