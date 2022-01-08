package com.github._1c_syntax.reader;

import com.github._1c_syntax.bsl.mdclasses.MDClass;
import com.github._1c_syntax.bsl.mdo.MDObject;

public interface MDReader {
  MDClass readConfiguration();

  MDObject readMDObject(String fullName);
}
