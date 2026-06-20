package com.noki.noban.api.models;

public class Version {
    
    private String name;

    private String version;

    public Version(String name, String version) {
        this.version = version;
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
