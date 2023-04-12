package com.adam.evaluaretehnica.util;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationProperties {
    private final Environment env;

    public String getConfigValue(String configKey) {
        return env.getProperty("adam.evaluareTehnica." + configKey);
    }
}
