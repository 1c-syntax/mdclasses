package com.github._1c_syntax.mdclasses.utils;

import lombok.experimental.UtilityClass;

import java.util.Map;

@UtilityClass
public class MapExtension {

  public static String getOrEmptyString(Map<String, Object> map, String key) {
    return (String) map.getOrDefault(key, "");
  }

  public static boolean getOrFalse(Map<String, Object> map, String key) {
    return Boolean.parseBoolean((String) map.getOrDefault(key, "false"));
  }
}
