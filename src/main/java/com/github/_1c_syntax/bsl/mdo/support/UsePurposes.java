package com.github._1c_syntax.bsl.mdo.support;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Назначения использования приложения и форм
 */
@AllArgsConstructor
public enum UsePurposes {
  PLATFORM_APPLICATION("PersonalComputer", "PlatformApplication",
    "Приложение для платформы"),
  MOBILE_PLATFORM_APPLICATION("MobileDevice", "MobilePlatformApplication",
    "Приложение для мобильной платформы");

  @Getter
  @Accessors(fluent = true)
  private final String valueVar1;

  @Getter
  @Accessors(fluent = true)
  private final String valueVar2;

  @Getter
  @Accessors(fluent = true)
  private final String valueRu;
}