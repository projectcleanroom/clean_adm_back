package com.clean.cleanroom.healthCheck;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.TreeMap;

@RestController
public class HealthCheckController {
    @Value(value = "${server.env}")
    private String env;
    @Value(value = "${server.port}")
    private String serverPort;
    @Value(value = "${server.serveraddress}")
    private String serverAddress;
    @Value(value = "${servername}")
    private String serverName;

    @GetMapping("/hc")
    public ResponseEntity<?> healthCheck(){
        Map<String, String> responeseData = new TreeMap<>();
        responeseData.put("serverName", serverName);
        responeseData.put("serverAddress", serverAddress);
        responeseData.put("serverPort", serverPort);
        responeseData.put("env", env);
        return ResponseEntity.ok(responeseData);
    }

    @GetMapping("/env")
    public ResponseEntity<?> getEnv(){
        return ResponseEntity.ok(env);
    }
}
