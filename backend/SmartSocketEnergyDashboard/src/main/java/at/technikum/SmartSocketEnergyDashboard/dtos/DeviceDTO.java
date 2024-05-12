package at.technikum.SmartSocketEnergyDashboard.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static at.technikum.SmartSocketEnergyDashboard.util.DeviceFullNameGenerator.generateFullDeviceName;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDTO {

    private String name;
    private String hostName;
    private String ipAddress;
    private double energyTotal;
    private double energyYesterday;
    private double energyToday;
    private double power;
    private double apparentPower;
    private double reactivePower;
    private double factor;
    private double voltage;
    private double current;
    private boolean isPowerOn;

    @Override
    public String toString() {
        return "DeviceDTO{" +
                "name='" + generateFullDeviceName(name, ipAddress) + '\'' +
                ", hostName='" + hostName + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", energyTotal=" + energyTotal +
                ", energyYesterday=" + energyYesterday +
                ", energyToday=" + energyToday +
                ", power=" + power +
                ", apparentPower=" + apparentPower +
                ", reactivePower=" + reactivePower +
                ", factor=" + factor +
                ", voltage=" + voltage +
                ", current=" + current +
                ", isPowerOn=" + isPowerOn +
                '}';
    }

}

