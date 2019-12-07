
package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


public class LocalStringItemType {

    @JsonProperty("lang")
    protected String lang;
    @JsonProperty("content")
    protected String content;

}
