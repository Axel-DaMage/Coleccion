package com.ecomarket.ecomarket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.EnableHypermediaSupport;

@Configuration
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class HateoasConfig {
    // This configuration ensures HAL (Hypertext Application Language) format is
    // enabled
    // which is the default format for Spring HATEOAS
}
