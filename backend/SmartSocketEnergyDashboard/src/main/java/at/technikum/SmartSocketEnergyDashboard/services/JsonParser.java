package at.technikum.SmartSocketEnergyDashboard.services;

import at.technikum.SmartSocketEnergyDashboard.models.SmartSocket;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class JsonParser {

    private static final ObjectMapper objectMapper = new ObjectMapper();


    public static SmartSocket parseEnergyData(String json) throws JsonProcessingException {
        // Now you have access to the mapped data, you can store it in InfluxDB
        // Call your next step InfluxDB storing logic here
        return objectMapper.readValue(json, SmartSocket.class);
    }

    public static double extractTotalFromJson(String jsonString) throws Exception {
        JsonNode rootNode = objectMapper.readTree(jsonString);
        JsonNode energyTotalNode = rootNode.get("EnergyTotal");
        if (energyTotalNode != null) {
            JsonNode totalNode = energyTotalNode.get("Total");
            if (totalNode != null && totalNode.isNumber()) {
                return totalNode.asDouble();
            } else {
                throw new IllegalArgumentException("Total field is missing or not a number");
            }
        } else {
            throw new IllegalArgumentException("EnergyTotal field is missing");
        }
    }


}
