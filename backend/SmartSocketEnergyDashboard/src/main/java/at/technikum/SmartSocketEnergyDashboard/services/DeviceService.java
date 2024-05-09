package at.technikum.SmartSocketEnergyDashboard.services;

import at.technikum.SmartSocketEnergyDashboard.entities.DeviceEntity;
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

@Service
public class DeviceService {

    private static final Logger logger = LoggerFactory.getLogger(DeviceService.class);
    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public DeviceEntity registerDevice(DeviceEntity deviceEntity) {
        DeviceEntity device = deviceRepository.save(deviceEntity);
        updateDeviceName(device.getIpAddress(), device.getName());
        return device;
    }

    // TODO convert Entity to DTO go to Scheduler
    public List<DeviceEntity> getAllDevices() {
        return deviceRepository.findAll();
    }

    public boolean updateDeviceName(String ipAddress, String newName) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s/cm?cmnd=DeviceName %s",ipAddress, newName)))
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
