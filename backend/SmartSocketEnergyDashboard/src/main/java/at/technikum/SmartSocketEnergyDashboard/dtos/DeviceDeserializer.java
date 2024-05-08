package at.technikum.SmartSocketEnergyDashboard.dtos;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class DeviceDeserializer extends JsonDeserializer<DeviceDTO> {

    private static final Logger logger = LoggerFactory.getLogger(DeviceDeserializer.class);

    @Override
    public DeviceDTO deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        String name = node.get("Status").get("DeviceName").asText();

        String total = node.get("StatusSNS")
                        .get("ENERGY")
                        .get("Total").asText();

        String energyYesterday = node.get("StatusSNS")
                                    .get("ENERGY")
                                    .get("Yesterday").asText();

        String energyToday = node.get("StatusSNS")
                .get("ENERGY")
                .get("Today").asText();

        String power = node.get("StatusSNS")
                .get("ENERGY")
                .get("Power").asText();

        String apparentPower = node.get("StatusSNS")
                .get("ENERGY")
                .get("ApparentPower").asText();

        String reactivePower = node.get("StatusSNS")
                .get("ENERGY")
                .get("ReactivePower").asText();

        String factor = node.get("StatusSNS")
                .get("ENERGY")
                .get("Factor").asText();

        String voltage = node.get("StatusSNS")
                .get("ENERGY")
                .get("Voltage").asText();

        String current = node.get("StatusSNS")
                .get("ENERGY")
                .get("Current").asText();

        String isPowerOn = node.get("StatusSTS")
                .get("POWER")
                .asText();


        DeviceDTO device = new DeviceDTO();

        device.setName(name);
        device.setEnergyTotal(Double.parseDouble(total));
        device.setEnergyYesterday(Double.parseDouble(energyYesterday));
        device.setEnergyToday(Double.parseDouble(energyToday));
        device.setPower(Double.parseDouble(power));
        device.setApparentPower(Double.parseDouble(apparentPower));
        device.setReactivePower(Double.parseDouble(reactivePower));
        device.setFactor(Double.parseDouble(factor));
        device.setVoltage(Double.parseDouble(voltage));
        device.setCurrent(Double.parseDouble(current));
        device.setPowerOn(convertPowerStatusToBoolean(isPowerOn));

        logger.debug(device.toString());

        return device;
    }

    public static boolean convertPowerStatusToBoolean(String powerStatus) throws IOException {
        return switch (powerStatus.toUpperCase()) {
            case "ON" -> true;
            case "OFF" -> false;
            default -> throw new IOException("Unexpected value for power status: " + powerStatus);
        };
    }
}
