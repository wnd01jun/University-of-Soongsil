package sakura.softwareProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SoftwareProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoftwareProjectApplication.class, args);
	}
}
