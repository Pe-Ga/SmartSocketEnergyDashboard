package at.technikum.SmartSocketEnergyDashboard.services;

import at.technikum.SmartSocketEnergyDashboard.controllers.HomeController;
import at.technikum.SmartSocketEnergyDashboard.models.SmartSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class SmartSocketService {

    Logger logger = LoggerFactory.getLogger(SmartSocketService.class);

    private final String endpointUrl = "http://192.168.0.57/cm?cmnd=EnergyTotal";

    private final RestTemplate restTemplate;

    public SmartSocketService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Scheduled(fixedRate = 9000)
    public SmartSocket callTasmotaEndpoint() {
        logger.info("callTasmotaEndpoint");
        SmartSocket smartSocket  = restTemplate.getForObject(endpointUrl, SmartSocket.class);
        logger.info("Response: {}", smartSocket.toString());
        return smartSocket;
    }

}
