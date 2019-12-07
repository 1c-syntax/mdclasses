


package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.xml.namespace.QName;
import java.util.List;


@Getter
@Setter
public class TypeDescription {

    @JsonProperty("Type")
    protected List<QName> type;
    @JsonProperty("TypeSet")
    protected List<QName> typeSet;
    @JsonProperty("TypeId")
    protected List<String> typeId;
    @JsonProperty("NumberQualifiers")
    protected NumberQualifiers numberQualifiers;
    @JsonProperty("StringQualifiers")
    protected StringQualifiers stringQualifiers;
    @JsonProperty("DateQualifiers")
    protected DateQualifiers dateQualifiers;
    @JsonProperty("BinaryDataQualifiers")
    protected BinaryDataQualifiers binaryDataQualifiers;

}
