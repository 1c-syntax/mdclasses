

package com.github._1c_syntax.mdclasses.jabx.original;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonModuleProperties {

  @JsonProperty("Name")
  protected String name;
  //  @JsonProperty("Synonym")
//  protected LocalStringType synonym;
  @JsonProperty("Comment")
  protected String comment;
  @JsonProperty("ObjectBelonging")
  protected ObjectBelonging objectBelonging;
  @JsonProperty("Module")
  protected String module;
  @JsonProperty("Global")
  protected Boolean global;
  @JsonProperty("ClientManagedApplication")
  protected Boolean clientManagedApplication;
  @JsonProperty("Server")
  protected Boolean server;
  @JsonProperty("ExternalConnection")
  protected Boolean externalConnection;
  @JsonProperty("ClientOrdinaryApplication")
  protected Boolean clientOrdinaryApplication;
  @JsonProperty("Client")
  protected Boolean client;
  @JsonProperty("ServerCall")
  protected Boolean serverCall;
  @JsonProperty("Privileged")
  protected Boolean privileged;
  @JsonProperty("ReturnValuesReuse")
  protected ReturnValuesReuse returnValuesReuse;


}
