package com.crio.qeats.config;

import java.util.concurrent.Executor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

// COMPLETED: CRIO_TASK_MODULE_MULTITHREADING
// Spring uses certain tags which help asynchronous execution.
// Use the class level tag for this module.
// Hint: This class implements AsyncConfigurer, so perhaps a tag in connection to that?
@Configuration
@EnableAsync
public class SpringAsyncConfiguration implements AsyncConfigurer {

  @Override
  @Bean(name = "restaurantExecutor")
  public Executor getAsyncExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setMaxPoolSize(4);
    executor.setCorePoolSize(4);
    executor.setQueueCapacity(500);
    executor.setThreadNamePrefix("RestaurantSearch-");
    executor.initialize();
    return executor;
  }
}
