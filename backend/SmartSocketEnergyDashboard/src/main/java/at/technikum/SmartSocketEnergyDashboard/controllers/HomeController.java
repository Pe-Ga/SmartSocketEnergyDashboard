package at.technikum.SmartSocketEnergyDashboard.controllers;

import at.technikum.SmartSocketEnergyDashboard.services.SmartSocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private SmartSocketService smartSocketService;

    @Value("${smartsocket.device1.ip}")
    private static String deviceIp;

    private static final String url = "http://192.168.0.57/cm?cmnd=EnergyTotal";


    // http://localhost:8181/v1/api
    // Send Request to smartsocket every 5s
/*
    @GetMapping("/v1/api")
    public void getEnergyTotal() {
        logger.info("Device IP: {}",deviceIp);
        // Construct the URL with the device IP, port, and command
        String response = smartSocketService.callTasmotaEndpoint();
        logger.info(response);

        // TODO implement serialization and save data in DB
    }
*/
}
