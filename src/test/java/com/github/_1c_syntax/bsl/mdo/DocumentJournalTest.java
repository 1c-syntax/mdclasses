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

import com.github._1c_syntax.bsl.test_utils.Fixtures;
import com.github._1c_syntax.bsl.test_utils.assertions.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class DocumentJournalTest {
  @ParameterizedTest
  @CsvSource({
    "true, mdclasses, DocumentJournals.ЖурналДокументов1",
    "false, mdclasses, DocumentJournals.ЖурналДокументов1",
    "true, ssl_3_1, DocumentJournals.Взаимодействия",
    "false, ssl_3_1, DocumentJournals.Взаимодействия",
    "true, ssl_3_2, DocumentJournals.Взаимодействия",
    "false, ssl_3_2, DocumentJournals.Взаимодействия"
  })
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = Fixtures.get(argumentsAccessor);
    assertThat(mdo).isInstanceOf(DocumentJournal.class);

    var documentJournal = (DocumentJournal) mdo;
    assertThat(documentJournal).isNotNull();

    var columns = documentJournal.getColumns();
    var modules = documentJournal.getModules();
    var moduleTypes = documentJournal.getModuleTypes();
    var forms = documentJournal.getForms();
    var templates = documentJournal.getTemplates();
    var commands = documentJournal.getCommands();

    // --- ModuleOwner ---
    assertThat(moduleTypes).hasSize(modules.size());
    Assertions.assertThat(documentJournal.getAllModules(), false)
      .containsAll(modules, forms, commands);

    // --- ChildrenOwner ---
    Assertions.assertThat(documentJournal.getChildren(), true)
      .containsAll(columns, forms, templates, commands);
    Assertions.assertThat(documentJournal.getPlainChildren(), true)
      .containsAllPlain(columns, forms, templates, commands);

    // --- AttributeOwner ---
    Assertions.assertThat(documentJournal.getAllAttributes(), false)
      .containsAll(columns);
    Assertions.assertThat(documentJournal.getStorageFields(), false)
      .containsAll(columns);
    Assertions.assertThat(documentJournal.getPlainStorageFields(), false)
      .containsAll(columns);
  }
}
