package com.trivago.subscription;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
		title = "Subscription API",
		description = "Subscription API doc for hoteliers.",
		version = "1.0.0",
		contact = @Contact(
				name = "Ravikant Pal",
				email = "iamravikantpal@gmail.com"
		)
))
public class SubscriptionApplication {

	public static void main(String[] args) {
		SpringApplication.run(SubscriptionApplication.class, args);
	}

}
