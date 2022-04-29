package com.polyrepo.analyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableFeignClients
@EnableAutoConfiguration
public class AnalyzerApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(AnalyzerApplication.class, args);
	}

//	@Override
//	public void configurePathMatch(PathMatchConfigurer configurer) {
//		UrlPathHelper urlPathHelper = new UrlPathHelper();
//		urlPathHelper.setUrlDecode(false);
//		configurer.setUrlPathHelper(urlPathHelper);
//	}
}
