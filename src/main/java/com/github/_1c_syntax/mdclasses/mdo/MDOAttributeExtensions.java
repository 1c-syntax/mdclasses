package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.AttributeType;
import lombok.NonNull;

/**
 * Интерфейс для классов-атрибутов
 */
public interface MDOAttributeExtensions {

  /**
   * Возвращает тип атрибута
   *
   * @return - тип атрибута
   */
  @NonNull
  AttributeType getAttributeType();
}
