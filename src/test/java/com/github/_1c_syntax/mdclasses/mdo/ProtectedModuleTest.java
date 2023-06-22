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

import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.bsl.types.ModuleType;
import com.github._1c_syntax.mdclasses.Configuration;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

class ProtectedModuleTest extends AbstractMDOTest {

  private static final String SRC_EDT = "src/test/resources/metadata/skd/edt/src";
  private static final String SRC_DESIGNER = "src/test/resources/metadata/skd/original";

  ProtectedModuleTest() {
    super(MDOType.CATALOG);
  }

  @Override
  @Test
  void testEDT() {
    var mdoCommonModule = (MDCommonModule) getMDObjectEDT("CommonModules/ГлобальныйОбщийМодуль/ГлобальныйОбщийМодуль.mdo");
    checkModules(mdoCommonModule.getModules(), 1,
      "CommonModules/Глобальны", ModuleType.CommonModule);
    assertThat(mdoCommonModule.getModules()).hasSize(1);
    assertThat(mdoCommonModule.getModules().get(0).isProtected())
      .isFalse();

    var mdCatalog = (MDCatalog) getMDObjectEDT("Catalogs/ПервыйСправочник/ПервыйСправочник.mdo", SRC_EDT);
    checkModules(mdCatalog.getProtectedModules(), 1,
      "Catalogs/ПервыйСправочн", ModuleType.ObjectModule);
    assertThat(mdCatalog.getProtectedModules()).hasSize(1);
    assertThat(mdCatalog.getProtectedModules().get(0).isProtected())
      .isTrue();

    File srcPath = new File(SRC_EDT);
    Configuration configuration = Configuration.create(srcPath.toPath());
    assertThat(configuration.getProtectedModules()).hasSize(1);
  }

  @Override
  @Test
  void testDesigner() {
    var mdoCommonModule = (MDCommonModule)getMDObjectDesigner("CommonModules/ГлобальныйОбщийМодуль.xml");
    checkModules(mdoCommonModule.getModules(), 1,
        "CommonModules/ГлобальныйОбщийМ", ModuleType.CommonModule);
    final var mdoModule = mdoCommonModule.getModules().get(0);
    assertThat(mdoModule.isProtected()).isFalse();

    var mdCatalog = (MDCatalog)getMDObjectDesigner("Catalogs/ПервыйСправочник.xml", SRC_DESIGNER);
    checkModules(mdCatalog.getProtectedModules(), 1,
      "Catalogs/ПервыйСправочн", ModuleType.ObjectModule);
    assertThat(mdCatalog.getProtectedModules()).hasSize(1);
    assertThat(mdCatalog.getProtectedModules().get(0).isProtected())
        .isTrue();

    File srcPath = new File(SRC_DESIGNER);
    Configuration configuration = Configuration.create(srcPath.toPath());
    assertThat(configuration.getProtectedModules()).hasSize(1);
  }
}
