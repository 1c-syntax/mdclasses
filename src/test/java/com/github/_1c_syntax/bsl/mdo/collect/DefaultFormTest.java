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
package com.github._1c_syntax.bsl.mdo.collect;

import com.github._1c_syntax.bsl.mdo.BusinessProcess;
import com.github._1c_syntax.bsl.mdo.Catalog;
import com.github._1c_syntax.bsl.mdo.FormOwner;
import com.github._1c_syntax.bsl.mdo.support.DefaultFormKind;
import com.github._1c_syntax.bsl.test_utils.Fixtures;
import com.github._1c_syntax.bsl.types.MdoReference;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultFormTest {

  @ParameterizedTest
  @CsvSource(
    {
      "true, mdclasses, BusinessProcesses.БизнесПроцесс1",
      "false, mdclasses, BusinessProcesses.БизнесПроцесс1"
    }
  )
  void testNoDefaultForm(ArgumentsAccessor argumentsAccessor) {
    var mdo = Fixtures.get(argumentsAccessor);
    assertThat(mdo).isInstanceOf(BusinessProcess.class);

    var bp = (BusinessProcess) mdo;

    // Все формы по умолчанию отсутствуют
    assertThat(bp.getDefaultFormLink(DefaultFormKind.OBJECT_FORM)).isEqualTo(MdoReference.EMPTY);
    assertThat(bp.getDefaultForm(DefaultFormKind.OBJECT_FORM)).isEmpty();
    assertThat(bp.getDefaultFormLink(DefaultFormKind.AUX_OBJECT_FORM)).isEqualTo(MdoReference.EMPTY);
    assertThat(bp.getDefaultForm(DefaultFormKind.AUX_OBJECT_FORM)).isEmpty();
    assertThat(bp.getDefaultFormLink(DefaultFormKind.FOLDER_FORM)).isEqualTo(MdoReference.EMPTY);

    // getFormByLink с несуществующей ссылкой
    assertThat(bp.getFormByLink(MdoReference.create("BusinessProcess.Unknown.Form.Unknown"))).isEmpty();
  }

  @ParameterizedTest
  @CsvSource(
    {
      "true, ssl_3_1, BusinessProcesses.Задание",
      "false, ssl_3_1, BusinessProcesses.Задание",
      "true, ssl_3_2, BusinessProcesses.Задание",
      "false, ssl_3_2, BusinessProcesses.Задание",
      "true, mdclasses, Catalogs.Справочник1",
      "false, mdclasses, Catalogs.Справочник1"
    }
  )
  void testHasDefaultForm(ArgumentsAccessor argumentsAccessor) {
    var mdo = Fixtures.get(argumentsAccessor);
    assertThat(mdo).isInstanceOfAny(BusinessProcess.class, Catalog.class);

    var formOwner = (FormOwner) mdo;

    var formLink = formOwner.getDefaultFormLink(DefaultFormKind.OBJECT_FORM);
    assertThat(formLink.isEmpty()).isFalse();

    var form = formOwner.getDefaultForm(DefaultFormKind.OBJECT_FORM);
    assertThat(form).isPresent();
    assertThat(form.get().getName()).isNotEmpty();

    var formByLink = formOwner.getFormByLink(formLink);
    assertThat(formByLink).isPresent();

    var existingLink = formOwner.getDefaultFormMap().get(DefaultFormKind.OBJECT_FORM);
    assertThat(existingLink).isEqualTo(formLink);

    // getFormByLink с несуществующей ссылкой
    assertThat(formOwner.getFormByLink(MdoReference.create("MD.Unknown.Form.Unknown"))).isEmpty();
  }

}
