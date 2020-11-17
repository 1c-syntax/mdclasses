package com.github._1c_syntax.mdclasses.unmarshal;

import com.github._1c_syntax.mdclasses.mdo.form.attribute.DynamicListExtInfo;
import com.github._1c_syntax.mdclasses.mdo.form.attribute.ExtInfo;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import java.util.Optional;

public class ExtInfoConverter implements Converter {
  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    // noop
  }

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    ExtInfo item;
    var type = Optional.ofNullable(reader.getAttribute("xsi:type")).orElse(ExtInfo.UNKNOWN);
    if (type.equals("form:DynamicListExtInfo")) {
      item = (DynamicListExtInfo) context.convertAnother(reader, DynamicListExtInfo.class,
        XStreamFactory.getReflectionConverter());
    } else {
      item = new ExtInfo();
    }
    item.setType(type);
    return item;
  }

  @Override
  public boolean canConvert(Class type) {
    return type == ExtInfo.class;
  }
}
