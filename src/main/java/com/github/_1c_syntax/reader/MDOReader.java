package com.github._1c_syntax.reader;

import com.github._1c_syntax.bsl.mdclasses.MDClass;
import com.github._1c_syntax.bsl.types.ConfigurationSource;
import com.github._1c_syntax.mdclasses.utils.MDOUtils;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.nio.file.Path;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Читатель MDO
 */
@UtilityClass
public class MDOReader {

  private static ConcurrentHashMap<Path, MDReader> readers = new ConcurrentHashMap<>();

  public MDReader getReader(@NonNull Path rootPath) {
    var reader = readers.get(rootPath);
    if (reader == null) {
      var configurationSource = MDOUtils.getConfigurationSourceByPath(rootPath);
      reader = getReader(configurationSource, rootPath);
      readers.put(rootPath, reader);
    }
    return reader;
  }

  public MDClass readConfiguration(@NonNull Path rootPath) {
    return getReader(rootPath).readConfiguration();
  }

  private MDReader getReader(ConfigurationSource configurationSource, Path rootPath) {
    if (configurationSource == ConfigurationSource.DESIGNER) {
      return new DesignerReader(rootPath);
    } else if (configurationSource == ConfigurationSource.EDT) {
      return new EDTReader(rootPath);
    } else {
      return new FakeReader();
    }
  }

}
