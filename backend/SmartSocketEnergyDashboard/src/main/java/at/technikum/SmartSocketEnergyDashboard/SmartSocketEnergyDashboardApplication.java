package at.technikum.SmartSocketEnergyDashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SmartSocketEnergyDashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartSocketEnergyDashboardApplication.class, args);
	}
}
