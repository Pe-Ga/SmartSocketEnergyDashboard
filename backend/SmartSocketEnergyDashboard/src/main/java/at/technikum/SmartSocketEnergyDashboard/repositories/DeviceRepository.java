package at.technikum.SmartSocketEnergyDashboard.repositories;

import at.technikum.SmartSocketEnergyDashboard.entities.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<DeviceEntity, Long> {
}
