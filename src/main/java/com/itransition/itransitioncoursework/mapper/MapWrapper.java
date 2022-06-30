package com.itransition.itransitioncoursework.mapper;
//Sevinch Abdisattorova 06/27/2022 6:21 AM


import java.util.HashMap;
import java.util.Map;

public class MapWrapper {
    private Map<String, Object> map = new HashMap<>();

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}

