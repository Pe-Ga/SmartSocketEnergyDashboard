package at.technikum.SmartSocketEnergyDashboard.util;

import org.junit.jupiter.api.Test;

import static at.technikum.SmartSocketEnergyDashboard.util.DeviceFullNameGenerator.extractIPAddresseHostPart;
import static at.technikum.SmartSocketEnergyDashboard.util.DeviceFullNameGenerator.generateFullDeviceName;
import static org.junit.jupiter.api.Assertions.*;

class DeviceFullNameGeneratorTest {

    @Test
    void extractIPAddresseHostPartFromGivenIPAddresse() {
        String ipAddress = "192.168.1.1";
        String hostPart = extractIPAddresseHostPart(ipAddress);
        assertEquals("1", hostPart);

        ipAddress = "192.168.1.57";
        hostPart = extractIPAddresseHostPart(ipAddress);
        assertEquals("57", hostPart);

        ipAddress = "192.168.1.251";
        hostPart = extractIPAddresseHostPart(ipAddress);
        assertEquals("251", hostPart);
    }

    @Test
    void generateFullDeviceNameFromGivenNameAndIPAddresse() {
        String name = "Tasmota";
        String ipAddress = "192.168.1.1";

        assertEquals("Tasmota-1", generateFullDeviceName(name, ipAddress));

        name = "Default";
        ipAddress = "192.168.1.57";
        assertEquals("Default-57", generateFullDeviceName(name, ipAddress));

        name = "Device";
        ipAddress = "192.168.1.251";
        assertEquals("Device-251", generateFullDeviceName(name, ipAddress));

    }
}