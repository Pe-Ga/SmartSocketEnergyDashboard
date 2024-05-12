package at.technikum.SmartSocketEnergyDashboard.util;

/**
 * Utility class for generating full device names from given device name and ip address.
 * Contains only static methods and should not be instantiated or managed as a Spring bean.
 */

public class DeviceFullNameGenerator {

    private DeviceFullNameGenerator() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String extractIPAddresseHostPart(String ipAddress) {
        String reversedIP = new StringBuilder(ipAddress).reverse().toString();

        String[] parts = reversedIP.split("\\.");
        String lastPartReversed = parts[0];
        return new StringBuilder(lastPartReversed).reverse().toString();
    }

    public static String generateFullDeviceName(String name, String ipAddresse) {
        return name +
                "-" +
                extractIPAddresseHostPart(ipAddresse);
    }
}
