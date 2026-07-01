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
package com.github._1c_syntax.bsl.mdo;

import com.github._1c_syntax.bsl.mdo.support.DefaultFormKind;
import com.github._1c_syntax.bsl.test_utils.MDTestUtils;
import com.github._1c_syntax.bsl.types.MdoReference;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class DocumentJournalTest {
  @ParameterizedTest
  @CsvSource(
    {
      "true, mdclasses, DocumentJournals.ЖурналДокументов1, _edt",
      "false, mdclasses, DocumentJournals.ЖурналДокументов1"
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.getMDWithSimpleTest(argumentsAccessor);
    assertThat(mdo).isInstanceOf(DocumentJournal.class);
    var documentJournal = (DocumentJournal) mdo;

    // FormOwner
    assertThat(documentJournal.getDefaultFormMap()).hasSize(2);

    assertThat(documentJournal.getDefaultFormLink(DefaultFormKind.DEFAULT_FORM)).isEqualTo(MdoReference.EMPTY);
    assertThat(documentJournal.getDefaultForm(DefaultFormKind.DEFAULT_FORM)).isEmpty();
    assertThat(documentJournal.getDefaultFormLink(DefaultFormKind.AUX_FORM)).isEqualTo(MdoReference.EMPTY);
    assertThat(documentJournal.getDefaultForm(DefaultFormKind.AUX_FORM)).isEmpty();

    assertThat(documentJournal.getFormByLink(MdoReference.create("DocumentJournal.Unknown.Form.Unknown"))).isEmpty();
  }

  @ParameterizedTest
  @CsvSource(
    {
      "true, ssl_3_1, DocumentJournals.Взаимодействия, _edt",
      "false, ssl_3_1, DocumentJournals.Взаимодействия"
    }
  )
  void testSSL(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.getMDWithSimpleTest(argumentsAccessor);
    assertThat(mdo).isInstanceOf(DocumentJournal.class);
    var documentJournal = (DocumentJournal) mdo;

    // FormOwner
    assertThat(documentJournal.getDefaultFormMap()).hasSize(2);

    var formLink = documentJournal.getDefaultFormLink(DefaultFormKind.DEFAULT_FORM);
    assertThat(formLink).isEqualTo(documentJournal.getDefaultFormMap().get(DefaultFormKind.DEFAULT_FORM));
    assertThat(formLink.isEmpty()).isFalse();

    var form = documentJournal.getDefaultForm(DefaultFormKind.DEFAULT_FORM);
    assertThat(form).isPresent();
    assertThat(form.get().getName()).isEqualTo("ФормаСписка");

    var formByLink = documentJournal.getFormByLink(formLink);
    assertThat(formByLink).isPresent();
    assertThat(formByLink.get().getName()).isEqualTo("ФормаСписка");

    assertThat(documentJournal.getDefaultFormLink(DefaultFormKind.AUX_FORM)).isEqualTo(MdoReference.EMPTY);
    assertThat(documentJournal.getDefaultForm(DefaultFormKind.AUX_FORM)).isEmpty();

    assertThat(documentJournal.getFormByLink(MdoReference.create("DocumentJournal.Unknown.Form.Unknown"))).isEmpty();
  }

  @ParameterizedTest
  @CsvSource(
    {
      "true, ssl_3_2, DocumentJournals.Взаимодействия, _edt",
      "false, ssl_3_2, DocumentJournals.Взаимодействия"
    }
  )
  void testSSL32(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.getMDWithSimpleTest(argumentsAccessor);
    assertThat(mdo).isInstanceOf(DocumentJournal.class);
    var documentJournal = (DocumentJournal) mdo;

    // FormOwner
    assertThat(documentJournal.getDefaultFormMap()).hasSize(2);

    var formLink = documentJournal.getDefaultFormLink(DefaultFormKind.DEFAULT_FORM);
    assertThat(formLink).isEqualTo(documentJournal.getDefaultFormMap().get(DefaultFormKind.DEFAULT_FORM));
    assertThat(formLink.isEmpty()).isFalse();

    var form = documentJournal.getDefaultForm(DefaultFormKind.DEFAULT_FORM);
    assertThat(form).isPresent();
    assertThat(form.get().getName()).isEqualTo("ФормаСписка");

    var formByLink = documentJournal.getFormByLink(formLink);
    assertThat(formByLink).isPresent();
    assertThat(formByLink.get().getName()).isEqualTo("ФормаСписка");

    assertThat(documentJournal.getDefaultFormLink(DefaultFormKind.AUX_FORM)).isEqualTo(MdoReference.EMPTY);
    assertThat(documentJournal.getDefaultForm(DefaultFormKind.AUX_FORM)).isEmpty();

    assertThat(documentJournal.getFormByLink(MdoReference.create("DocumentJournal.Unknown.Form.Unknown"))).isEmpty();
  }

}