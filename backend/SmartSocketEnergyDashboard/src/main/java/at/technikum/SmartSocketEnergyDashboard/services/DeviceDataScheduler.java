package at.technikum.SmartSocketEnergyDashboard.services;

import at.technikum.SmartSocketEnergyDashboard.dtos.DeviceDTO;
import at.technikum.SmartSocketEnergyDashboard.entities.DeviceEntity;
import at.technikum.SmartSocketEnergyDashboard.exceptions.DeviceException;
import at.technikum.SmartSocketEnergyDashboard.util.DeviceDataLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import retrofit2.HttpException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class DeviceDataScheduler {

    private static final Logger logger = LoggerFactory.getLogger(DeviceDataScheduler.class);
    private final DeviceDataService deviceDataService;
    private final DeviceDataLogger deviceDataLogger;
    private final DataPointService dataPointService;
    private final DeviceService deviceService;
    private final DeviceConverter deviceConverter;


    public DeviceDataScheduler(DeviceDataService deviceDataService, DeviceDataLogger deviceDataLogger, DataPointService dataPointService, DeviceService deviceService, DeviceConverter deviceConverter) {
        this.deviceDataService = deviceDataService;
        this.deviceDataLogger = deviceDataLogger;
        this.dataPointService = dataPointService;
        this.deviceService = deviceService;
        this.deviceConverter = deviceConverter;
    }

    @Scheduled(fixedRate = 60000)
    public void executeDeviceDataCollection() {
        List<DeviceDTO> devices = getDevicesDTOs();
        if (devices.isEmpty()) {
            deviceDataLogger.logInfo("Device List is currently empty. No device data will be persisted.");
            return;
        }
        devices.forEach(this::processDevice);
    }

    private List<DeviceDTO> getDevicesDTOs() {
        try {
            List<DeviceEntity> deviceEntities = deviceService.getAllDevices();
            return deviceEntities.stream()
                    .map(deviceConverter::toDeviceDTO)
                    .toList();
        } catch (Exception e) {
            throw new DeviceException("Error retrieving device list", e);
        }
    }

    public void processDevice(DeviceDTO dto) {
        try {
            Optional<DeviceDTO> optionalFetchedData = deviceDataService.fetchDeviceStatus(dto.getIpAddress());

            optionalFetchedData.ifPresentOrElse(
                    fetchedData -> {
                        deviceDataLogger.logDeviceData(fetchedData);
                        dataPointService.saveDeviceData(fetchedData);
                    },
                    () -> deviceDataLogger.logWarning("No data fetched for device {}", dto.getIpAddress())

            );
        } catch (HttpException | NoSuchElementException | IllegalArgumentException | IllegalStateException | DataAccessException e) {
            logger.error("{} in scheduled task for device {}: {}", e.getClass().getSimpleName(), dto.getIpAddress(), e.getMessage(), e);
        } catch (Exception e) {
            throw new DeviceException("Error processing device with ip: " + dto.getIpAddress());
        }
    }

}

