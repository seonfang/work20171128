package com.example.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.ServletRequestHandledEvent;


@SpringBootApplication
public class BasicApplication {

	//问题4：使用log4j2来做日志系统
	private static final Logger log = LoggerFactory.getLogger(BasicApplication.class);
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(BasicApplication.class, args);
		//问题5.    定义一个TaijCongfiguration,利用FooProperties的所有属性(包括集合等)
		System.out.println(context.getBean(FooProperties.class));
//		SpringApplication.run(BasicApplication.class, args);
		
	}
	
	/**
	 * 
	*问题 2.    访问http://localhost:8080/em/health能看到是否能访问互联网
	 * @author seon
	 *
	 */
	@Component
	public class MyHealthIndicator implements HealthIndicator {
	 @Override
	 public Health health() {
	 int errorCode = check(); // perform some specific health check
	 if (errorCode != 0) {
	 return Health.down().withDetail("Error Code", errorCode).build();
	 }
	 return Health.up().withDetail("True Code", "True").build();
	 }

	private int check() {
		// TODO Auto-generated method stub
		
		return 0;
	}
	}
	
	/**
	 * 
	 * 问题 3.    访问 http://localhost:8080/em/metrics能访问到访问http://localhost:8080/xyz的信息
	 * http://localhost:8080/em/xyz 访问
	 * http://localhost:8080/em/metrics 查看
	 */
	@Autowired
	private CounterService counterService;
	
	@Bean
	public ApplicationListener<ApplicationEvent> helloListener() {
		final String XYZ_URL = "/xyz";

		return (ApplicationEvent event) -> {
			if (event instanceof ServletRequestHandledEvent) {
				ServletRequestHandledEvent e = (ServletRequestHandledEvent) event;
				if (e.getRequestUrl().equals(XYZ_URL))
					counterService.increment("xyz.hits");
			}
		};
	}
	
}
