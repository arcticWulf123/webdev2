package com.jaderoland.webdev2;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "shop")
@Component
public class ShopProperties {
    private String name;
    private String currency;

    public void setName(String name) {
        this.name = name;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getName() {
        return name;
    }

    public String getCurrency() {
        return currency;
    }
}
