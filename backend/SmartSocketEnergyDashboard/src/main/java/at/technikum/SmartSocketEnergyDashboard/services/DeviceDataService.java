package at.technikum.SmartSocketEnergyDashboard.services;

import at.technikum.SmartSocketEnergyDashboard.dtos.DeviceDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DeviceDataService {

    private final RestTemplate restTemplate;
    private final String endpointUrl;
    private final ObjectMapper objectMapper;


    public DeviceDataService(RestTemplate restTemplate,
                             @Value("${smartsocket.endpointUrl}") String endpointUrl, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.endpointUrl = endpointUrl;
        this.objectMapper = objectMapper;
    }

    public DeviceDTO fetchDeviceData() throws JsonProcessingException {
        String response = restTemplate.getForObject(endpointUrl, String.class);
        return objectMapper.readValue(response, DeviceDTO.class);
    }
}
