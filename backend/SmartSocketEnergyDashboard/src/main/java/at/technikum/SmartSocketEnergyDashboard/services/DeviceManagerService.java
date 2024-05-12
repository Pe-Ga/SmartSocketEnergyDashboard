package at.technikum.SmartSocketEnergyDashboard.services;

import at.technikum.SmartSocketEnergyDashboard.dtos.DeviceDTO;
import at.technikum.SmartSocketEnergyDashboard.entities.DeviceEntity;
import at.technikum.SmartSocketEnergyDashboard.repositories.DeviceRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class DeviceManagerService {

    private static final Logger logger = LoggerFactory.getLogger(DeviceManagerService.class);

    private final List<DeviceDTO> deviceList = Collections.synchronizedList(new ArrayList<>());

    private final DeviceRepository deviceRepository;

    public DeviceManagerService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @PostConstruct
    public void init() {
         List<DeviceEntity> devices = deviceRepository.findAll();
         devices.forEach(device -> {
             DeviceDTO dto = convertToDeviceDTO(device);
             deviceList.add(dto);
         });
         deviceList.forEach(device -> logger.info(device.getName()));

     }

     public void registerDevice(DeviceDTO dto) {
        deviceList.add(dto);
     }

    public List<DeviceDTO> getAllDevices() {
        return new ArrayList<>(deviceList);
    }

    private DeviceDTO convertToDeviceDTO(DeviceEntity device) {
        DeviceDTO dto = new DeviceDTO();

        dto.setId(device.getId());
        dto.setName(device.getName());
        dto.setIpAddress(device.getIpAddress());

        return dto;
    }

}
