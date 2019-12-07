

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FilterItem {

    @JsonProperty("Name")
    protected String name;
    @JsonProperty("Usage")
    protected Boolean usage;
    @JsonProperty("ComparisonType")
    protected ComparisonType comparisonType;
//    @JsonPropertyRef("Value")
//    protected JAXBElement<Object> value;
//    @JsonPropertyRef("ValueFrom", namespace = "http://v8.1c.ru/8.1/data/enterprise", type = JAXBElement.class, required = false)
//    protected JAXBElement<Object> valueFrom;
//    @JsonPropertyRef("ValueTo", namespace = "http://v8.1c.ru/8.1/data/enterprise", type = JAXBElement.class, required = false)
//    protected JAXBElement<Object> valueTo;

}
