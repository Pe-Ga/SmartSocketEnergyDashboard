package at.technikum.SmartSocketEnergyDashboard.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Device {
    private Long id;
    private String name;
    private String ipAddress;
    private boolean powerOn;
}
