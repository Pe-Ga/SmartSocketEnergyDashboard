package at.technikum.SmartSocketEnergyDashboard.util;

import at.technikum.SmartSocketEnergyDashboard.dtos.DeviceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DeviceDataLogger {

    private static final Logger logger = LoggerFactory.getLogger(DeviceDataLogger.class);

    public void logDeviceData(DeviceDTO deviceDTO) {
        logger.info(deviceDTO.toString());
    }

    public void logError(String error, Throwable cause) {
        logger.error(error, cause);
    }

    public void logInfo(String message, Object... args) {
        logger.info(message, args);
    }

    public void logWarning(String message, Object... args) {
        logger.warn(message, args);
    }
}
