package at.technikum.SmartSocketEnergyDashboard.services;

import at.technikum.SmartSocketEnergyDashboard.dtos.DeviceDTO;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

import static at.technikum.SmartSocketEnergyDashboard.util.DeviceFullNameGenerator.generateFullDeviceName;

@Service
public class DataPointService {
    private static final Logger logger = LoggerFactory.getLogger(DataPointService.class);
    private final WriteApiBlocking writeApi;
    private final String bucket;
    private final String org;
    private final InfluxDBClient influxDBClient;

    @Autowired
    public DataPointService(InfluxDBClient influxDBClient, @Value("${influxdb.bucket}") String bucket, @Value("${influxdb.org}") String org) {
        this.writeApi = influxDBClient.getWriteApiBlocking();
        this.bucket = bucket;
        this.org = org;
        this.influxDBClient = influxDBClient;
    }

    public void saveDeviceData(DeviceDTO deviceDTO) {
        Point point = createDataPoint(deviceDTO);
        try {
            writeApi.writePoint(bucket, org, point);
        } catch (Exception e) {
            logger.error("Failed to save device data point", e);
            // Handle the exception or rethrow if needed
        }
    }

    @PreDestroy
    public void closeInfluxDBClient() {
        if (influxDBClient != null) {
            try {
                influxDBClient.close();
            } catch (Exception e) {
                logger.error("Failed to close InfluxDB client cleanly", e);
            }
        }
    }

    private Point createDataPoint(DeviceDTO deviceDTO) {
        String deviceFullName = generateFullDeviceName(deviceDTO.getName(), deviceDTO.getIpAddress());
        Point point = Point.measurement("device_data")
                .addTag("name", deviceFullName)
                .time(Instant.now(), WritePrecision.NS);

        addFieldsToDataPoint(point, deviceDTO);

        return point;
    }

    private void addFieldsToDataPoint(Point point, DeviceDTO deviceDTO) {
        point.addField("energyTotal", deviceDTO.getEnergyTotal())
                .addField("energyYesterday", deviceDTO.getEnergyYesterday())
                .addField("energyToday", deviceDTO.getEnergyToday())
                .addField("power", deviceDTO.getPower())
                .addField("apparentPower", deviceDTO.getApparentPower())
                .addField("reactivePower", deviceDTO.getReactivePower())
                .addField("factor", deviceDTO.getFactor())
                .addField("voltage", deviceDTO.getVoltage())
                .addField("current", deviceDTO.getCurrent())
                .addField("isPowerOn", deviceDTO.isPowerOn());
    }
}