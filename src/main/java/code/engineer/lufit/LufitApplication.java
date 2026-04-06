package code.engineer.lufit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class LufitApplication {

	public static void main(String[] args) {
		SpringApplication.run(LufitApplication.class, args);
	}

}
