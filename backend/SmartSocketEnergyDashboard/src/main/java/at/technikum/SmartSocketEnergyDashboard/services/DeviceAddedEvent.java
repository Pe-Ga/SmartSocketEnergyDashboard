package at.technikum.SmartSocketEnergyDashboard.services;

import at.technikum.SmartSocketEnergyDashboard.dtos.DeviceDTO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class DeviceAddedEvent extends ApplicationEvent {
    private DeviceDTO deviceDTO;

    public DeviceAddedEvent(Object source, DeviceDTO deviceDTO) {
        super(source);
        this.deviceDTO = deviceDTO;
    }

}
