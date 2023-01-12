/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2023
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
package com.github._1c_syntax.mdclasses.metadata;

import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.mdclasses.Configuration;
import com.github._1c_syntax.mdclasses.ConfigurationExtension;
import com.github._1c_syntax.mdclasses.mdo.AbstractMDObjectBase;
import com.github._1c_syntax.mdclasses.mdo.MDConfiguration;
import com.github._1c_syntax.mdclasses.utils.MDOFactory;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultRolesTest {

  @Test
  void defaultRoleExistsInOriginal() {

    var mdConfiguration =  (MDConfiguration) MDOFactory.readMDObject(Path.of("src/test/resources/metadata/original_roles/Configuration.xml")).get();

    assertThat(mdConfiguration.getChildren())
            .filteredOn(mdo -> mdo.get().getMdoType() == MDOType.ROLE)
            .hasSize(4);
    assertThat(mdConfiguration.getDefaultRoles())
            .hasSize(3)
            .containsExactlyInAnyOrder("Role.ПолныеПрава", "Role.АдминистраторСистемы", "Role.ИнтерактивноеОткрытиеВнешнихОтчетовИОбработок");

    var configuration = Configuration.create(Path.of("src/test/resources/metadata/original_roles"));

    assertThat(configuration).isNotInstanceOf(ConfigurationExtension.class);
    assertThat(configuration.getChildren())
            .filteredOn(mdObjectBase -> mdObjectBase.getMdoType() == MDOType.ROLE)
            .hasSize(4);
    assertThat(configuration.getDefaultRoles())
            .hasSize(3)
            .map(AbstractMDObjectBase::getName)
            .containsExactlyInAnyOrder("ПолныеПрава", "АдминистраторСистемы", "ИнтерактивноеОткрытиеВнешнихОтчетовИОбработок");
  }

  @Test
  void defaultRoleExistsInEdt() {

    var mdConfiguration =  (MDConfiguration) MDOFactory.readMDObject(Path.of("src/test/resources/metadata/edt/src/Configuration/Configuration.mdo")).get();

    assertThat(mdConfiguration.getChildren())
            .filteredOn(mdo -> mdo.get().getMdoType() == MDOType.ROLE)
            .hasSize(1);
    assertThat(mdConfiguration.getDefaultRoles())
            .hasSize(1)
            .contains("Role.Роль1");

    var configuration = Configuration.create(Path.of("src/test/resources/metadata/edt"));

    assertThat(configuration).isNotInstanceOf(ConfigurationExtension.class);
    assertThat(configuration.getChildren())
            .filteredOn(mdObjectBase -> mdObjectBase.getMdoType() == MDOType.ROLE)
            .hasSize(1);
    assertThat(configuration.getDefaultRoles())
            .hasSize(1)
            .map(AbstractMDObjectBase::getName)
            .contains("Роль1");
  }

  @Test
  void noDefaultRolesInOriginal() {

    var mdConfiguration =  (MDConfiguration) MDOFactory.readMDObject(Path.of("src/test/resources/metadata/original/Configuration.xml")).get();

    assertThat(mdConfiguration.getChildren())
            .filteredOn(mdo -> mdo.get().getMdoType() == MDOType.ROLE)
            .hasSize(1);
    assertThat(mdConfiguration.getDefaultRoles())
            .isEmpty();

    var configuration = Configuration.create(Path.of("src/test/resources/metadata/original"));

    assertThat(configuration).isNotInstanceOf(ConfigurationExtension.class);
    assertThat(configuration.getChildren())
            .filteredOn(mdObjectBase -> mdObjectBase.getMdoType() == MDOType.ROLE)
            .hasSize(1);
    assertThat(configuration.getDefaultRoles())
            .isEmpty();
  }

  @Test
  void noDefaultRolesInEdt() {

    var mdConfiguration =  (MDConfiguration) MDOFactory.readMDObject(Path.of("src/test/resources/metadata/edt_en/src/Configuration/Configuration.mdo")).get();

    assertThat(mdConfiguration.getChildren())
            .filteredOn(mdo -> mdo.get().getMdoType() == MDOType.ROLE)
            .isEmpty();
    assertThat(mdConfiguration.getDefaultRoles())
            .isEmpty();

    var configuration = Configuration.create(Path.of("src/test/resources/metadata/edt_en"));

    assertThat(configuration).isNotInstanceOf(ConfigurationExtension.class);
    assertThat(configuration.getChildren())
            .filteredOn(mdObjectBase -> mdObjectBase.getMdoType() == MDOType.ROLE)
            .isEmpty();
    assertThat(configuration.getDefaultRoles())
            .isEmpty();
  }
}
