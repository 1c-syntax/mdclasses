package com.github._1c_syntax.mdclasses.unmarshal;

import com.github._1c_syntax.mdclasses.mdo.wrapper.form.DesignerFormItem;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class DesignerFormItemConverter implements Converter {
  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    // noop
  }

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    var nodeName = reader.getNodeName();
    var item = (DesignerFormItem) context.convertAnother(reader, DesignerFormItem.class,
      XStreamFactory.getReflectionConverter());
    item.setType(nodeName);
    return item;
  }

  @Override
  public boolean canConvert(Class type) {
    return type == DesignerFormItem.class;
  }
}
