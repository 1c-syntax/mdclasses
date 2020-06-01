package com.github._1c_syntax.mdclasses.metadata.additional;

import lombok.NonNull;
import lombok.Value;

import java.net.URI;

/**
 * Класс-описание модуля объекта
 */
@Value
public class MDOModule {

  /**
   * Тип модуля
   */
  @NonNull
  ModuleType moduleType;

  /**
   * Ссылка на файл bsl модуля
   */
  @NonNull
  URI uri;

  public MDOModule(@NonNull ModuleType moduleType, @NonNull URI uri) {
    this.moduleType = moduleType;
    this.uri = uri;
  }
}
