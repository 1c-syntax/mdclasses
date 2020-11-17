package com.github._1c_syntax.mdclasses.mdo.wrapper.form;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
public class DesignerAttributeType {
  @XStreamAlias("v8:Type")
  @XStreamImplicit
  private List<String> types = Collections.emptyList();
}
