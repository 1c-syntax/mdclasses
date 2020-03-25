package com.github._1c_syntax.mdclasses.utils;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.Getter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ObjectMapperFactory {
  @Getter(lazy = true)
  private static final XmlMapper xmlMapper = new XmlMapper();
}
