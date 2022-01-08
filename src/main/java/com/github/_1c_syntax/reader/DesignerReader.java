package com.github._1c_syntax.reader;

import com.github._1c_syntax.bsl.mdclasses.MDClass;
import com.github._1c_syntax.bsl.mdclasses.MDClasses;
import com.github._1c_syntax.bsl.mdo.MDObject;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.reader.designer.DesignerPaths;
import com.github._1c_syntax.reader.designer.DesignerXStreamFactory;
import com.github._1c_syntax.supconf.ParseSupportData;

import java.nio.file.Path;
import java.util.Optional;

public class DesignerReader implements MDReader {
  private final Path rootPath;

  public DesignerReader(Path path) {
    this(path, false);
  }

  public DesignerReader(Path path, boolean skipSupport) {
    rootPath = path;
    if (!skipSupport) {
      ParseSupportData.readSimple(DesignerPaths.parentConfigurationsPath(rootPath));
    }
  }

  @Override
  public MDClass readConfiguration() {
    var mdc = readConfiguration(
      DesignerPaths.mdoPath(rootPath, MDOType.CONFIGURATION, MDOType.CONFIGURATION.getName())
    );
    return mdc.orElse(MDClasses.createConfiguration());
  }

  @Override
  public MDObject readMDObject(String fullName) {
    var dotPosition = fullName.indexOf('.');
    var type = MDOType.fromValue(fullName.substring(0, dotPosition));
    var name = fullName.substring(dotPosition + 1);

    if (type.isPresent()) {
      var path = DesignerPaths.mdoPath(rootPath, type.get(), name);
      var mdo = (MDObject) DesignerXStreamFactory.fromXML(path.toFile());
      return mdo;

    }
    return null;
  }

  private Optional<MDClass> readConfiguration(Path path) {
    return Optional.ofNullable((MDClass) DesignerXStreamFactory.fromXML(path.toFile()));
  }
}
