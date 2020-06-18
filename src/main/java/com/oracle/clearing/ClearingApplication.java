package com.oracle.clearing;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.shell.jline.PromptProvider;

@SpringBootApplication
public class ClearingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClearingApplication.class, args);
	}

	@Bean
	public PromptProvider myPromptProvider() {
		return () -> new AttributedString("clearing >>", AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
	}
}
