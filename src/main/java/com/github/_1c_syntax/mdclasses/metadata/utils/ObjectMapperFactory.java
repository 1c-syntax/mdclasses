package com.github._1c_syntax.mdclasses.metadata.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ObjectMapperFactory {

  private static final XmlMapper xmlMapper = createXmlMapper();

  public static XmlMapper getXmlMapper() {
    return xmlMapper;
  }

  private static XmlMapper createXmlMapper() {
    XmlMapper xmlMapper = new XmlMapper();

    xmlMapper.registerModule(new ParameterNamesModule());

    xmlMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

    return xmlMapper;
  }
}
