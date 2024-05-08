package at.technikum.SmartSocketEnergyDashboard.dtos;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static at.technikum.SmartSocketEnergyDashboard.dtos.DeviceDeserializer.convertPowerStatusToBoolean;
import static org.junit.jupiter.api.Assertions.*;

class DeviceDeserializerTest {

    @Test
    void deserializeDeviceNameFromJsonResponse() throws IOException {
        // Arrange
        String json = "{\n" +
                "    \"Status\": {\n" +
                "        \"Module\": 0,\n" +
                "        \"DeviceName\": \"Tasmota\",\n" +
                "        \"FriendlyName\": [\n" +
                "            \"Tasmota\"\n" +
                "        ],\n" +
                "        \"Topic\": \"tasmota_FA3AE2\",\n" +
                "        \"ButtonTopic\": \"0\",\n" +
                "        \"Power\": 1,\n" +
                "        \"PowerOnState\": 3,\n" +
                "        \"LedState\": 1,\n" +
                "        \"LedMask\": \"FFFF\",\n" +
                "        \"SaveData\": 1,\n" +
                "        \"SaveState\": 1,\n" +
                "        \"SwitchTopic\": \"0\",\n" +
                "        \"SwitchMode\": [\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0\n" +
                "        ],\n" +
                "        \"ButtonRetain\": 0,\n" +
                "        \"SwitchRetain\": 0,\n" +
                "        \"SensorRetain\": 0,\n" +
                "        \"PowerRetain\": 0,\n" +
                "        \"InfoRetain\": 0,\n" +
                "        \"StateRetain\": 0,\n" +
                "        \"StatusRetain\": 0\n" +
                "    },\n" +
                "    \"StatusPRM\": {\n" +
                "        \"Baudrate\": 115200,\n" +
                "        \"SerialConfig\": \"8N1\",\n" +
                "        \"GroupTopic\": \"tasmotas\",\n" +
                "        \"OtaUrl\": \"http://ota.tasmota.com/tasmota/release/tasmota.bin.gz\",\n" +
                "        \"RestartReason\": \"Power On\",\n" +
                "        \"Uptime\": \"14T03:53:17\",\n" +
                "        \"StartupUTC\": \"2024-04-23T08:57:20\",\n" +
                "        \"Sleep\": 50,\n" +
                "        \"CfgHolder\": 4617,\n" +
                "        \"BootCount\": 15,\n" +
                "        \"BCResetTime\": \"2024-04-06T17:08:35\",\n" +
                "        \"SaveCount\": 90,\n" +
                "        \"SaveAddress\": \"FA000\"\n" +
                "    },\n" +
                "    \"StatusFWR\": {\n" +
                "        \"Version\": \"12.5.0(tasmota)\",\n" +
                "        \"BuildDateTime\": \"2023-05-11T09:19:51\",\n" +
                "        \"Boot\": 31,\n" +
                "        \"Core\": \"2_7_4_9\",\n" +
                "        \"SDK\": \"2.2.2-dev(38a443e)\",\n" +
                "        \"CpuFrequency\": 80,\n" +
                "        \"Hardware\": \"ESP8266EX\",\n" +
                "        \"CR\": \"351/699\"\n" +
                "    },\n" +
                "    \"StatusLOG\": {\n" +
                "        \"SerialLog\": 2,\n" +
                "        \"WebLog\": 2,\n" +
                "        \"MqttLog\": 0,\n" +
                "        \"SysLog\": 0,\n" +
                "        \"LogHost\": \"\",\n" +
                "        \"LogPort\": 514,\n" +
                "        \"SSId\": [\n" +
                "            \"WESTlan\",\n" +
                "            \"\"\n" +
                "        ],\n" +
                "        \"TelePeriod\": 300,\n" +
                "        \"Resolution\": \"558180C0\",\n" +
                "        \"SetOption\": [\n" +
                "            \"00008001\",\n" +
                "            \"2805C80001000600003C5A0A192800000000\",\n" +
                "            \"00000080\",\n" +
                "            \"00006000\",\n" +
                "            \"00004000\",\n" +
                "            \"00000000\"\n" +
                "        ]\n" +
                "    },\n" +
                "    \"StatusMEM\": {\n" +
                "        \"ProgramSize\": 633,\n" +
                "        \"Free\": 368,\n" +
                "        \"Heap\": 21,\n" +
                "        \"ProgramFlashSize\": 1024,\n" +
                "        \"FlashSize\": 2048,\n" +
                "        \"FlashChipId\": \"156085\",\n" +
                "        \"FlashFrequency\": 40,\n" +
                "        \"FlashMode\": \"DOUT\",\n" +
                "        \"Features\": [\n" +
                "            \"00000809\",\n" +
                "            \"8F9AC787\",\n" +
                "            \"04368001\",\n" +
                "            \"000000CF\",\n" +
                "            \"010013C0\",\n" +
                "            \"C000F981\",\n" +
                "            \"00004004\",\n" +
                "            \"00001000\",\n" +
                "            \"54000020\",\n" +
                "            \"00000080\"\n" +
                "        ],\n" +
                "        \"Drivers\": \"1,2,3,4,5,6,7,8,9,10,12,16,18,19,20,21,22,24,26,27,29,30,35,37,45,62\",\n" +
                "        \"Sensors\": \"1,2,3,4,5,6\",\n" +
                "        \"I2CDriver\": \"7\"\n" +
                "    },\n" +
                "    \"StatusNET\": {\n" +
                "        \"Hostname\": \"tasmota-FA3AE2-6882\",\n" +
                "        \"IPAddress\": \"192.168.0.57\",\n" +
                "        \"Gateway\": \"192.168.0.1\",\n" +
                "        \"Subnetmask\": \"255.255.255.0\",\n" +
                "        \"DNSServer1\": \"192.168.0.1\",\n" +
                "        \"DNSServer2\": \"192.168.0.1\",\n" +
                "        \"Mac\": \"08:3A:8D:FA:3A:E2\",\n" +
                "        \"Webserver\": 2,\n" +
                "        \"HTTP_API\": 1,\n" +
                "        \"WifiConfig\": 4,\n" +
                "        \"WifiPower\": 17.0\n" +
                "    },\n" +
                "    \"StatusTIM\": {\n" +
                "        \"UTC\": \"2024-05-07T12:50:37\",\n" +
                "        \"Local\": \"2024-05-07T13:50:37\",\n" +
                "        \"StartDST\": \"2024-03-31T02:00:00\",\n" +
                "        \"EndDST\": \"2024-10-27T03:00:00\",\n" +
                "        \"Timezone\": \"+01:00\",\n" +
                "        \"Sunrise\": \"05:19\",\n" +
                "        \"Sunset\": \"20:14\"\n" +
                "    },\n" +
                "    \"StatusPTH\": {\n" +
                "        \"PowerDelta\": [\n" +
                "            0,\n" +
                "            0,\n" +
                "            0\n" +
                "        ],\n" +
                "        \"PowerLow\": 0,\n" +
                "        \"PowerHigh\": 0,\n" +
                "        \"VoltageLow\": 0,\n" +
                "        \"VoltageHigh\": 0,\n" +
                "        \"CurrentLow\": 0,\n" +
                "        \"CurrentHigh\": 0\n" +
                "    },\n" +
                "    \"StatusSNS\": {\n" +
                "        \"Time\": \"2024-05-07T13:50:37\",\n" +
                "        \"ENERGY\": {\n" +
                "            \"TotalStartTime\": \"2024-04-06T17:08:35\",\n" +
                "            \"Total\": 2.701,\n" +
                "            \"Yesterday\": 0.184,\n" +
                "            \"Today\": 0.104,\n" +
                "            \"Power\": 7,\n" +
                "            \"ApparentPower\": 14,\n" +
                "            \"ReactivePower\": 12,\n" +
                "            \"Factor\": 0.48,\n" +
                "            \"Voltage\": 304,\n" +
                "            \"Current\": 0.046\n" +
                "        }\n" +
                "    },\n" +
                "    \"StatusSTS\": {\n" +
                "        \"Time\": \"2024-05-07T13:50:37\",\n" +
                "        \"Uptime\": \"14T03:53:17\",\n" +
                "        \"UptimeSec\": 1223597,\n" +
                "        \"Heap\": 20,\n" +
                "        \"SleepMode\": \"Dynamic\",\n" +
                "        \"Sleep\": 50,\n" +
                "        \"LoadAvg\": 19,\n" +
                "        \"MqttCount\": 0,\n" +
                "        \"POWER\": \"ON\",\n" +
                "        \"Wifi\": {\n" +
                "            \"AP\": 1,\n" +
                "            \"SSId\": \"WESTlan\",\n" +
                "            \"BSSId\": \"F0:A7:31:37:D6:6F\",\n" +
                "            \"Channel\": 10,\n" +
                "            \"Mode\": \"11n\",\n" +
                "            \"RSSI\": 58,\n" +
                "            \"Signal\": -71,\n" +
                "            \"LinkCount\": 2,\n" +
                "            \"Downtime\": \"0T00:00:32\"\n" +
                "        }\n" +
                "    }\n" +
                "}";

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(DeviceDTO.class, new DeviceDeserializer());
        mapper.registerModule(module);

        DeviceDTO parsedDeviceDto = mapper.readValue(json, DeviceDTO.class);

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