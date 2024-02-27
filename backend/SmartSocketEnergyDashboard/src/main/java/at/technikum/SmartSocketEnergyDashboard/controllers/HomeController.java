package at.technikum.SmartSocketEnergyDashboard.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    Logger logger = LoggerFactory.getLogger(HomeController.class);

    @GetMapping("/")
    public ResponseEntity<?> home() {
        logger.info("Home Endpoint");
        return ResponseEntity
                .ok()
                .body("HOME ENDPOINT RESPONSE");
    }
}
