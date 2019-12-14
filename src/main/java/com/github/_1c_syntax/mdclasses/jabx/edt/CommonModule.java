

package com.github._1c_syntax.mdclasses.jabx.edt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonModule
  extends MDObjectBase {

  @JsonProperty(defaultValue = "false")
  protected Boolean server;
  @JsonProperty(defaultValue = "false")
  protected Boolean global;
  @JsonProperty(defaultValue = "false")
  protected Boolean clientManagedApplication;
  @JsonProperty(defaultValue = "false")
  protected Boolean externalConnection;
  @JsonProperty(defaultValue = "false")
  protected Boolean clientOrdinaryApplication;
  @JsonProperty(defaultValue = "false")
  protected Boolean serverCall;
  @JsonProperty()
  protected ReturnValueReuse returnValuesReuse;
  @JsonProperty(defaultValue = "false")
  protected Boolean privileged;

}
