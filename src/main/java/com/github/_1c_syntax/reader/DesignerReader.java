package com.github._1c_syntax.reader;

import com.github._1c_syntax.bsl.mdclasses.MDClass;
import com.github._1c_syntax.bsl.mdclasses.MDClasses;
import com.github._1c_syntax.bsl.mdo.MDObject;
import com.github._1c_syntax.bsl.types.ConfigurationSource;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.mdclasses.utils.MDOPathUtils;
import com.github._1c_syntax.reader.designer.DesignerXStreamFactory;
import com.github._1c_syntax.support_configuration.ParseSupportData;

import java.nio.file.Path;
import java.util.Optional;

public class DesignerReader implements MDReader {
  private final Path rootPath;

  public DesignerReader(Path path) {
    rootPath = path;
    MDOPathUtils.getParentConfigurationsPath(ConfigurationSource.DESIGNER, rootPath)
      .ifPresent(ParseSupportData::readSimple);
  }

  @Override
  public MDClass readConfiguration() {
    var mdc = MDOPathUtils.getMDOPath(ConfigurationSource.DESIGNER, rootPath,
        MDOType.CONFIGURATION, MDOType.CONFIGURATION.getName())
      .flatMap(this::readConfiguration);
    return mdc.orElse(MDClasses.createConfiguration());
  }

  @Override
  public MDObject readMDObject(String fullName) {
    var dotPosition = fullName.indexOf('.');
    var type = MDOType.fromValue(fullName.substring(0, dotPosition));
    var name = fullName.substring(dotPosition + 1);

    if (type.isPresent()) {
      var path = MDOPathUtils.getMDOPath(ConfigurationSource.DESIGNER,
        rootPath, type.get(), name);

      if (path.isPresent()) {
        var mdo = (MDObject) DesignerXStreamFactory.fromXML(path.get().toFile());
        return mdo;
      }
    }
    return null;
  }

  private Optional<MDClass> readConfiguration(Path path) {
    return Optional.ofNullable((MDClass) DesignerXStreamFactory.fromXML(path.toFile()));
  }
}
