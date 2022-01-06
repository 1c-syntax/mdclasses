package com.github._1c_syntax.reader;

import com.github._1c_syntax.bsl.mdclasses.MDClass;
import com.github._1c_syntax.bsl.types.ConfigurationSource;

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
    return getReader(rootPath, false);
  }

  public MDReader getReader(@NonNull Path rootPath, boolean skipSupport) {
    var reader = readers.get(rootPath);
    if (reader == null) {
      var configurationSource = MDOPathUtils.getConfigurationSourceByPath(rootPath);
      reader = getReader(configurationSource, rootPath, skipSupport);
      readers.put(rootPath, reader);
    }
    return reader;
  }

  public MDClass readConfiguration(@NonNull Path rootPath) {
    return getReader(rootPath, false).readConfiguration();
  }

  public MDClass readConfiguration(@NonNull Path rootPath, boolean skipSupport) {
    return getReader(rootPath, skipSupport).readConfiguration();
  }

  private MDReader getReader(ConfigurationSource configurationSource, Path rootPath, boolean skipSupport) {
    if (configurationSource == ConfigurationSource.DESIGNER) {
      return new DesignerReader(rootPath, skipSupport);
    } else if (configurationSource == ConfigurationSource.EDT) {
      return new EDTReader(rootPath, skipSupport);
    } else {
      return new FakeReader();
    }
  }

}
