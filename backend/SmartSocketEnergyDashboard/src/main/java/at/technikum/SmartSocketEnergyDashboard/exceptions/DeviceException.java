package at.technikum.SmartSocketEnergyDashboard.exceptions;

public class DeviceException extends RuntimeException {

    public DeviceException(String message) {
        super(message);
    }

    public DeviceException(String message, Throwable cause) {
        super(message, cause);
    }

}
