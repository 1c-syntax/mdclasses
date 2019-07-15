package org.github._1c_syntax.mdclasses.metadata.additional;

public enum ScriptVariant {

    RUSSIAN("RUSSIAN"),
    ENGLISH("ENGLISH");

    private final String name;

    ScriptVariant(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
