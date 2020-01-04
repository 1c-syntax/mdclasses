package com.github._1c_syntax.mdclasses.metadata.additional;

public enum MDOType {
    CONFIGURATION("Configuration"),
    COMMON_MODULE("CommonModule");

    private String mdoClassName;

    MDOType(String attribute) {
        mdoClassName = attribute;
    }

    public String getMdoClassName() {
        return this.mdoClassName;
    }
}