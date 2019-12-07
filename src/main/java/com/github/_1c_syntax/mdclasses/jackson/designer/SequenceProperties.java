

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SequenceProperties {

    @JsonProperty("Name")
    protected String name;
    @JsonProperty("Synonym")
    protected LocalStringType synonym;
    @JsonProperty("Comment")
    protected String comment;
    @JsonProperty("ObjectBelonging")
    protected ObjectBelonging objectBelonging;
    @JsonProperty("MoveBoundaryOnPosting")
    protected MoveBoundaryOnPosting moveBoundaryOnPosting;
    @JsonProperty("Documents")
    protected MDListType documents;
    @JsonProperty("RegisterRecords")
    protected MDListType registerRecords;
    @JsonProperty("RecordSetModule")
    protected String recordSetModule;
    @JsonProperty("DataLockControlMode")
    protected DefaultDataLockControlMode dataLockControlMode;


}
