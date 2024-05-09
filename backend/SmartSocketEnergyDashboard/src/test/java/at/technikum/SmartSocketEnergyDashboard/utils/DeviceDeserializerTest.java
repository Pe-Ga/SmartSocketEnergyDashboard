package at.technikum.SmartSocketEnergyDashboard.utils;

import at.technikum.SmartSocketEnergyDashboard.dtos.DeviceDTO;
import at.technikum.SmartSocketEnergyDashboard.util.DeviceDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import static at.technikum.SmartSocketEnergyDashboard.util.DeviceDeserializer.convertPowerStatusToBoolean;
import static org.junit.jupiter.api.Assertions.*;


class DeviceDeserializerTest {

    private ObjectMapper mapper;
    private File statusResponseFile;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(DeviceDTO.class, new DeviceDeserializer());
        mapper.registerModule(module);

        String jsonFilePath = "src/test/resources/tasmota_status_response.json";
        statusResponseFile = new File(jsonFilePath);
        assertTrue(statusResponseFile.exists());
    }

    @Test
    void deserializeDeviceNameFromJsonResponse() throws IOException {

        DeviceDTO parsedDeviceDto = mapper.readValue(statusResponseFile, DeviceDTO.class);

        assertEquals("Tasmota", parsedDeviceDto.getName());
        assertEquals(2.701, parsedDeviceDto.getEnergyTotal());
        assertEquals(0.184, parsedDeviceDto.getEnergyYesterday());
        assertEquals(0.104, parsedDeviceDto.getEnergyToday());
        assertEquals(7, parsedDeviceDto.getPower());
        assertEquals(14, parsedDeviceDto.getApparentPower());
        assertEquals(12, parsedDeviceDto.getReactivePower());
        assertEquals(0.48, parsedDeviceDto.getFactor());
        assertEquals(304, parsedDeviceDto.getVoltage());
        assertEquals(0.046, parsedDeviceDto.getCurrent());
        assertTrue(parsedDeviceDto.isPowerOn());
    }

    @Test
    void convertPowerStatusToBooleanReturnsTrueforONValue() throws IOException {
        String powerStatusON = "ON";
        String powerStatusOFF = "OFF";
        String powerStatusInvalid = "Invalid";

        boolean isPowerOn = convertPowerStatusToBoolean(powerStatusON);
        assertTrue(isPowerOn);

        isPowerOn = convertPowerStatusToBoolean(powerStatusOFF);
        assertFalse(isPowerOn);

        assertThrows(IOException.class, () -> convertPowerStatusToBoolean(powerStatusInvalid),
                "Unexpected value for power status");

    }
}