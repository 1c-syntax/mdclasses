package com.github._1c_syntax.mdclasses.mdo.wrapper;

import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Collections;
import java.util.List;

/**
 * Враппер свойств-item для формата конфигуратора
 */
@Data
@NoArgsConstructor
public class DesignerXRItems {
  @NonNull
  @XStreamImplicit(itemFieldName = "xr:Item")
  List<String> items = Collections.emptyList();
}
