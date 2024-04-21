package at.technikum.SmartSocketEnergyDashboard.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SmartSocket {


    private double energyTotal;
    private double energyToday;
    private double energyYesterday;

}
