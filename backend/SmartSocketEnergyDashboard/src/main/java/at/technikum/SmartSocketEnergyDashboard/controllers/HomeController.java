package at.technikum.SmartSocketEnergyDashboard.controllers;

import at.technikum.SmartSocketEnergyDashboard.services.SmartSocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private SmartSocketService smartSocketService;

    @Value("${smartsocket.device1.ip}")
    private static String deviceIp;

}
