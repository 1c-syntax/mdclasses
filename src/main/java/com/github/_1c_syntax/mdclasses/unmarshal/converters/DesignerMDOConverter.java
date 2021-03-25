package com.github._1c_syntax.mdclasses.unmarshal.converters;

import com.github._1c_syntax.mdclasses.unmarshal.XStreamFactory;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.DesignerMDO;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.DesignerRootWrapper;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * Конвертор для объектов в формате конфигуратора, минуя класс враппер
 */
@Slf4j
public class DesignerMDOConverter implements Converter {
  private final Map<Class<?>, Constructor<?>> constructors = new HashMap<>();

  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    // no-op
  }

  @SneakyThrows
  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    reader.moveDown();
    var nodeName = reader.getNodeName();
    Class<?> realClass = XStreamFactory.getRealClass(nodeName);
    if (realClass == null) {
      throw new IllegalStateException("Unexpected type: " + nodeName);
    }
    var wrapperMDO = (DesignerMDO) context.convertAnother(reader, DesignerMDO.class);
    wrapperMDO.setMdoPath(XStreamFactory.getCurrentPath(reader));
    return getConstructor(realClass).newInstance(wrapperMDO);
  }

  @Override
  public boolean canConvert(Class type) {
    return type == DesignerRootWrapper.class;
  }

  @SneakyThrows
  private Constructor<?> getConstructor(Class<?> realClass) {
    var constructor = constructors.get(realClass);
    if (constructor == null) {
      constructor = realClass.getConstructor(DesignerMDO.class);
      constructors.put(realClass, constructor);
    }

    return constructor;
  }
}
