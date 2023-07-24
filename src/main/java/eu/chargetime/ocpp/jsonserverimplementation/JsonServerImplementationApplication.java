package eu.chargetime.ocpp.jsonserverimplementation;

import eu.chargetime.ocpp.jsonserverimplementation.config.DatabaseConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;

@SpringBootApplication(exclude = { R2dbcAutoConfiguration.class })
public class JsonServerImplementationApplication {
	public static void start(){
		System.out.println("Server running on port: "+ DatabaseConfiguration.CONFIG.getDb().getPort());
	}
	public static void main(String[] args) {
		SpringApplication.run(JsonServerImplementationApplication.class, args);
	}

}
