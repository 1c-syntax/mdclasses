package com.github._1c_syntax.reader.designer.converter;

import com.github._1c_syntax.bsl.mdo.MDObject;
import com.github._1c_syntax.bsl.mdo.Module;
import com.github._1c_syntax.bsl.mdo.XdtoPackage;
import com.github._1c_syntax.bsl.mdo.children.ObjectModule;
import com.github._1c_syntax.bsl.mdo.data_storage.XdtoPackageData;
import com.github._1c_syntax.bsl.mdo.support.MdoReference;
import com.github._1c_syntax.bsl.support.SupportVariant;
import com.github._1c_syntax.bsl.types.ConfigurationSource;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.bsl.types.ModuleType;
import com.github._1c_syntax.mdclasses.unmarshal.XStreamFactory;
import com.github._1c_syntax.mdclasses.utils.MDOPathUtils;
import com.github._1c_syntax.mdclasses.utils.MDOUtils;
import com.github._1c_syntax.mdclasses.utils.TransformationUtils;
import com.github._1c_syntax.reader.designer.DesignerXStreamFactory;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    var currentPath = DesignerXStreamFactory.getCurrentPath(reader);
    var mdoFolderPath = MDOPathUtils.getMDOTypeFolderByMDOPath(currentPath, mdoType);
    if (mdoFolderPath.isPresent()) {
      var folder = mdoFolderPath.get();
      var moduleTypes = MDOUtils.getModuleTypesForMdoTypes().getOrDefault(mdoType, Collections.emptySet());
      if (!moduleTypes.isEmpty()) {
        var mdoName = (String) properties.get("properties").get("Name");
        var owner = (MdoReference) properties.get("properties").get("mdoReference");
        var supportVariant = (SupportVariant) properties.get("properties").get("supportVariant");
        List<Module> modules = new ArrayList<>();
        moduleTypes.forEach((ModuleType moduleType) ->
          MDOPathUtils.getModulePath(ConfigurationSource.DESIGNER, folder, mdoName, moduleType)
            .ifPresent((Path modulePath) -> {
              if (modulePath.toFile().exists()) {
                modules.add(ObjectModule.builder()
                  .moduleType(moduleType)
                  .uri(modulePath.toUri())
                  .owner(owner)
                  .supportVariant(supportVariant)
                  .build());
              }
            }));
        properties.get("properties").put("modules", modules);
      }
    }

    if (XdtoPackage.class.isAssignableFrom(realClass)) {
      var packageDataPath = MDOPathUtils.getPackageDataPath(currentPath,
        (String) properties.get("properties").get("Name"));
      readXDTOPackageData(packageDataPath).ifPresent(xdtoPackageData ->
        properties.get("properties").put("data", xdtoPackageData));
    }

    DesignerConverterCommon.computeBuilder(builder, properties);


    var value = TransformationUtils.build(builder);
    return value;
  }

  @Override
  public boolean canConvert(Class type) {
    return MDObject.class.isAssignableFrom(type);
  }

  private Optional<XdtoPackageData> readXDTOPackageData(Path path) {
    if (Files.notExists(path)) {
      return Optional.empty();
    }

    return Optional.of((XdtoPackageData) DesignerXStreamFactory.fromXML(path.toFile()));
  }
}
