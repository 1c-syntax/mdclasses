package com.github._1c_syntax.mdclasses.metadata.additional;

import com.github._1c_syntax.mdclasses.mdo.MDOAttribute;
import com.github._1c_syntax.mdclasses.mdo.MDObjectBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * Класс-ссылка на объект в формате ВидОбъектаМетаднных.ИмяОбъекта
 */
@Data
@EqualsAndHashCode(of = {"mdoRef"})
@ToString(of = {"mdoRef"})
public class MDOReference {

  /**
   * Тип объекта метаданных
   */
  @NonNull
  MDOType type;

  /**
   * Строковое представление ссылки
   */
  @NonNull
  String mdoRef;

  public MDOReference(MDObjectBase mdo) {
    type = mdo.getType();
    mdoRef = getType().getClassName() + "." + mdo.getName();
  }

  /**
   * Создает ссылку для дочерних объектов
   *
   * @param mdo    - Объект метаданных
   * @param parent - Родительский объект
   */
  public MDOReference(MDObjectBase mdo, MDObjectBase parent) {
    this(mdo);
    if (parent.getMdoReference() != null) {
      mdoRef = parent.getMdoReference().getMdoRef() + "." + mdoRef;
    }
  }

  /**
   * Создает ссылку для атрибутов объекта
   *
   * @param mdo    - Объект-атрибут
   * @param parent - Родительский объект
   */
  public MDOReference(MDOAttribute mdo, MDObjectBase parent) {
    type = mdo.getType();
    mdoRef = mdo.getAttributeType().getClassName() + "." + mdo.getName();
    if (parent.getMdoReference() != null) {
      mdoRef = parent.getMdoReference().getMdoRef() + "." + mdoRef;
    }
  }
}
