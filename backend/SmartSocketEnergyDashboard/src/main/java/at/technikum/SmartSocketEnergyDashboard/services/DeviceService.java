package at.technikum.SmartSocketEnergyDashboard.services;

import at.technikum.SmartSocketEnergyDashboard.entities.DeviceEntity;
import at.technikum.SmartSocketEnergyDashboard.repositories.DeviceRepository;
import at.technikum.SmartSocketEnergyDashboard.exceptions.DeviceRegistrationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
    private static final String ENCODE_SPACE = "%20";
    private final DeviceRepository deviceRepository;
    private final DeviceConverter deviceConverter;


    public DeviceService(DeviceRepository deviceRepository, DeviceConverter deviceConverter) {
        this.deviceRepository = deviceRepository;
        this.deviceConverter = deviceConverter;
    }

    @CacheEvict(value="devices", allEntries = true)
    public DeviceEntity saveDevice(DeviceEntity deviceEntity) {
        return deviceRepository.save(deviceEntity);
    }

    @CacheEvict(value="devices", allEntries = true)
    public void deleteDeviceById(Long id) {
        deviceRepository.deleteById(id);
    }


    @Cacheable("devices")
    public List<DeviceEntity> getAllDevices() {
        return deviceRepository.findAll();
    }

    public DeviceEntity registerDevice(DeviceEntity deviceEntity) throws DeviceRegistrationException {
        try {
            var device = saveDevice(deviceEntity);
            var deviceDTO = deviceConverter.mapDeviceEntityToDeviceDTO(device);
            boolean nameUpdated = updateDeviceName(deviceDTO.getIpAddress(), deviceDTO.getName());
            if (!nameUpdated) {
                logger.error("Failed to update device name on the device");
                throw new DeviceRegistrationException("Failed to update device name on the device");
            }
            return device;
        } catch (Exception e) {
            logger.error("Error registering device", e);
            throw new DeviceRegistrationException("Error registering device", e);
        }
    }

    public boolean updateDeviceName(String ipAddress, String newName) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("http://%s/cm?cmnd=DeviceName%s%s",ipAddress, ENCODE_SPACE, newName)))
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
