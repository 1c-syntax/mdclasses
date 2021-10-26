package com.github._1c_syntax.reader.designer.converter;

import com.github._1c_syntax.bsl.mdo.MDObject;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.mdclasses.utils.TransformationUtils;
import com.github._1c_syntax.reader.designer.DesignerXStreamFactory;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import lombok.extern.slf4j.Slf4j;

import static java.util.Objects.requireNonNull;

@DesignerConverter
@Slf4j
public class MDObjectConverter implements Converter {

  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    // no-op
  }

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    var nodeName = reader.getNodeName();
    Class<?> realClass = DesignerXStreamFactory.getRealClass(nodeName);
    var builder = TransformationUtils.builder(realClass);
    requireNonNull(builder);

    var mdoType = MDOType.fromValue(reader.getNodeName()).get();

    var properties = DesignerConverterCommon.readHead(reader);

    while (reader.hasMoreChildren()) {
      reader.moveDown();

      if ("Properties".equals(reader.getNodeName())) {
        properties.putAll(DesignerConverterCommon.readProperties(builder, mdoType, reader, context));
      }
      reader.moveUp();
    }

    DesignerConverterCommon.computeBuilder(builder, properties);

    var value = TransformationUtils.build(builder);
    return value;
  }

  @Override
  public boolean canConvert(Class type) {
    return MDObject.class.isAssignableFrom(type);
  }
}
