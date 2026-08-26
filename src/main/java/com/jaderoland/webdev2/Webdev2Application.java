package com.jaderoland.webdev2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Webdev2Application {

	public static void main(String[] args) {
		SpringApplication.run(Webdev2Application.class, args);
	}

	@Bean
	public CommandLineRunner startupReport(ProductService productService) {
		return args -> {
			double threshold = 5000;

			System.out.println("================================");
			System.out.println("       PRODUCT REPORT");
			System.out.println("================================");
			productService.getShopInfo();
			System.out.println();
			System.out.println("Products above " + threshold + ":");
			System.out.println();

			for (Product p : productService.filterProducts(threshold)) {
				System.out.println(p.getName() + " - " + p.getPrice());
			}

			System.out.println("================================");
		};
	}
}
