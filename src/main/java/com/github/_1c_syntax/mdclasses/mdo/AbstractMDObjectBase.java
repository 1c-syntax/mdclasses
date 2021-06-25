/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2021
 * Tymko Oleg <olegtymko@yandex.ru>, Maximov Valery <maximovvalery@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 *
 * MDClasses is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * MDClasses is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with MDClasses.
 */
package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.mdo.metadata.MetadataStorage;
import com.github._1c_syntax.mdclasses.mdo.support.MDOReference;
import com.github._1c_syntax.mdclasses.mdo.support.MDOType;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.DesignerMDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Базовый класс всех типов и дочерних объектов 1С
 */
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
public abstract class AbstractMDObjectBase extends AbstractMDO implements MDOBase {

  /**
   * Путь к файлу объекта
   */
  protected Path path;

  /**
   * Список подсистем, в состав которых входит объект
   */
  protected List<MDSubsystem> includedSubsystems = Collections.emptyList();

  /**
   * Используется для заполнения объекта на основании информации формата конфигуратора
   *
   * @param designerMDO - Служебный объект, содержащий данные в формате конфигуратора.
   */
  protected AbstractMDObjectBase(DesignerMDO designerMDO) {
    super(designerMDO);
  }

  @Override
  public MDOType getType() {
    return MetadataStorage.get(getClass()).type();
  }

  @Override
  public String getMetadataName() {
    return MetadataStorage.get(getClass()).name();
  }

  @Override
  public String getMetadataNameRu() {
    return MetadataStorage.get(getClass()).nameRu();
  }

  /**
   * Метод должен вызываться в конце чтения объекта для его дозаполнения.
   * При необходимости, можно переопределить в дочерних объектах для персональной дообработки
   */
  public void supplement() {
    if (getMdoReference() == null) {
      setMdoReference(new MDOReference(this));
    }
  }

  /**
   * Метод должен вызываться в конце чтения объекта для его дозаполнения.
   * При необходимости, можно переопределить в дочерних объектах для персональной дообработки
   */
  public void supplement(AbstractMDObjectBase parent) {
    if (getMdoReference() == null) {
      setMdoReference(new MDOReference(this, parent));
    }
  }

  /**
   * Для добавления ссылки на подсистему, в которую включен объект
   *
   * @param subsystem - Подсистема, в которую включен объект
   */
  public void addIncludedSubsystem(MDSubsystem subsystem) {
    if (includedSubsystems.equals(Collections.emptyList())) {
      includedSubsystems = new ArrayList<>();
    }
    includedSubsystems.add(subsystem);
  }
}
