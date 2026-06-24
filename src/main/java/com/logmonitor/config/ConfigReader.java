package com.logmonitor.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private Properties properties = new Properties();

    public ConfigReader() {

        try {

            FileInputStream file =
                    new FileInputStream("config/application.properties");

            properties.load(file);

        } catch(IOException e) {
            e.printStackTrace();
        }
    }


    public String get(String key) {

        return properties.getProperty(key);

    }
}