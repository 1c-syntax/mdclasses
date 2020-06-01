package com.github._1c_syntax.mdclasses.unmarshal;

import com.github._1c_syntax.mdclasses.metadata.additional.CompatibilityMode;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * Используется для преобразования режима совместимости из строкового вида в объектный вид
 */
public class CompatibilityModeConverter implements Converter {
  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    // noop
  }

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    return new CompatibilityMode(reader.getValue());
  }

  @Override
  public boolean canConvert(Class type) {
    return CompatibilityMode.class.isAssignableFrom(type);
  }
}
