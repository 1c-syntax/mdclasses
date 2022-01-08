package com.github._1c_syntax.reader.designer.converter;

import com.github._1c_syntax.bsl.mdclasses.Configuration;
import com.github._1c_syntax.bsl.mdclasses.ConfigurationExtension;
import com.github._1c_syntax.bsl.mdo.MDObject;
import com.github._1c_syntax.bsl.types.ConfigurationSource;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.reader.MDOReader;
import com.github._1c_syntax.reader.common.TransformationUtils;
import com.github._1c_syntax.reader.designer.DesignerPaths;
import com.github._1c_syntax.reader.designer.DesignerXStreamFactory;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@DesignerConverter
@Slf4j
public class ConfigurationConverter implements Converter {

  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    // no-op
  }

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    var builder = TransformationUtils.builder(Configuration.class);
    requireNonNull(builder);

    var mdoType = MDOType.fromValue(reader.getNodeName()).get();

    var properties = DesignerConverterCommon.readHead(reader);
    List<String> childrenNames = new ArrayList<>();

    while (reader.hasMoreChildren()) {
      reader.moveDown();

      if ("Properties".equals(reader.getNodeName())) {
        properties.putAll(DesignerConverterCommon.readProperties(builder, mdoType, reader, context));
      } else if ("ChildObjects".equals(reader.getNodeName())) {
        while (reader.hasMoreChildren()) {
          reader.moveDown();
          childrenNames.add(reader.getNodeName() + "." + reader.getValue());
          reader.moveUp();
        }
      }
      reader.moveUp();
    }

    var isConfigurationExtension = properties.get("undefinedProperties")
      .containsKey("ConfigurationExtensionPurpose");

    if (isConfigurationExtension) {
      builder = TransformationUtils.builder(ConfigurationExtension.class);
      requireNonNull(builder);
    }
    DesignerConverterCommon.computeBuilder(builder, properties);

    var currentPath = DesignerXStreamFactory.getCurrentPath(reader);
    var children = readChildren(childrenNames, currentPath);

    // todo set DefaultLanguage
    TransformationUtils.setValue(builder, "configurationSource", ConfigurationSource.DESIGNER);
    TransformationUtils.setValue(builder, "children", children);

//    var allChildrenMDO = new ArrayList<>(children);
//    children.stream()
//      .filter(ChildrenOwner.class::isInstance)
//      .map(ChildrenOwner.class::cast)
//      .forEach(mdObject -> allChildrenMDO.addAll(mdObject.getPlainChildren()));
//    TransformationUtils.setValue(builder, "plainChildren", allChildrenMDO);
    TransformationUtils.setValue(builder, "plainChildren", children);


    var value = TransformationUtils.build(builder);
    return value;
  }

  private List<MDObject> readChildren(List<String> childrenNames, Path currentPath) {
    var rootPath = DesignerPaths.rootPathByConfigurationMDO(currentPath);
    var reader = MDOReader.getReader(rootPath);
    return childrenNames.parallelStream()
      .map(reader::readMDObject)
      .filter(Objects::nonNull)
      .map(MDObject.class::cast)
      .collect(Collectors.toList());
  }

  @Override
  public boolean canConvert(Class type) {
    return type == Configuration.class;
  }

}
