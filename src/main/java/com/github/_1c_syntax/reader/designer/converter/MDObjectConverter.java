package com.github._1c_syntax.reader.designer.converter;

import com.github._1c_syntax.bsl.mdo.CommonModule;
import com.github._1c_syntax.bsl.mdo.ExchangePlan;
import com.github._1c_syntax.bsl.mdo.MDObject;
import com.github._1c_syntax.bsl.mdo.Role;
import com.github._1c_syntax.bsl.mdo.ScheduledJob;
import com.github._1c_syntax.bsl.mdo.Template;
import com.github._1c_syntax.bsl.mdo.XdtoPackage;
import com.github._1c_syntax.bsl.mdo.children.WebServiceOperation;
import com.github._1c_syntax.bsl.mdo.data_storage.XdtoPackageData;
import com.github._1c_syntax.bsl.mdo.support.Handler;
import com.github._1c_syntax.bsl.mdo.support.TemplateType;
import com.github._1c_syntax.bsl.types.ConfigurationSource;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.bsl.types.ModuleType;
import com.github._1c_syntax.mdclasses.utils.MDOPathUtils;
import com.github._1c_syntax.mdclasses.utils.TransformationUtils;
import com.github._1c_syntax.reader.designer.DesignerXStreamFactory;
import com.github._1c_syntax.reader.designer.wrapper.DesignerProperties;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@DesignerConverter
@Slf4j
public class MDObjectConverter implements Converter {

  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    // no-op
  }

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    var designerProperties = new DesignerProperties(reader, context);

    if (XdtoPackage.class.isAssignableFrom(designerProperties.getRealClass())) {
      var packageDataPath = MDOPathUtils.getPackageDataPath(designerProperties.getCurrentPath(),
        designerProperties.getName());
      readXDTOPackageData(packageDataPath).ifPresent(xdtoPackageData ->
        designerProperties.getProperties().put("data", xdtoPackageData));
    }

    if (WebServiceOperation.class.isAssignableFrom(designerProperties.getRealClass())) {
      designerProperties.getProperties().put("handler", designerProperties.getUnknownProperties().get("ProcedureName"));
    }

    if (ScheduledJob.class.isAssignableFrom(designerProperties.getRealClass())) {
      designerProperties.getProperties().put("handler",
        new Handler((String) designerProperties.getUnknownProperties().get("MethodName")));
    }

    if (Role.class.isAssignableFrom(designerProperties.getRealClass())) {
      var dataPath = MDOPathUtils.getRoleDataPath(designerProperties.getCurrentPath(),
        designerProperties.getName());
      readRoleRightsData(dataPath).ifPresent(designerProperties.getProperties()::putAll);
    }

    if (Template.class.isAssignableFrom(designerProperties.getRealClass())) {
      if (designerProperties.getProperties().get("TemplateType") == TemplateType.DATA_COMPOSITION_SCHEME) {
        var dataPath = MDOPathUtils.getTemplateDataPath(designerProperties.getCurrentPath(),
          designerProperties.getName(), designerProperties.getMdoType());
        readTemplateData(dataPath).ifPresent(designerProperties.getProperties()::putAll);
        designerProperties.getProperties().put("templateDataPath", dataPath);
      }
    }

    if (ExchangePlan.class.isAssignableFrom(designerProperties.getRealClass())) {
      var dataPath = MDOPathUtils.getExchangePlanContentPath(designerProperties.getCurrentPath(),
        designerProperties.getName());
      readExchangePlanData(dataPath).ifPresent(designerProperties.getProperties()::putAll);
    }

    if (CommonModule.class.isAssignableFrom(designerProperties.getRealClass())) {
      readModule(designerProperties.getCurrentPath(),
        designerProperties.getName()).ifPresent(designerProperties.getProperties()::putAll);
    }

    if (MDOType.valuesWithoutChildren().contains(designerProperties.getMdoType())) {
      DesignerConverterCommon.computeBuilder(designerProperties.getBuilder(), designerProperties);
      return TransformationUtils.build(designerProperties.getBuilder());
    } else {
      return designerProperties;
    }
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

  private Optional<Map> readRoleRightsData(Path path) {
    if (Files.notExists(path)) {
      return Optional.empty();
    }

    return Optional.of((Map) DesignerXStreamFactory.fromXML(path.toFile()));
  }

  private Optional<Map> readExchangePlanData(Path path) {
    if (Files.notExists(path)) {
      return Optional.empty();
    }

    return Optional.of((Map) DesignerXStreamFactory.fromXML(path.toFile()));
  }

  private Optional<Map> readTemplateData(Path path) {
    if (Files.notExists(path)) {
      return Optional.empty();
    }

    return Optional.of((Map) DesignerXStreamFactory.fromXML(path.toFile()));
  }

  private Optional<Map<String, Object>> readModule(Path currentPath, String name) {

    Map<String, Object> properties = new HashMap<>();
    properties.put("moduleType", ModuleType.CommonModule);
    Optional<Path> mdoFolderPath = MDOPathUtils.getMDOTypeFolderByMDOPath(currentPath, MDOType.COMMON_MODULE);

    if (mdoFolderPath.isEmpty()) {
      return Optional.empty();
    }
    var folder = mdoFolderPath.get();

    MDOPathUtils.getModulePath(ConfigurationSource.DESIGNER, folder, name, ModuleType.CommonModule)
      .ifPresent((Path modulePath) -> {
        if (modulePath.toFile().exists()) {
          properties.put("uri", modulePath.toUri());
        }
      });
    return Optional.of(properties);
  }
}
