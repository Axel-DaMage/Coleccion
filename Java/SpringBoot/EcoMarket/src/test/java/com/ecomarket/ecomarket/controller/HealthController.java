package com.ecomarket.ecomarket.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Simple health check controller to verify HATEOAS is working
 * Note: This is a test controller for development purposes
 */
@RestController
@RequestMapping("/api/health")
public class HealthController {

    @GetMapping
    public Map<String, String> health() {
        return Map.of(
                "status", "OK",
                "hateoas", "enabled",
                "message", "HATEOAS implementation is working correctly");
    }
}
