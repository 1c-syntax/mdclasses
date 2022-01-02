package com.github._1c_syntax.bsl.mdo.support;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
public class ValueType {

  public static final ValueType EMPTY = new ValueType("UNKNOWN");

  /**
   * Строковое представление типа
   */
  List<String> types;

  /**
   * Квалификатор строки
   */
  StringQualifier stringQualifier;

  /**
   * Конструктор для типа Строка
   *
   * @param stringQualifier Квалификатор строки
   */
  public ValueType(@NonNull StringQualifier stringQualifier) {
    this.types = List.of("xs:string");
    this.stringQualifier = stringQualifier;
  }

  /**
   * Конструктор для типа без квалификаторов
   *
   * @param type Строковое представление типа
   */
  public ValueType(@NonNull String type) {
    this.types = List.of(type);
    this.stringQualifier = StringQualifier.EMPTY;
  }

  /**
   * Конструктор для типа без квалификаторов
   *
   * @param types Список строковых представлений типов
   */
  public ValueType(@NonNull List<String> types) {
    this.types = types;
    this.stringQualifier = StringQualifier.EMPTY;
  }

  @Value
  @AllArgsConstructor
  public static class StringQualifier {
    public static final StringQualifier EMPTY = new StringQualifier(0, "");
    int length;
    String allowedLength;
  }
}
