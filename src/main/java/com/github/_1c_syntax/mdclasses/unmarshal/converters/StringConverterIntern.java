package com.github._1c_syntax.mdclasses.unmarshal.converters;

import com.thoughtworks.xstream.converters.basic.StringConverter;

public class StringConverterIntern extends StringConverter {

  public StringConverterIntern() {
    super();
  }

  @Override
  public Object fromString(String str) {
    return ((String) super.fromString(str)).intern();
  }
}
