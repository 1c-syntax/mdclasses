package com.github._1c_syntax.mdclasses.unmarshal;

import com.github._1c_syntax.mdclasses.mdo.FormItem;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * Конвертирует в FormItem элемент формы
 */
public class FormItemConverter implements Converter {
  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    // noop
  }

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    // если свойство type отсутствует, тип элемента заполняется из атрибута
    var nodeName = reader.getNodeName();
    var type = getItemType(nodeName, reader.getAttribute("xsi:type"));
    var item = (FormItem) context.convertAnother(reader, FormItem.class,
      XStreamFactory.getReflectionConverter());
    if (item.getType().isEmpty()) {
      item.setType(type);
    }
    return item;
  }

  @Override
  public boolean canConvert(Class type) {
    return type == FormItem.class;
  }

  private String getItemType(String nodeName, String attribute) {
    if (nodeName.equals("items")) {
      return attribute;
    } else {
      return nodeName;
    }
  }
}
