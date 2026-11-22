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

import com.github._1c_syntax.bsl.mdclasses.CF;
import com.github._1c_syntax.bsl.mdo.CommonForm;
import com.github._1c_syntax.bsl.mdo.support.DefaultFormKind;
import com.github._1c_syntax.bsl.test_utils.Fixtures;
import com.github._1c_syntax.bsl.types.MdoReference;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultConfigurationFormTest {

  @ParameterizedTest
  @CsvSource(
    {
      "true, mdclasses_3_25",
      "false, mdclasses_3_25",
      "true, mdclasses_3_27",
      "false, mdclasses_3_27"
    }
  )
  void testHasDefaultForms(ArgumentsAccessor argumentsAccessor) {
    var cf = getConfiguration(argumentsAccessor);
    assertThat(cf).isNotNull();

    assertLink(cf, DefaultFormKind.REPORT_FORM, "CommonForm.ФормаОтчета");
    assertLink(cf, DefaultFormKind.REPORT_VARIANT_FORM, "CommonForm.ФормаВариантаОтчета");
    assertLink(cf, DefaultFormKind.REPORT_SETTINGS_FORM, "CommonForm.ФормаНастроекОтчета");
    assertLink(cf, DefaultFormKind.DYNAMIC_LIST_SETTINGS_FORM, "CommonForm.ФормаНастроекДинамическогоСписка");
    assertLink(cf, DefaultFormKind.DATA_HISTORY_CHANGE_HISTORY_FORM, "CommonForm.ФормаИсторииИзмененийИсторииДанных");
    assertLink(cf, DefaultFormKind.DATA_HISTORY_VERSION_DATA_FORM, "CommonForm.ФормаДанныхВерсииИсторииДанных");
    assertLink(cf, DefaultFormKind.DATA_HISTORY_VERSION_DIFFERENCES_FORM, "CommonForm.ФормаРазличийВерсийИсторииДанных");

    assertThat(cf.getDefaultFormLink(DefaultFormKind.CONSTANTS_FORM)).isEqualTo(MdoReference.EMPTY);
    assertThat(cf.getDefaultFormLink(DefaultFormKind.COLLABORATION_SYSTEM_USERS_CHOICE_FORM)).isEqualTo(MdoReference.EMPTY);
    assertThat(cf.getDefaultFormLink(DefaultFormKind.AUX_REPORT_FORM)).isEqualTo(MdoReference.EMPTY);
  }

  @ParameterizedTest
  @CsvSource(
    {
      "true, mdclasses_3_25",
      "false, mdclasses_3_25"
    }
  )
  void testResolveDefaultForm(ArgumentsAccessor argumentsAccessor) {
    var cf = getConfiguration(argumentsAccessor);

    var form = cf.getDefaultForm(DefaultFormKind.REPORT_FORM);
    assertThat(form).isPresent();
    assertThat(form.get()).isInstanceOf(CommonForm.class);
    assertThat(form.get().getName()).isEqualTo("ФормаОтчета");
    assertThat(cf.getCommonForms()).contains(form.get());

    assertThat(cf.getDefaultForm(DefaultFormKind.CONSTANTS_FORM)).isEmpty();
    assertThat(cf.getDefaultForm(DefaultFormKind.AUX_REPORT_FORM)).isEmpty();
  }

  @ParameterizedTest
  @CsvSource(
    {
      "true, mdclasses_3_25",
      "false, mdclasses_3_25"
    }
  )
  void testDesignerEdtIdentical(ArgumentsAccessor argumentsAccessor) {
    var formatEDT = argumentsAccessor.getBoolean(0);
    var pack = argumentsAccessor.getString(1);

    var cf = getConfiguration(argumentsAccessor);
    var otherFormat = (CF) Fixtures.get(pack, "Configuration", !formatEDT);

    assertThat(cf.getDefaultFormMap()).isEqualTo(otherFormat.getDefaultFormMap());
  }

  @ParameterizedTest
  @CsvSource(
    {
      "ConstantsForm, CONSTANTS_FORM",
      "DataHistoryChangeHistoryForm, DATA_HISTORY_CHANGE_HISTORY_FORM",
      "DataHistoryVersionDataForm, DATA_HISTORY_VERSION_DATA_FORM",
      "DataHistoryVersionDifferencesForm, DATA_HISTORY_VERSION_DIFFERENCES_FORM",
      "CollaborationSystemUsersChoiceForm, COLLABORATION_SYSTEM_USERS_CHOICE_FORM",
      "AuxiliaryDataHistoryChangeHistoryForm, AUX_DATA_HISTORY_CHANGE_HISTORY_FORM",
      "AuxiliaryDataHistoryVersionDataForm, AUX_DATA_HISTORY_VERSION_DATA_FORM",
      "AuxiliaryDataHistoryVersionDifferencesForm, AUX_DATA_HISTORY_VERSION_DIFFERENCES_FORM",
      "AuxiliaryCollaborationSystemUsersChoiceForm, AUX_COLLABORATION_SYSTEM_USERS_CHOICE_FORM"
    }
  )
  void testNewKinds(ArgumentsAccessor argumentsAccessor) {
    var nameEn = argumentsAccessor.getString(0);
    var kind = DefaultFormKind.valueOf(argumentsAccessor.getString(1));
    assertThat(DefaultFormKind.valueByName(nameEn)).isEqualTo(kind);
  }

  private static CF getConfiguration(ArgumentsAccessor argumentsAccessor) {
    var formatEDT = argumentsAccessor.getBoolean(0);
    var pack = argumentsAccessor.getString(1);
    return (CF) Fixtures.get(pack, "Configuration", formatEDT);
  }

  private static void assertLink(CF cf, DefaultFormKind kind, String mdoRef) {
    var link = MdoReference.create(mdoRef);
    assertThat(cf.getDefaultFormLink(kind)).isEqualTo(link);
    assertThat(cf.getDefaultFormMap()).containsEntry(kind, link);
  }
}