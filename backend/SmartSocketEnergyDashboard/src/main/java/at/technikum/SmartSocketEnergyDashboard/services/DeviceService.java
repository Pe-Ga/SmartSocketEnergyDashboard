package at.technikum.SmartSocketEnergyDashboard.services;

import at.technikum.SmartSocketEnergyDashboard.dtos.DeviceDTO;
import at.technikum.SmartSocketEnergyDashboard.entities.DeviceEntity;
import at.technikum.SmartSocketEnergyDashboard.models.Device;
import at.technikum.SmartSocketEnergyDashboard.repositories.DeviceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceService {

    private static final Logger logger = LoggerFactory.getLogger(DeviceService.class);
    private final DeviceRepository deviceRepository;


    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;

    }

    public DeviceDTO saveDevice(DeviceDTO deviceDTO) {
        DeviceEntity deviceEntity = convertToEntity(deviceDTO);
        DeviceEntity savedEntity = deviceRepository.save(deviceEntity);
        return convertToDTO(savedEntity);
    }


    public List<DeviceEntity> getAllDevices() {
        return deviceRepository.findAll();
    }

    public DeviceDTO convertToDTO(DeviceEntity entity) {
        DeviceDTO dto = new DeviceDTO();
        dto.setName(entity.getName());
        dto.setIpAddress(entity.getIpAddress());
        return dto;
    }

    public DeviceEntity convertToEntity(DeviceDTO deviceDTO) {
        // Convert DTO to Entity
        DeviceEntity deviceEntity = new DeviceEntity();
        deviceEntity.setName(deviceDTO.getName());
        deviceEntity.setIpAddress(deviceDTO.getIpAddress());
        return deviceEntity;
    }

    public boolean updateDeviceName(String ipAddress, String newName) {
        String encodeSpace = "%20";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("http://%s/cm?cmnd=DeviceName%s%s",ipAddress, encodeSpace, newName)))
                .POST(HttpRequest.BodyPublishers.ofString(newName))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 200;
        } catch (IOException | InterruptedException e) {
            logger.error("Failed to update device name on the device", e);
            return false;
        }
    }
}
