package com.thesis.cactoots.models;

import java.util.Date;
import java.util.Map;

public class Item {
    private String id;
    private String name;
    private Map<String, Integer> price;
    private Map<String, Integer> quantity;
    private Date timestamp;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Map<String, Integer> getPrice() {
        return price;
    }

    public Map<String, Integer> getQuantity() {
        return quantity;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
