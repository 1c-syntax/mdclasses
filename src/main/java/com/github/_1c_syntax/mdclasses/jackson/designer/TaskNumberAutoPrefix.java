

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum TaskNumberAutoPrefix {

    @JsonProperty("BusinessProcessNumber")
    BUSINESS_PROCESS_NUMBER("BusinessProcessNumber"),
    @JsonProperty("DontUse")
    DONT_USE("DontUse");
    private final String value;

    TaskNumberAutoPrefix(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TaskNumberAutoPrefix fromValue(String v) {
        for (TaskNumberAutoPrefix c: TaskNumberAutoPrefix.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
