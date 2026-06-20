package com.noki.noban.api.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import com.noki.noban.api.models.Version;

@RestController
@RequestMapping("/api/version")
public class VersionController {
    
    @Value("${spring.application.version}")
    private String version;

    @Value("${spring.application.name}")
    private String name;

    @GetMapping
    public Version getVersion() {
        return new Version(name, version);
    }
}
