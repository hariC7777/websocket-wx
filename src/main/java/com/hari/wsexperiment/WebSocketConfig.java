package com.hari.wsexperiment;

/**
 * @Auther: Hari
 * @Date: 2020/12/23 14:11
 * @Description:
 */


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;


/**
 * WebSocketConfig配置
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private WebSocketDecoratorFactory webSocketDecoratorFactory;
    @Autowired
    private PrincipalHandshakeHandler principalHandshakeHandler;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        /**
         * myUrl表示 你前端到时要对应url映射
         */
        registry.addEndpoint("/myUrl").setAllowedOrigins("*").setHandshakeHandler(principalHandshakeHandler).withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        /**
         * queue 点对点
         * topic 广播
         * user 点对点前缀
         */
        registry.enableSimpleBroker("/queue", "/topic");
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.addDecoratorFactory(webSocketDecoratorFactory);
    }
}