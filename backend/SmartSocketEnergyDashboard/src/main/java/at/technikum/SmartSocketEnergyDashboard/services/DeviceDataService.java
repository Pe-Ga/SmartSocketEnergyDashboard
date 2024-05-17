package at.technikum.SmartSocketEnergyDashboard.services;

import at.technikum.SmartSocketEnergyDashboard.dtos.DeviceDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class DeviceDataService {

    private static final Logger logger = LoggerFactory.getLogger(DeviceDataService.class);

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final static String DEVICE_COMMAND = "status0";


    public DeviceDataService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public Optional<DeviceDTO> fetchDeviceStatus(String ipAddress) {
        String endpointUrl = buildEndpointUrl(ipAddress, DEVICE_COMMAND);
        try {
            String response = restTemplate.getForObject(endpointUrl, String.class);
            DeviceDTO deviceDTO = objectMapper.readValue(response, DeviceDTO.class);
            return Optional.of(deviceDTO);
        } catch (JsonProcessingException e) {
            logger.error("Failed to parse JSON response from device at IP: {}", ipAddress, e);
            throw new RuntimeException("Error parsing device data", e);
        } catch (ResourceAccessException e) {
            logger.warn("Device at IP: {} is not reachable", ipAddress);
            return Optional.empty();  // Return an empty Optional if the device is not reachable
        } catch (RestClientException e) {
            logger.error("HTTP request failed for device at IP: {}", ipAddress, e);
            throw new RuntimeException("Error communicating with device", e);
        }
    }

    public String buildEndpointUrl(String ipAddresse, String command) {
        logger.info(String.format("Send Command: %s to IP: %s",command, ipAddresse));
        return String.format("http://%s/cm?cmnd=%S",ipAddresse, command);
    }
}
