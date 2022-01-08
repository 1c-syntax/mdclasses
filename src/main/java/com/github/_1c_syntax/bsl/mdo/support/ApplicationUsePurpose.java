package com.github._1c_syntax.bsl.mdo.support;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * Назначения применения
 */
@AllArgsConstructor
public enum ApplicationUsePurpose {
  PERSONAL_COMPUTER(List.of("PersonalComputer", "PlatformApplication")),
  MOBILE_DEVICE(List.of("MobileDevice", "MobilePlatformApplication"));

  @Getter
  private final List<String> values;
}
