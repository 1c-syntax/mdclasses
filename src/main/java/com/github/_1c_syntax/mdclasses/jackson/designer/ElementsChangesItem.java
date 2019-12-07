
package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ElementsChangesItem {

    protected String guid;
    protected MainElement main;
    protected FieldLogForm field;
    protected GroupLogForm group;
    protected TableLogForm table;
    protected Button button;
    protected Decoration decoration;
    protected Addition addition;
    @JsonProperty("type")
    protected BigDecimal type;
    @JsonProperty("id")
    protected BigDecimal id;
    @JsonProperty("name")
    protected String name;
    @JsonProperty("pid")
    protected BigDecimal pid;
    @JsonProperty("aid")
    protected BigDecimal aid;

}
