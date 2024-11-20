package com.zync.network.core.config;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.zync.network.core.domain.ZID;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ua_parser.Parser;

import java.util.concurrent.Executors;

@Configuration
public class WebGlobalConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new ZIDConverter());
    }

    @Bean
    public SimpleModule simpleModule(){
        // Create a new module
        SimpleModule module = new SimpleModule();

        // Register the custom serializer and deserializer for Date class
        module.addSerializer(ZID.class, new ZIDSerializer());
        module.addDeserializer(ZID.class, new ZIDDeserializer());
        return module;
    }
    @Bean
    public Parser parser(){
        // Create a new module
        return new Parser();

    }

    //async worker
    @Bean(TaskExecutionAutoConfiguration.APPLICATION_TASK_EXECUTOR_BEAN_NAME)
    public TaskExecutor applicationTaskExecutor() {
        return new TaskExecutorAdapter(Executors.newVirtualThreadPerTaskExecutor());
    }

    @Bean
    //set visual thread
    public TomcatProtocolHandlerCustomizer<?> protocolHandlerVirtualThreadExecutorCustomizer() {
        return protocolHandler -> {
            protocolHandler.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
        };
    }
}
