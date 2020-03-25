package com.github._1c_syntax.mdclasses.metadata;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class SupportConfiguration {
  String name;
  String provider;
  String version;
}
