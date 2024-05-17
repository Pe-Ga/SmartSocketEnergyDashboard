package at.technikum.SmartSocketEnergyDashboard.controllers;

import at.technikum.SmartSocketEnergyDashboard.entities.DeviceEntity;
import at.technikum.SmartSocketEnergyDashboard.exceptions.DeviceRegistrationException;
import at.technikum.SmartSocketEnergyDashboard.models.Device;
import at.technikum.SmartSocketEnergyDashboard.services.DeviceConverter;
import at.technikum.SmartSocketEnergyDashboard.services.DeviceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeviceControllerTest {

    @Mock
    private DeviceService deviceService;

    @Mock
    private DeviceConverter deviceConverter;

    @InjectMocks
    private DeviceController deviceController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Happy Path Tests

    @Test
    void testRegisterDevice_ValidDevice_ReturnsCreatedStatus() {
        // Arrange
        Device device = new Device();
        device.setName("Test Device");
        device.setIpAddress("192.168.0.1");
        DeviceEntity deviceEntity = new DeviceEntity();
        when(deviceConverter.mapDeviceEntityToModel(device)).thenReturn(deviceEntity);
        when(deviceService.registerDevice(deviceEntity)).thenReturn(deviceEntity);

        // Act
        ResponseEntity<DeviceEntity> response = deviceController.registerDevice(device);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(deviceEntity, response.getBody());
    }

    @Test
    void testGetAllDevices_DevicesExist_ReturnsOkStatus() {
        // Arrange
        List<DeviceEntity> devices = new ArrayList<>();
        devices.add(new DeviceEntity());
        when(deviceService.getAllDevices()).thenReturn(devices);

        // Act
        ResponseEntity<List<DeviceEntity>> response = deviceController.getAllDevices();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(devices, response.getBody());
    }

    @Test
    void testDeleteDevice_ValidId_CallsDeleteMethod() {
        // Arrange
        String deviceId = "1";

        // Act
        deviceController.deleteDevice(deviceId);

        // Assert
        verify(deviceService, times(1)).deleteDeviceById(Long.parseLong(deviceId));
    }

    // Unhappy Path Tests

    @Test
    void testRegisterDevice_NullDevice_ReturnsBadRequestStatus() {
        // Arrange
        Device device = null;

        // Act
        ResponseEntity<DeviceEntity> response = deviceController.registerDevice(device);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testRegisterDevice_MissingName_ReturnsBadRequestStatus() {
        // Arrange
        Device device = new Device();
        device.setIpAddress("192.168.0.1");

        // Act
        ResponseEntity<DeviceEntity> response = deviceController.registerDevice(device);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testRegisterDevice_MissingIpAddress_ReturnsBadRequestStatus() {
        // Arrange
        Device device = new Device();
        device.setName("Test Device");

        // Act
        ResponseEntity<DeviceEntity> response = deviceController.registerDevice(device);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testRegisterDevice_DeviceRegistrationException_ReturnsInternalServerErrorStatus() {
        // Arrange
        Device device = new Device();
        device.setName("Test Device");
        device.setIpAddress("192.168.0.1");
        DeviceEntity deviceEntity = new DeviceEntity();
        when(deviceConverter.mapDeviceEntityToModel(device)).thenReturn(deviceEntity);
        when(deviceService.registerDevice(deviceEntity)).thenThrow(new DeviceRegistrationException("Registration failed"));

        // Act
        ResponseEntity<DeviceEntity> response = deviceController.registerDevice(device);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testRegisterDevice_UnexpectedException_ReturnsInternalServerErrorStatus() {
        // Arrange
        Device device = new Device();
        device.setName("Test Device");
        device.setIpAddress("192.168.0.1");
        DeviceEntity deviceEntity = new DeviceEntity();
        when(deviceConverter.mapDeviceEntityToModel(device)).thenReturn(deviceEntity);
        when(deviceService.registerDevice(deviceEntity)).thenThrow(new RuntimeException("Unexpected error"));

        // Act
        ResponseEntity<DeviceEntity> response = deviceController.registerDevice(device);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetAllDevices_NoDevicesExist_ReturnsNoContentStatus() {
        // Arrange
        List<DeviceEntity> devices = new ArrayList<>();
        when(deviceService.getAllDevices()).thenReturn(devices);

        // Act
        ResponseEntity<List<DeviceEntity>> response = deviceController.getAllDevices();

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetAllDevices_ExceptionThrown_ReturnsInternalServerErrorStatus() {
        // Arrange
        when(deviceService.getAllDevices()).thenThrow(new RuntimeException("Error retrieving devices"));

        // Act
        ResponseEntity<List<DeviceEntity>> response = deviceController.getAllDevices();

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }
}