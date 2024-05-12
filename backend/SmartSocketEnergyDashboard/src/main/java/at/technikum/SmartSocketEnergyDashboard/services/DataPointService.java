package at.technikum.SmartSocketEnergyDashboard.services;

import at.technikum.SmartSocketEnergyDashboard.dtos.DeviceDTO;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

import static at.technikum.SmartSocketEnergyDashboard.util.DeviceFullNameGenerator.generateFullDeviceName;

@Service
public class DataPointService {

    private final WriteApiBlocking writeApi;
    private final String bucket;
    private final String org;

    @Autowired
    public DataPointService(InfluxDBClient influxDBClient,
                            @Value("${influxdb.bucket}") String bucket,
                            @Value("${influxdb.org}") String org) {
        this.writeApi = influxDBClient.getWriteApiBlocking();
        this.bucket = bucket;
        this.org = org;
    }

    public void saveDeviceData(DeviceDTO deviceDTO) {
        Point point = createDataPoint(deviceDTO);
        writeApi.writePoint(bucket, org, point);
    }


    private Point createDataPoint(DeviceDTO deviceDTO) {
        String deviceFullName = generateFullDeviceName(deviceDTO.getName(),
                deviceDTO.getIpAddress());
        return Point
                .measurement("device_data")
                .addTag("name", deviceFullName)
                .addField("energyTotal", deviceDTO.getEnergyTotal())
                .addField("energyYesterday", deviceDTO.getEnergyYesterday())
                .addField("energyToday", deviceDTO.getEnergyToday())
                .addField("power", deviceDTO.getPower())
                .addField("apparentPower", deviceDTO.getApparentPower())
                .addField("reactivePower", deviceDTO.getReactivePower())
                .addField("factor", deviceDTO.getFactor())
                .addField("voltage", deviceDTO.getVoltage())
                .addField("current", deviceDTO.getCurrent())
                .addField("isPowerOn", deviceDTO.isPowerOn())
                .time(Instant.now(), WritePrecision.NS);
    }
}
