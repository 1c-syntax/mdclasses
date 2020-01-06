package com.github._1c_syntax.mdclasses.metadata.additional;

import lombok.Getter;

public class CompatibilityMode {

  @Getter
  private int major = 8;
  @Getter
  private int minor = 0;
  @Getter
  private int version = 0;

  private static final String DONT_USE = "DontUse";

  public CompatibilityMode() {
    setVersionComponents(3, 99);
  }

  public CompatibilityMode(String value) {

    if (value.equalsIgnoreCase(DONT_USE)) {
      setVersionComponents(3, 99);
      return;
    }

    // парсим версию, например Version_8_3_10
    String newValue = value.toUpperCase().replace("VERSION_", "");

    String[] array = newValue.split("([_.])");
    setVersionComponents(Integer.parseInt(array[1]), Integer.parseInt(array[2]));

  }

  public CompatibilityMode(int minor, int version) {
    setVersionComponents(minor, version);
  }

  private void setVersionComponents(int minor, int version) {
    this.minor = minor;
    this.version = version;
  }

  public static int compareTo(CompatibilityMode versionA, CompatibilityMode versionB) {

    // TODO: переделать в цикл
    if (versionA.major == versionB.major) {
      if (versionA.minor == versionB.minor) {
        if (versionA.version == versionB.version) {
          return 0;
        } else if (versionA.version >= versionB.version) {
          return -1;
        } else {
          return 1;
        }
      } else if (versionA.minor >= versionB.minor) {
        return -1;
      } else {
        return 1;
      }
    } else if (versionA.major >= versionB.major) {
      return -1;
    } else {
      return 1;
    }

  }

}
