package com.github._1c_syntax.bsl.mdo;

import com.github._1c_syntax.bsl.types.ValueTypeDescription;

/**
 * Расширение - владелец имеет тип значения
 */
public interface ValueTypeOwner {
  /**
   * Возвращает описание типа значения
   */
  ValueTypeDescription getValueType();
}
