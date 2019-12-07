

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.xml.namespace.QName;
import java.util.List;


@Getter
@Setter
public class TypeSet {

    protected List<String> type;
    protected StringQualifiersMAC string;
    protected NumberQualifiersMAC number;
    protected DateQualifiersMAC date;
    protected BinaryDataQualifiersMAC binary;
    @JsonProperty("clsid")
    protected String clsid;
    @JsonProperty("name")
    protected QName name;
}
