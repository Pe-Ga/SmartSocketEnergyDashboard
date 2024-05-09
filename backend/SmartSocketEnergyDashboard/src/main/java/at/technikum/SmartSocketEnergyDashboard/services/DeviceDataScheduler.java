package at.technikum.SmartSocketEnergyDashboard.services;

import at.technikum.SmartSocketEnergyDashboard.dtos.DeviceDTO;
import at.technikum.SmartSocketEnergyDashboard.util.DeviceDataLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DeviceDataScheduler {

    private static final Logger logger = LoggerFactory.getLogger(DeviceDataScheduler.class);
    private final DeviceDataService deviceDataService;
    private final DeviceDataLogger deviceDataLogger;
    private final DataPointService dataPointService;


    public DeviceDataScheduler(DeviceDataService deviceDataService, DeviceDataLogger deviceDataLogger, DataPointService dataPointService) {
        this.deviceDataService = deviceDataService;
        this.deviceDataLogger = deviceDataLogger;
        this.dataPointService = dataPointService;
    }

    @Scheduled(fixedRate = 60000)
    public void scheduleDeviceDataCollection() {

        // TODO

        /*

        smthing like this
        List<DeviceDTO> devices = deviceDataService.fetchAllDeviceData();
            for (DeviceDTO deviceDTO : devices) {
                deviceDataLogger.logDeviceData(deviceDTO);
                dataPointService.saveDeviceData(deviceDTO);
         */
        logger.info("{}started working . . .", getClass().getName());

        try {
            DeviceDTO deviceDTO = deviceDataService.fetchDeviceData();
            deviceDataLogger.logDeviceData(deviceDTO);
            dataPointService.saveDeviceData(deviceDTO);
        } catch (Exception e) {
            logger.error("Error in scheduled task: {}", e.getMessage());
        }
    }

}
