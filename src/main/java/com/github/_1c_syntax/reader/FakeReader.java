package com.github._1c_syntax.reader;

import com.github._1c_syntax.bsl.mdclasses.MDClass;
import com.github._1c_syntax.bsl.mdo.MDObject;

public class FakeReader implements MDReader {
  @Override
  public MDClass readConfiguration() {
    return null;
  }

  @Override
  public MDObject readMDObject(String fullName) {
    return null;
  }
}
