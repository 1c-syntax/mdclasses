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
package com.github._1c_syntax.bsl.test_utils;

import com.github._1c_syntax.bsl.mdclasses.MDClasses;
import com.github._1c_syntax.bsl.mdo.MDObject;
import com.github._1c_syntax.bsl.mdo.support.MdoReference;
import com.github._1c_syntax.bsl.mdo.support.ObjectBelonging;
import com.github._1c_syntax.bsl.types.ConfigurationSource;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.mdclasses.utils.MDOFactory;
import com.github._1c_syntax.mdclasses.utils.MDOPathUtils;
import lombok.SneakyThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import static com.github._1c_syntax.bsl.test_utils.Assertions.assertThat;

abstract public class AbstractMDObjectTest<T extends MDObject> {
  private final Class<T> clazz;
  private static final String SRC_DESIGNER = "src/test/resources/metadata/original";
  private static final String SRC_DESIGNER18 = "src/test/resources/metadata/original_3_18";

  protected AbstractMDObjectTest(Class<T> clazz) {
    this.clazz = clazz;
  }

  /**
   * Возвращает прочитанный объект
   */
  protected T getMDObject(String name) {
    return getMDObject(Paths.get(SRC_DESIGNER,
      name + MDOPathUtils.mdoExtension(ConfigurationSource.DESIGNER, true)));
  }

  /**
   * Возвращает прочитанный объект
   */
  protected T getMDObject18(String name) {
    return getMDObject(Paths.get(SRC_DESIGNER18,
      name + MDOPathUtils.mdoExtension(ConfigurationSource.DESIGNER, true)));
  }

  /**
   * Проверяет базовый набор реквизитов
   */
  @SneakyThrows
  protected void checkBaseField(MDObject mdo, MDOType type, String name, String uuid, ObjectBelonging objectBelonging) {

    var fields = clazz.getDeclaredFields();
    for (var field : fields) {
      assertThat(field, true).isNotNull(mdo);
    }

    assertThat(mdo.getType()).isEqualTo(type);
    assertThat(mdo.getName()).isEqualTo(name);

    assertThat(mdo.getUuid()).isEqualTo(uuid);
    assertThat(mdo.getMdoReference())
      .isNotNull().extracting(MdoReference::getType)
      .isEqualTo(type);
    assertThat(mdo.getMdoReference().getMdoRef())
      .isEqualTo(type.getName() + "." + name);
    assertThat(mdo.getMdoReference().getMdoRefRu())
      .isEqualTo(type.getNameRu() + "." + name);

    assertThat(mdo.getObjectBelonging()).isEqualTo(objectBelonging);
  }

  protected T getMDObject(Path path) {
    var mdo = MDOFactory.readMDObject(path);
    assertThat(mdo).isPresent();
    return clazz.cast(MDClasses.build(mdo.get().buildMDObject()));
  }
}
