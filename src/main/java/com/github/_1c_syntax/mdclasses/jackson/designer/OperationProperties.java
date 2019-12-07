

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.xml.namespace.QName;

@Setter
@Getter
public class OperationProperties {

    @JsonProperty("Name")
    protected String name;
    @JsonProperty("Synonym")
    protected LocalStringType synonym;
    @JsonProperty("Comment")
    protected String comment;
    @JsonProperty("ObjectBelonging")
    protected ObjectBelonging objectBelonging;
    @JsonProperty("XDTOReturningValueType")
    protected QName xdtoReturningValueType;
    @JsonProperty("Nillable")
    protected boolean nillable;
    @JsonProperty("Transactioned")
    protected boolean transactioned;
    @JsonProperty("ProcedureName")
    protected String procedureName;
    @JsonProperty("DataLockControlMode")
    protected DefaultDataLockControlMode dataLockControlMode;

}
