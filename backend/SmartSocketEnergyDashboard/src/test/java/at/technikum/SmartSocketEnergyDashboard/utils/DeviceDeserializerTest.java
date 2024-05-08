package at.technikum.SmartSocketEnergyDashboard.utils;

import at.technikum.SmartSocketEnergyDashboard.dtos.DeviceDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;
import static at.technikum.SmartSocketEnergyDashboard.util.DeviceDeserializer.convertPowerStatusToBoolean;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DeviceDeserializerTest {

    @Value("classpath:tasmota_status_response.json")
    private Resource statusResponse;

    @Autowired
    private ApplicationContext context;

    @Test
    void deserializeDeviceNameFromJsonResponse() throws IOException {

        ObjectMapper mapper = context.getBean(ObjectMapper.class);
        DeviceDTO parsedDeviceDto = mapper.readValue(statusResponse.getFile(), DeviceDTO.class);

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