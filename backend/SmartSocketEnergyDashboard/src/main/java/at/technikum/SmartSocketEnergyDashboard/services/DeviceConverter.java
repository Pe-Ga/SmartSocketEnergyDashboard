package at.technikum.SmartSocketEnergyDashboard.services;

import at.technikum.SmartSocketEnergyDashboard.dtos.DeviceDTO;
import at.technikum.SmartSocketEnergyDashboard.entities.DeviceEntity;
import at.technikum.SmartSocketEnergyDashboard.models.Device;
import org.springframework.stereotype.Component;

@Component
public class DeviceConverter {

    public DeviceDTO toDeviceDTO(DeviceEntity device) {
        if (device == null) {
            return null;
        }

        DeviceDTO dto = new DeviceDTO();
        dto.setId(device.getId());
        dto.setName(device.getName());
        dto.setIpAddress(device.getIpAddress());
        return dto;
    }

    public DeviceEntity mapDeviceEntityToModel(Device device) {
        if (device == null) {
            return null;
        }

        DeviceEntity entity = new DeviceEntity();
        entity.setName(device.getName());
        entity.setIpAddress(device.getIpAddress());
        return entity;
    }

    public DeviceDTO mapDeviceEntityToDeviceDTO(DeviceEntity entity) {
        DeviceDTO dto = new DeviceDTO();
        dto.setName(entity.getName());
        dto.setIpAddress(entity.getIpAddress());
        return dto;
    }

    public DeviceDTO mapDeviceToDeviceDTO(Device device) {
        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setName(device.getName());
        deviceDTO.setIpAddress(device.getIpAddress());
        return deviceDTO;
    }

    public DeviceEntity mapDeviceDTOtoDeviceEntity(DeviceDTO deviceDTO) {
        DeviceEntity deviceEntity = new DeviceEntity();
        deviceEntity.setName(deviceDTO.getName());
        deviceEntity.setIpAddress(deviceDTO.getIpAddress());
        return deviceEntity;
    }

}
