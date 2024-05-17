package at.technikum.SmartSocketEnergyDashboard.services;

import at.technikum.SmartSocketEnergyDashboard.dtos.DeviceDTO;
import at.technikum.SmartSocketEnergyDashboard.entities.DeviceEntity;
import at.technikum.SmartSocketEnergyDashboard.exceptions.DeviceException;
import at.technikum.SmartSocketEnergyDashboard.util.DeviceDataLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeviceDataSchedulerTest {

    @Mock
    private DeviceDataService deviceDataService;

    @Mock
    private DeviceDataLogger deviceDataLogger;

    @Mock
    private DataPointService dataPointService;

    @Mock
    private DeviceService deviceService;

    @Mock
    private DeviceConverter deviceConverter;

    @InjectMocks
    private DeviceDataScheduler deviceDataScheduler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Happy Path Tests

    @Test
    void testExecuteDeviceDataCollection_NoDevices_LogsMessage() {
        // Arrange
        List<DeviceEntity> deviceEntities = new ArrayList<>();
        when(deviceService.getAllDevices()).thenReturn(deviceEntities);

        // Act
        deviceDataScheduler.executeDeviceDataCollection();

        // Assert
        verify(deviceService, times(1)).getAllDevices();
        verify(deviceConverter, never()).toDeviceDTO(any(DeviceEntity.class));
        verify(deviceDataService, never()).fetchDeviceStatus(anyString());
        verify(deviceDataLogger, times(1)).logInfo(anyString());
        verify(dataPointService, never()).saveDeviceData(any(DeviceDTO.class));
    }

    // Unhappy Path Tests

    @Test
    void testExecuteDeviceDataCollection_DeviceServiceThrowsException_ThrowsDeviceException() {
        // Arrange
        when(deviceService.getAllDevices()).thenThrow(new RuntimeException("Error retrieving devices"));

        // Act & Assert
        assertThrows(DeviceException.class, () -> deviceDataScheduler.executeDeviceDataCollection());
    }


    @Test
    void testProcessDevice_DeviceDataServiceThrowsDataAccessException_LogsError() {
        // Arrange
        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setIpAddress("192.168.0.1");
        when(deviceDataService.fetchDeviceStatus(anyString())).thenThrow(new DataAccessException("Database error") {});

        // Act
        deviceDataScheduler.processDevice(deviceDTO);

        // Assert
        verify(deviceDataLogger, never()).logDeviceData(any(DeviceDTO.class));
        verify(dataPointService, never()).saveDeviceData(any(DeviceDTO.class));
    }

    @Test
    void testProcessDevice_DeviceDataServiceReturnsEmptyOptional_LogsWarning() {
        // Arrange
        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setIpAddress("192.168.0.1");
        when(deviceDataService.fetchDeviceStatus(anyString())).thenReturn(Optional.empty());

        // Act
        deviceDataScheduler.processDevice(deviceDTO);

        // Assert
        verify(deviceDataLogger, never()).logDeviceData(any(DeviceDTO.class));
        verify(dataPointService, never()).saveDeviceData(any(DeviceDTO.class));
        verify(deviceDataLogger, times(1)).logWarning(anyString(), eq("192.168.0.1"));
    }

    @Test
    void testProcessDevice_UnexpectedExceptionThrown_ThrowsDeviceException() {
        // Arrange
        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setIpAddress("192.168.0.1");
        when(deviceDataService.fetchDeviceStatus(anyString())).thenThrow(new RuntimeException("Unexpected error"));

        // Act & Assert
        assertThrows(DeviceException.class, () -> deviceDataScheduler.processDevice(deviceDTO));
    }
}