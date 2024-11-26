package com.api.RecordTimeline.global.config;

import com.api.RecordTimeline.global.security.jwt.AuthHandshakeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSoketConfig  implements WebSocketMessageBrokerConfigurer  {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 클라이언트로 보낼 메시지의 prefix
        config.enableSimpleBroker("/topic");

        // 서버로 메시지를 보낼 때 사용하는 prefix
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .addInterceptors(new AuthHandshakeInterceptor()) // 인증 인터셉터 추가
                .withSockJS()
                .setHeartbeatTime(60000);  // 60초마다 heart-beat 보내도록 설정
    }
}
