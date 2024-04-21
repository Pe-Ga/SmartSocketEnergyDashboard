package at.technikum.SmartSocketEnergyDashboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class Config {

    @Bean
    public WebClient tasmotaApiClient() {
        return WebClient.create("192.168.0.57/cm?cmnd=EnergyTotal");
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
