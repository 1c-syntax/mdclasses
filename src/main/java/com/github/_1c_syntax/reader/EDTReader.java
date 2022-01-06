package com.github._1c_syntax.reader;

import com.github._1c_syntax.bsl.mdclasses.MDClass;
import com.github._1c_syntax.bsl.mdo.MDObject;

import java.nio.file.Path;

public class EDTReader implements MDReader {
  private final Path configurationPath;

  public EDTReader(Path path) {
    configurationPath = path;
  }

  public EDTReader(Path path, boolean skipSupport) {
    configurationPath = path;
  }

  @Override
  public MDClass readConfiguration() {
    return null;
  }

  @Override
  public MDObject readMDObject(String fullName) {
    return null;
  }
}
