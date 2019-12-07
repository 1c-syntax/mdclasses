

package com.github._1c_syntax.mdclasses.jabx.edt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
  @JsonProperty("string")
  protected ReturnValueReuse returnValuesReuse;
  @JsonProperty(defaultValue = "false")
  protected Boolean privileged;

}
