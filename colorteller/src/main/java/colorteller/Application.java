package colorteller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

	final private String DEFAULT_COLOR = "black";

	@RequestMapping("/")
	public String getColor() {
		String color = System.getenv("COLOR");
		if (color == null) {
			color = DEFAULT_COLOR;
		}
		return "{ \"color\": \"" + color + "\" }";
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
