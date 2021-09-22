package com.github._1c_syntax.bsl.mdo.data_storage;

import lombok.Value;

/**
 * Реализация пустого содержимого макета
 */
@Value
public class EmptyTemplateData implements TemplateData {
  private static final EmptyTemplateData EMPTY = new EmptyTemplateData();

  @Override
  public boolean isEmpty() {
    return true;
  }

  /**
   * Возвращает ссылку на пустое содержимое макета
   *
   * @return Пустое содержимое макета
   */
  public static EmptyTemplateData getEmpty() {
    return EMPTY;
  }
}
