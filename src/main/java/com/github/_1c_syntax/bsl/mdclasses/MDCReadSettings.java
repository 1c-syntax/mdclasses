package com.github._1c_syntax.bsl.mdclasses;

import lombok.Builder;
import lombok.Value;

/**
 * Настройки чтения MDC
 */
@Value
@Builder
public class MDCReadSettings {
  /**
   * Настройки по умолчанию
   */
  public static final MDCReadSettings DEFAULT = MDCReadSettings.builder().build();

  /**
   * Шаблон с отключением только чтения поддержки
   */
  public static final MDCReadSettings SKIP_SUPPORT = MDCReadSettings.builder().skipSupport(true).build();

  /**
   * Пропускать чтение настроек поставки конфигурации
   */
  boolean skipSupport;

  /**
   * Пропускать чтение содержимого ролей
   */
  boolean skipRoleData;

  /**
   * Пропускать чтение содержимого xdto пакетов
   */
  boolean skipXdtoPackage;

  /**
   * Пропускать чтение элементов форм
   */
  boolean skipFormElements;

  /**
   * Пропускать чтение элементов макетов системы компоновки
   */
  boolean skipDataCompositionSchemaElements;

}
