package com.github._1c_syntax.mdclasses.metadata.utils;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ObjectMapperFactory {

  private static final XmlMapper xmlMapper = createXmlMapper();

  public static XmlMapper getXmlMapper() {
    return xmlMapper;
  }

  private static XmlMapper createXmlMapper() {
    return new XmlMapper();
  }
}
