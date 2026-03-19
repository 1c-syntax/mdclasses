/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
 * Tymko Oleg <olegtymko@yandex.ru>, Maximov Valery <maximovvalery@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package com.github._1c_syntax.bsl.mdclasses.jaxb.write;

import com.github._1c_syntax.bsl.mdo.Task;
import com.github._1c_syntax.bsl.mdo.children.ObjectForm;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.MetaDataObject;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.ObjectFactory;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.TaskChildObjects;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.TaskProperties;

/**
 * Преобразует задачу mdclasses в JAXB-DTO для записи Designer XML.
 */
public final class TaskToJaxbConverter {

  private static final ObjectFactory FACTORY = new ObjectFactory();

  private TaskToJaxbConverter() {
  }

  /**
   * Собирает JAXB MetaDataObject из задачи.
   *
   * @param task задача из модели mdclasses
   * @return корневой элемент для маршаллинга в Designer XML
   */
  public static MetaDataObject toMetaDataObject(Task task) {
    MetaDataObject root = FACTORY.createMetaDataObject();
    root.setVersion(JaxbWriteDefaults.DEFAULT_FORMAT_VERSION);
    com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.Task inner = FACTORY.createTask();
    inner.setUuid(task.getUuid() != null ? task.getUuid() : "");
    inner.setProperties(buildProperties(task));
    inner.setChildObjects(buildChildObjects(task));
    root.setTask(inner);
    return root;
  }

  private static TaskProperties buildProperties(Task t) {
    TaskProperties p = FACTORY.createTaskProperties();
    p.setName(t.getName());
    p.setSynonym(JaxbWriteDefaults.localStringType(JaxbWriteUtils.contentForLocalString(t.getSynonym())));
    p.setComment(t.getComment() != null ? t.getComment() : "");
    return p;
  }

  private static TaskChildObjects buildChildObjects(Task t) {
    TaskChildObjects co = FACTORY.createTaskChildObjects();
    if (t.getForms() != null) {
      for (ObjectForm form : t.getForms()) {
        co.getForm().add(form.getName() != null ? form.getName() : "");
      }
    }
    return co;
  }
}
