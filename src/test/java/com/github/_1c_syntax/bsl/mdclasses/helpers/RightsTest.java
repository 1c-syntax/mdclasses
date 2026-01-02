/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
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
package com.github._1c_syntax.bsl.mdclasses.helpers;

import com.github._1c_syntax.bsl.mdclasses.CF;
import com.github._1c_syntax.bsl.mdclasses.Configuration;
import com.github._1c_syntax.bsl.mdclasses.ConfigurationExtension;
import com.github._1c_syntax.bsl.mdclasses.MDClasses;
import com.github._1c_syntax.bsl.mdo.support.RoleRight;
import com.github._1c_syntax.bsl.types.MdoReference;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class RightsTest {

  @Test
  void rightAccessCf() {
    var mdc = MDClasses.createConfiguration(Path.of("src/test/resources/ext/designer/ssl_3_1/src/cf"));
    assertThat(mdc).isInstanceOf(Configuration.class);

    var cf = (CF) mdc;
    assertThat(Rights.rightAccess(cf, RoleRight.ADMINISTRATION)).isTrue();
    assertThat(Rights.rightAccess(cf, RoleRight.THICK_CLIENT)).isTrue();
    assertThat(Rights.rightAccess(cf, RoleRight.ALL_FUNCTIONS_MODE)).isFalse();
    assertThat(cf.rightAccess(RoleRight.WEB_CLIENT)).isTrue();

    var mdv = cf.findChild("Document.Анкета");
    assertThat(mdv).isPresent();
    var md = mdv.get();
    assertThat(Rights.rightAccess(cf, RoleRight.DELETE, md)).isTrue();
    assertThat(Rights.rightAccess(cf, RoleRight.VIEW, md)).isTrue();
    assertThat(Rights.rightAccess(cf, RoleRight.USE, md)).isFalse();
    assertThat(cf.rightAccess(RoleRight.POSTING, md)).isTrue();

    mdv = cf.findChild("Task.ЗадачаИсполнителя.Command.Перенаправить");
    assertThat(mdv).isPresent();
    md = mdv.get();
    assertThat(Rights.rightAccess(cf, RoleRight.VIEW, md)).isFalse();
    assertThat(Rights.rightAccess(cf, RoleRight.USE, md)).isFalse();
    assertThat(cf.rightAccess(RoleRight.START, md)).isFalse();

    var mdoReference = MdoReference.create("Enum.ВариантыВажностиЗадачи.EnumValue.Обычная");
    assertThat(Rights.rightAccess(cf, RoleRight.VIEW, mdoReference)).isFalse();
    assertThat(Rights.rightAccess(cf, RoleRight.DELETE, mdoReference)).isFalse();
    assertThat(cf.rightAccess(RoleRight.INTERACTIVE_DELETE, mdoReference)).isFalse();
  }

  @Test
  void rightAccessCfe() {
    var mdc = MDClasses.createConfiguration(Path.of("src/test/resources/ext/edt/mdclasses_ext/configuration"));
    assertThat(mdc).isInstanceOf(ConfigurationExtension.class);

    var cfe = (CF) mdc;
    assertThat(Rights.rightAccess(cfe, RoleRight.ADMINISTRATION)).isTrue();
    assertThat(Rights.rightAccess(cfe, RoleRight.THICK_CLIENT)).isFalse();
    assertThat(Rights.rightAccess(cfe, RoleRight.DELETE)).isFalse();

    var mdv = cfe.findChild("AccountingRegister.РегистрБухгалтерии1");
    assertThat(mdv).isPresent();
    var md = mdv.get();
    assertThat(Rights.rightAccess(cfe, RoleRight.DELETE, md)).isFalse();
    assertThat(Rights.rightAccess(cfe, RoleRight.VIEW, md)).isFalse();
    assertThat(Rights.rightAccess(cfe, RoleRight.USE, md)).isFalse();

    var mdoReference = MdoReference.create("Catalog.Справочник1");
    assertThat(Rights.rightAccess(cfe, RoleRight.DELETE, mdoReference)).isTrue();
    assertThat(Rights.rightAccess(cfe, RoleRight.INTERACTIVE_DELETE_MARKED, mdoReference)).isTrue();
    assertThat(Rights.rightAccess(cfe, RoleRight.UPDATE_DATA_HISTORY_SETTINGS, mdoReference)).isFalse();
  }

  @Test
  void rolesAccessCf() {
    var mdc = MDClasses.createConfiguration(Path.of("src/test/resources/ext/edt/ssl_3_1/configuration"));
    assertThat(mdc).isInstanceOf(Configuration.class);

    var cf = (CF) mdc;
    assertThat(Rights.rolesAccess(cf, RoleRight.ADMINISTRATION))
      .hasSize(2)
      .anyMatch(role -> role.getName().equals("АдминистраторСистемы"));

    assertThat(Rights.rolesAccess(cf, RoleRight.THICK_CLIENT))
      .hasSize(2)
      .anyMatch(role -> role.getName().equals("ЗапускТолстогоКлиента"));

    assertThat(Rights.rolesAccess(cf, RoleRight.ALL_FUNCTIONS_MODE)).isEmpty();
    assertThat(cf.rolesAccess(RoleRight.ANALYTICS_SYSTEM_CLIENT)).hasSize(78);

    var mdv = cf.findChild("Document.Анкета");
    assertThat(mdv).isPresent();
    var md = mdv.get();
    assertThat(Rights.rolesAccess(cf, RoleRight.DELETE, md)).hasSize(1);
    assertThat(Rights.rolesAccess(cf, RoleRight.INTERACTIVE_DELETE, md)).isEmpty();
    assertThat(Rights.rolesAccess(cf, RoleRight.VIEW, md)).hasSize(1);
    assertThat(Rights.rolesAccess(cf, RoleRight.USE, md)).isEmpty();
    assertThat(cf.rolesAccess(RoleRight.INTERACTIVE_DELETE_MARKED, md)).isEmpty();

    mdv = cf.findChild("Task.ЗадачаИсполнителя.Command.Перенаправить");
    assertThat(mdv).isPresent();
    md = mdv.get();
    assertThat(Rights.rolesAccess(cf, RoleRight.VIEW, md)).isEmpty();
    assertThat(Rights.rolesAccess(cf, RoleRight.USE, md)).isEmpty();
    assertThat(cf.rolesAccess(RoleRight.DELETE, md)).isEmpty();

    var mdoReference = MdoReference.create("Enum.ВариантыВажностиЗадачи.EnumValue.Обычная");
    assertThat(Rights.rolesAccess(cf, RoleRight.VIEW, mdoReference)).isEmpty();
    assertThat(Rights.rolesAccess(cf, RoleRight.DELETE, mdoReference)).isEmpty();
    assertThat(cf.rolesAccess(RoleRight.INSERT, mdoReference)).isEmpty();
  }

  @Test
  void rolesAccessCfe() {
    var mdc = MDClasses.createConfiguration(Path.of("src/test/resources/ext/designer/mdclasses_ext/src/cf"));
    assertThat(mdc).isInstanceOf(ConfigurationExtension.class);

    var cfe = (CF) mdc;
    assertThat(Rights.rolesAccess(cfe, RoleRight.ADMINISTRATION))
      .hasSize(1)
      .anyMatch(role -> role.getName().equals("Роль2"));

    assertThat(Rights.rolesAccess(cfe, RoleRight.THICK_CLIENT))
      .hasSize(1)
      .anyMatch(role -> role.getName().equals("Роль2"));

    assertThat(Rights.rolesAccess(cfe, RoleRight.DELETE)).isEmpty();

    var mdv = cfe.findChild("AccountingRegister.РегистрБухгалтерии1");
    assertThat(mdv).isPresent();
    var md = mdv.get();
    assertThat(Rights.rolesAccess(cfe, RoleRight.DELETE, md)).isEmpty();
    assertThat(Rights.rolesAccess(cfe, RoleRight.VIEW, md)).isEmpty();

    var mdoReference = MdoReference.create("Catalog.Справочник1");
    assertThat(Rights.rolesAccess(cfe, RoleRight.DELETE, mdoReference)).hasSize(1);
    assertThat(Rights.rolesAccess(cfe, RoleRight.INTERACTIVE_DELETE_MARKED, mdoReference)).hasSize(1);
    assertThat(Rights.rolesAccess(cfe, RoleRight.UPDATE_DATA_HISTORY_SETTINGS, mdoReference)).isEmpty();
  }
}