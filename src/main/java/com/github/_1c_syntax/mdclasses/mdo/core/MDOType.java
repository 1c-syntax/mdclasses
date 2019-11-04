package com.github._1c_syntax.mdclasses.mdo.core;

public enum MDOType {
    CONFIGURATION("Configuration"),
    COMMON_MODULE("CommonModule"),
    ATTRIBUTE("Attribute");

    private String mdoClassName;

    MDOType(String attribute) {
        mdoClassName = attribute;
    }

    public String getMdoClassName() {
        return mdoClassName;
    }
}
