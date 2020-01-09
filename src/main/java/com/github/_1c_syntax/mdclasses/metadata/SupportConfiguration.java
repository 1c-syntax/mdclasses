package com.github._1c_syntax.mdclasses.metadata;

  import lombok.Value;

@Value
public class SupportConfiguration {
  private String name;
  private String provider;
  private String version;

  public SupportConfiguration(String name, String provider, String version) {
    this.name = name;
    this.provider = provider;
    this.version = version;
  }

}
