package com.github._1c_syntax.mdclasses.unmarshal;

import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import lombok.Data;

import java.util.Collections;
import java.util.List;

public class ValueTypeConverter implements Converter {
  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    // noop
  }

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    var valueType = (ValueType) context.convertAnother(reader, ValueType.class,
      XStreamFactory.getReflectionConverter());
    return valueType.getTypes();
  }

  @Override
  public boolean canConvert(Class type) {
    return true;
  }

  @Data
  private static class ValueType {
    @XStreamImplicit
    List<String> types = Collections.emptyList();
  }
}
