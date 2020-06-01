package com.github._1c_syntax.mdclasses.mdo.wrapper;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.nio.file.Path;

/**
 * Обертка над всеми свойствами классов в формате конфигуратора
 */
@Data
@NoArgsConstructor
public class DesignerMDO {
  @NonNull
  @XStreamAsAttribute
  String uuid;
  @NonNull
  @XStreamAlias("Properties")
  DesignerProperties properties;
  @NonNull
  @XStreamAlias("ChildObjects")
  DesignerChildObjects childObjects;

  private Path mdoPath;
}
