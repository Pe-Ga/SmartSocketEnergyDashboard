package at.technikum.SmartSocketEnergyDashboard;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.exceptions.InfluxException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Setter
@Getter
@Component
public class DBConnector {

    @Value("${influxdb.url}")
    private String url;

    @Value("${influxdb.token}")
    private String token;

    @Value("${influxdb.bucket}")
    private String bucket;

    @Value("${influxdb.org}")
    private String org;

    public InfluxDBClient buildConnection(String url, String token, String bucket, String org) {
        setToken(token);
        setBucket(bucket);
        setOrg(org);
        setUrl(url);
        return InfluxDBClientFactory.create(getUrl(), getToken().toCharArray(), getOrg(), getBucket());
    }
    /*
        public boolean singlePointWrite(InfluxDBClient influxDBClient) {
            boolean flag = false;
            try {
                WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();

                Point point = Point.measurement("sensor").addTag("sensor_id", "TLM0100").addField("location", "Main Lobby")
                        .addField("model_number", "TLM89092A")
                        .time(Instant.parse("2021-10-11T15:18:15.117484Z"), WritePrecision.MS);

                writeApi.writePoint(point);
                flag = true;
            } catch (InfluxException e) {
                System.out.println("Exception!!" + e.getMessage());
            }
            return flag;
        }
    */
}
