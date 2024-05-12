package at.technikum.SmartSocketEnergyDashboard.controllers;

import at.technikum.SmartSocketEnergyDashboard.dtos.DeviceDTO;
import at.technikum.SmartSocketEnergyDashboard.entities.DeviceEntity;
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
    public ResponseEntity<?> registerDevice(@RequestBody DeviceDTO dto) {
        DeviceDTO savedDTO;

        try {
            // Save the device and receive the updated DTO
            savedDTO = deviceService.saveDevice(dto);

            // Attempt to update the device name on the device
            boolean nameUpdated = deviceService.updateDeviceName(savedDTO.getIpAddress(), savedDTO.getName());
            if (!nameUpdated) {
                logger.error("Failed to update device name on the device");
                return ResponseEntity.internalServerError().body("Failed to update device name on the device");
            }
        } catch (Exception e) {
            // Log the error and return an Internal Server Error response
            logger.error("Error registering or updating device", e);
            return ResponseEntity.internalServerError().build();
        }

        // Build the URI for the newly created resource
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedDTO.getId())
                .toUri();

        // Return the response entity with the location header and the saved DTO
        return ResponseEntity.created(location).body(savedDTO);
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
