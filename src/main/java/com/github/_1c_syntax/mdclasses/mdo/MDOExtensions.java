package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import lombok.NonNull;

/**
 * Интерфейс для объектов метаданных
 */
public interface MDOExtensions {
  /**
   * @return Тип метаданных, к котором относится объект
   */
  @NonNull
  MDOType getType();
}
