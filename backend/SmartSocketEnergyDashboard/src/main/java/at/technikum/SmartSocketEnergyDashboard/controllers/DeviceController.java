package at.technikum.SmartSocketEnergyDashboard.controllers;

import at.technikum.SmartSocketEnergyDashboard.entities.DeviceEntity;
import at.technikum.SmartSocketEnergyDashboard.exceptions.DeviceRegistrationException;
import at.technikum.SmartSocketEnergyDashboard.models.Device;
import at.technikum.SmartSocketEnergyDashboard.services.DeviceConverter;
import at.technikum.SmartSocketEnergyDashboard.services.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    private final DeviceService deviceService;
    private final DeviceConverter deviceConverter;
    private static final Logger logger = LoggerFactory.getLogger(DeviceController.class);

    public DeviceController(DeviceService deviceService, DeviceConverter deviceConverter) {
        this.deviceService = deviceService;
        this.deviceConverter = deviceConverter;
    }

    @PostMapping
    public ResponseEntity<DeviceEntity> registerDevice(@RequestBody Device device) {
        if (!isValidDevice(device)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        try {
            DeviceEntity createdDeviceEntity = deviceService.registerDevice(deviceConverter.mapDeviceEntityToModel(device));
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDeviceEntity);
        } catch (DeviceRegistrationException e) {
            logger.error("Error registering device", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            logger.error("Unexpected error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<DeviceEntity>> getAllDevices() {
        List<DeviceEntity> devices;
        try {
            devices = deviceService.getAllDevices();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
        if (devices.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(devices);
    }

    @DeleteMapping
    public void deleteDevice(@RequestParam String id) {
        deviceService.deleteDeviceById(Long.parseLong(id));
    }

    private boolean isValidDevice(Device device) {
        return device != null && device.getName() != null && !device.getName().isEmpty()
                && device.getIpAddress() != null && !device.getIpAddress().isEmpty();
    }
}
