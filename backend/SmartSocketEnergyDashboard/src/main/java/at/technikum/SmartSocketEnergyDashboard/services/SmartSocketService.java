package at.technikum.SmartSocketEnergyDashboard.services;

import at.technikum.SmartSocketEnergyDashboard.dtos.DeviceDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;


@Service
public class SmartSocketService {

    private static final Logger logger = LoggerFactory.getLogger(SmartSocketService.class);

    private final String endpointUrl;
    private final RestTemplate restTemplate;
    private final WriteApiBlocking writeApi;

    private final String bucket;
    private final String org;

    private final ObjectMapper objectMapper;

    @Autowired
    public SmartSocketService(RestTemplate restTemplate, InfluxDBClient influxDBClient,
                              @Value("${influxdb.bucket}") String bucket,
                              @Value("${influxdb.org}") String org,
                              @Value("${smartsocket.endpointUrl}") String endpointUrl, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.endpointUrl = endpointUrl;
        this.writeApi = influxDBClient.getWriteApiBlocking();
        this.bucket = bucket;
        this.org = org;
        this.objectMapper = objectMapper;
    }

    @Scheduled(fixedRate = 60000)
    public void getTotalEnergyAndStoreInDB() {
        try {
            String response = restTemplate.getForObject(endpointUrl, String.class);
            DeviceDTO deviceDTO = objectMapper.readValue(response, DeviceDTO.class);
            logger.info(deviceDTO.toString());




        } catch (ResourceAccessException e) {
            logger.error("Error connecting to the endpoint: {}", e.getMessage());
        } catch (HttpServerErrorException | HttpClientErrorException e) {
            logger.error("HTTP error when accessing the endpoint: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Error processing JSON response: {}", e.getMessage());
        }
    }
}
