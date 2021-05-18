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

import com.github._1c_syntax.mdclasses.mdo.support.MDOType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MDLanguageTest extends AbstractMDOTest {
  MDLanguageTest() {
    super(MDOType.LANGUAGE);
  }

  @Override
  @Test
  void testEDT() {
    // TODO язык входит в состав конфигурации и отдельно не существует
    assertThat(true).isTrue();
  }

  @Override
  @Test
  void testDesigner() {
    var mdo = getMDObjectDesigner("Languages/Русский.xml");
    checkBaseField(mdo, MDLanguage.class, "Русский",
      "1b5f5cd6-14b2-422e-ab6c-1103fd375982");
    checkNoChildren(mdo);
    checkNoModules(mdo);
    assertThat(((MDLanguage) mdo).getLanguageCode()).isEqualTo("ru");
  }

}
