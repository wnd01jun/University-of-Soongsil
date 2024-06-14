package network.freeTopic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;


@EnableJpaAuditing
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class FreeTopicApplication {

	public static void main(String[] args) {
		SpringApplication.run(FreeTopicApplication.class, args);
	}

	@Bean
	public PageableHandlerMethodArgumentResolverCustomizer customize() {
		return p -> {
			p.setOneIndexedParameters(true);
			p.setMaxPageSize(10);
		};
	}
}
