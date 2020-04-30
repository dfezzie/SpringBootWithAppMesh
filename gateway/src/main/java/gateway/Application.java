package gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@SpringBootApplication
@RestController
public class Application {
	private final String DEFAULT_COLOR_TELLER = "http://localhost:8080";

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	public String callColorTeller(RestTemplate restTemplate) {
		String url = System.getenv("COLOR_TELLER_ENDPOINT");
		if (url == null) {
			url = DEFAULT_COLOR_TELLER;
		}
		Color color = restTemplate.getForObject(url, Color.class);
		return color.getColor();
	}

	@RequestMapping("/")
	public String getColor(RestTemplate restTemplate) {
		return callColorTeller(restTemplate);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
