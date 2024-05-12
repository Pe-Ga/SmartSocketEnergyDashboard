package at.technikum.SmartSocketEnergyDashboard.services;

import at.technikum.SmartSocketEnergyDashboard.dtos.DeviceDTO;
import at.technikum.SmartSocketEnergyDashboard.util.DeviceDataLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeviceDataScheduler {

    private static final Logger logger = LoggerFactory.getLogger(DeviceDataScheduler.class);
    private final DeviceDataService deviceDataService;
    private final DeviceDataLogger deviceDataLogger;
    private final DataPointService dataPointService;
    private final DeviceManagerService deviceManagerService;


    public DeviceDataScheduler(DeviceDataService deviceDataService, DeviceDataLogger deviceDataLogger, DataPointService dataPointService, DeviceManagerService deviceManagerService) {
        this.deviceDataService = deviceDataService;
        this.deviceDataLogger = deviceDataLogger;
        this.dataPointService = dataPointService;
        this.deviceManagerService = deviceManagerService;
    }

    @Scheduled(fixedRate = 60000)
    public void scheduleDeviceDataCollection() {

        List<DeviceDTO> devices = deviceManagerService.getAllDevices();

        if (devices.isEmpty()) {
            logger.info("No devices to process at this time.");
        }

        for (DeviceDTO dto : devices) {
            logger.info("{}started working . . .", getClass().getName());

            try {
                DeviceDTO fetchedData = deviceDataService.fetchDeviceData(dto.getIpAddress());
                deviceDataLogger.logDeviceData(fetchedData);
                dataPointService.saveDeviceData(fetchedData);
            } catch (Exception e) {
                logger.error("Error in scheduled task: {}", e.getMessage());
            }
        }
    }

}
