package com.greenUs.server.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	private String resourcePath = "/upload/**"; // view 에서 접근할 경로
	private String savePath = "file:///C:/springboot_img/"; // 실제 파일 저장 경로

	// view에서 해당 경로로 접근하게 되면 스프링이 실제 파일 경로에서 찾게 해줌
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(resourcePath)
			.addResourceLocations(savePath);
	}
}
