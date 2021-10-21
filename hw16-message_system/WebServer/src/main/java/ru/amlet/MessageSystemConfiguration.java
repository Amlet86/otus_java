package ru.amlet;

import messagesystem.MessageSystem;
import messagesystem.MessageSystemImpl;
import messagesystem.client.CallbackRegistry;
import messagesystem.client.CallbackRegistryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageSystemConfiguration {

    @Bean
    MessageSystem createMessageSystem() {
        return new MessageSystemImpl();
    }

    @Bean
    CallbackRegistry createCallbackRegistry() {
        return new CallbackRegistryImpl();
    }
}
