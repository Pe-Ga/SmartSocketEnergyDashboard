package at.technikum.SmartSocketEnergyDashboard.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDTO {

    private String name;
    private double energyTotal;
    private double energyYesterday;
    private double energyToday;
    private double power;
    private double apparentPower;
    private double reactivePower;
    private double factor;
    private double voltage;
    private double current;

}
