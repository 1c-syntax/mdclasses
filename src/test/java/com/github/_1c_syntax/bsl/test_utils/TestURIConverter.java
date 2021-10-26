package com.github._1c_syntax.bsl.test_utils;

import com.thoughtworks.xstream.converters.basic.URIConverter;

import java.net.URI;
import java.nio.file.Path;

/**
 * Для возможности сохранять в фикстурах пути относительно рабочего каталога
 */
public class TestURIConverter extends URIConverter {
  private final static String WORKDIR = Path.of("").toUri().getPath();

  @Override
  public String toString(Object obj) {
    return ((URI) obj).getPath().replace(WORKDIR, "");
  }
}
