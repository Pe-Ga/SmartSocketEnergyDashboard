package at.technikum.SmartSocketEnergyDashboard.config;


import at.technikum.SmartSocketEnergyDashboard.util.DeviceDataLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    @Bean
    public DeviceDataLogger deviceDataLogger() {
        return new DeviceDataLogger();
    }

}
