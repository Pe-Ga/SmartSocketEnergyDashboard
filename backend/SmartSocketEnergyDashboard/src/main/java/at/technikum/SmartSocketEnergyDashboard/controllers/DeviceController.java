package at.technikum.SmartSocketEnergyDashboard.controllers;

import at.technikum.SmartSocketEnergyDashboard.entities.DeviceEntity;
import at.technikum.SmartSocketEnergyDashboard.models.Device;
import at.technikum.SmartSocketEnergyDashboard.services.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    private final DeviceService deviceService;
    private static final Logger logger = LoggerFactory.getLogger(DeviceController.class);

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping
    public ResponseEntity<DeviceEntity> registerDevice(@RequestBody DeviceEntity deviceEntity) {
        DeviceEntity savedDevice;
        try {
            savedDevice = deviceService.registerDevice(deviceEntity);
        } catch (Exception e) {
            logger.error("Error registering device", e);
            return ResponseEntity.internalServerError().build();
        }
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedDevice.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedDevice);
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
}
