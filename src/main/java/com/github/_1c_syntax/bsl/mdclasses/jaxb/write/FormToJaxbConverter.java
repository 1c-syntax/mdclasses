/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
 * Tymko Oleg <olegtymko@yandex.ru>, Maximov Valery <maximovvalery@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package com.github._1c_syntax.bsl.mdclasses.jaxb.write;

import com.github._1c_syntax.bsl.mdo.children.ObjectForm;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.Form;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.FormProperties;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.MetaDataObject;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.ObjectFactory;
import com.github._1c_syntax.bsl.mdclasses.jaxb.v8_3_xcf_enums.FormType;

/**
 * Преобразует форму объекта (ObjectForm) в JAXB MetaDataObject с Form для записи в отдельный XML-файл.
 */
public final class FormToJaxbConverter {

  private static final ObjectFactory FACTORY = new ObjectFactory();

  private FormToJaxbConverter() {
  }

  /**
   * Собирает JAXB MetaDataObject с одной формой для записи в Catalogs/Имя/Forms/ИмяФормы.xml и т.д.
   *
   * @param form форма из модели mdclasses (справочник, документ и т.д.)
   * @return корневой MetaDataObject для маршаллинга
   */
  public static MetaDataObject toMetaDataObject(ObjectForm form) {
    MetaDataObject root = FACTORY.createMetaDataObject();
    root.setVersion(JaxbWriteDefaults.DEFAULT_FORMAT_VERSION);
    Form inner = FACTORY.createForm();
    inner.setUuid(form.getUuid() != null && !form.getUuid().isEmpty()
        ? form.getUuid()
        : java.util.UUID.randomUUID().toString().toLowerCase());
    inner.setProperties(buildProperties(form));
    root.setForm(inner);
    return root;
  }

  private static FormProperties buildProperties(ObjectForm f) {
    FormProperties p = FACTORY.createFormProperties();
    p.setName(f.getName() != null ? f.getName() : "");
    p.setSynonym(JaxbWriteDefaults.localStringType(JaxbWriteUtils.contentForLocalString(f.getSynonym())));
    p.setComment(f.getComment() != null ? f.getComment() : "");
    p.setObjectBelonging(JaxbWriteDefaults.objectBelongingNative());
    p.setFormType(formTypeToJaxb(f.getFormType()));
    p.setIncludeHelpInContents(Boolean.FALSE);
    return p;
  }

  private static FormType formTypeToJaxb(com.github._1c_syntax.bsl.mdo.support.FormType ft) {
    if (ft == null) {
      return FormType.MANAGED;
    }
    return ft == com.github._1c_syntax.bsl.mdo.support.FormType.ORDINARY ? FormType.ORDINARY : FormType.MANAGED;
  }
}
