package at.technikum.SmartSocketEnergyDashboard.util;


public class DeviceFullNameGenerator {

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
