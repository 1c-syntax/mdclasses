package com.github._1c_syntax.mdclasses.unmarshal.converters;

import com.github._1c_syntax.mdclasses.mdo.wrapper.DesignerMDO;
import com.github._1c_syntax.mdclasses.mdo.wrapper.DesignerWrapper;
import com.github._1c_syntax.mdclasses.unmarshal.DesignerXStreamFactory;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * Конвертор для объектов в формате конфигуратора, минуя класс враппер
 */
@Slf4j
public class MetaDataObjectConverter implements Converter {
  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    // no-op
  }

  @SneakyThrows
  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    reader.moveDown();
    var nodeName = reader.getNodeName();
    Class<?> realClass = DesignerXStreamFactory.getRealClass(nodeName);
    if (realClass == null) {
      throw new IllegalStateException("Unexpected type: " + nodeName);
    }
    var wrapperMDO = (DesignerMDO) context.convertAnother(reader, DesignerMDO.class);
    wrapperMDO.setRealClass(realClass);

    return wrapperMDO;
  }

  @Override
  public boolean canConvert(Class type) {
    return type == DesignerWrapper.class;
  }
}
