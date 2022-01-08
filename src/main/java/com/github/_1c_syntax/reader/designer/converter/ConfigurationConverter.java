package com.github._1c_syntax.reader.designer.converter;

import com.github._1c_syntax.bsl.mdclasses.Configuration;
import com.github._1c_syntax.bsl.mdclasses.ConfigurationExtension;
import com.github._1c_syntax.bsl.mdo.MDObject;
import com.github._1c_syntax.bsl.types.ConfigurationSource;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.reader.MDOReader;
import com.github._1c_syntax.reader.designer.DesignerPaths;
import com.github._1c_syntax.reader.designer.DesignerXStreamFactory;
import com.github._1c_syntax.reader.designer.wrapper.DesignerProperties;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

@DesignerConverter
public class ConfigurationConverter implements Converter {

  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    // no-op
  }

  @SneakyThrows
  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {

    int count;
    var fileInputStream = new FileInputStream(DesignerXStreamFactory.getCurrentPath(reader).toFile());
    try (var scanner = new Scanner(fileInputStream, StandardCharsets.UTF_8)) {
      count = (int) scanner.findAll("(ConfigurationExtensionPurpose)").count();
    }

    Class<?> realClass = Configuration.class;
    var mdoType = MDOType.CONFIGURATION;

    if (count > 0) {
      realClass = ConfigurationExtension.class;
      // mdoType = MDOType.CONFIGURATION; // todo надо делать отдельный?
    }

    var designerProperties = new DesignerProperties(reader, context, realClass, mdoType);
    designerProperties.setChildren(readChildren((List<String>) designerProperties.getProperties()
        .getOrDefault("childrenNames", Collections.emptyList()),
      designerProperties.getCurrentPath()));
    designerProperties.getProperties().put("configurationSource", ConfigurationSource.DESIGNER);

    return designerProperties.computeAndBuild();
  }

  private List<Object> readChildren(List<String> childrenNames, Path currentPath) {
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
