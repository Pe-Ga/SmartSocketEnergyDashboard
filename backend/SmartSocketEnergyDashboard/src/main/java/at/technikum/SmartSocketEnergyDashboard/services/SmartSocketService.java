package at.technikum.SmartSocketEnergyDashboard.services;

import at.technikum.SmartSocketEnergyDashboard.models.SmartSocket;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import static at.technikum.SmartSocketEnergyDashboard.services.JsonParser.parseEnergyData;

@Service
public class SmartSocketService {

    Logger logger = LoggerFactory.getLogger(SmartSocketService.class);

    private final String endpointUrl = "http://192.168.0.57/cm?cmnd=EnergyTotal";

    private final RestTemplate restTemplate;
    private final MeterRegistry meterRegistry;

    public SmartSocketService(RestTemplate restTemplate, MeterRegistry meterRegistry) {
        this.restTemplate = restTemplate;
        this.meterRegistry = meterRegistry;
    }

    @Scheduled(fixedRate = 60000)
    public void callTasmotaEndpoint() throws Exception {
        try {
            String response = restTemplate.getForObject(endpointUrl, String.class);
            double totalEnergy = JsonParser.extractTotalFromJson(response);
            logger.info("Total Energy: {}", totalEnergy);
        } catch (Exception e) {
            logger.error("Error processing JSON response: {}", e.getMessage());
        }

    }

    private void reportEnergyMetrics(double totalEnergy) {
        // Register a gauge metric for total energy with Micrometer
        meterRegistry.gauge("energy.total", totalEnergy);
        logger.info("Total Energy reported: {}", totalEnergy);
    }

}
