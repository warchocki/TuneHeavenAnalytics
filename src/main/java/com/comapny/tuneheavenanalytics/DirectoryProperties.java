package com.comapny.tuneheavenanalytics;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "file")
public class DirectoryProperties {

    private String importDir;
    private String exportDir;

    public String getImportDir() {
        return importDir;
    }

    public String getExportDir() {
        return exportDir;
    }

    public void setImportDir(String importDir) {
        this.importDir = importDir;
    }

    public void setExportDir(String exportDir) {
        this.exportDir = exportDir;
    }
}
