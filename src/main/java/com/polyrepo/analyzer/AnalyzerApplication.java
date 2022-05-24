package com.polyrepo.analyzer;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

@SpringBootApplication
@EnableSwagger2
@EnableFeignClients
@EnableAutoConfiguration
public class AnalyzerApplication implements WebMvcConfigurer {

	public static void main(String[] args) throws IOException {


		ClassLoader classLoader = AnalyzerApplication.class.getClassLoader();

		File file = new File(Objects.requireNonNull(classLoader.getResource("ServiceAccountKey.json")).getFile());
		FileInputStream serviceAccount = new FileInputStream(file.getAbsolutePath());


		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.build();

		FirebaseApp.initializeApp(options);


		SpringApplication.run(AnalyzerApplication.class, args);
	}

	/**
	 * @Override
	 *        public void configurePathMatch(PathMatchConfigurer configurer) {
	 * 		UrlPathHelper urlPathHelper = new UrlPathHelper();
	 * 		urlPathHelper.setUrlDecode(false);
	 * 		configurer.setUrlPathHelper(urlPathHelper);
	 *    }
	 *
	 */

}
