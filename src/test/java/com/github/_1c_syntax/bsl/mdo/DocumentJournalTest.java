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
package com.github._1c_syntax.bsl.mdo;

import com.github._1c_syntax.bsl.test_utils.AbstractMDObjectTest;
import com.github._1c_syntax.bsl.types.MDOType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

class DocumentJournalTest extends AbstractMDObjectTest<DocumentJournal> {
  DocumentJournalTest() {
    super(DocumentJournal.class);
  }

  @ParameterizedTest(name = "DESIGNER {index}: {0}")
  @CsvSource(
    {
      "ЖурналДокументов1,c6743657-4787-40de-9a45-2493c630f626,,,DocumentJournal,ЖурналДокументов,1,0,0,0,0,0"
    }
  )
  void testDesigner(ArgumentsAccessor argumentsAccessor) {
    var mdo = getMDObject("DocumentJournals/" + argumentsAccessor.getString(0));
    mdoTest(mdo, MDOType.DOCUMENT_JOURNAL, argumentsAccessor);
  }

  @ParameterizedTest(name = "EDT {index}: {0}")
  @CsvSource(
    {
      "ЖурналДокументов1,c6743657-4787-40de-9a45-2493c630f626,,,DocumentJournal,ЖурналДокументов,1,0,1,1,1,1"
    }
  )
  void testEdt(ArgumentsAccessor argumentsAccessor) {
    var name = argumentsAccessor.getString(0);
    var mdo = getMDObjectEDT("DocumentJournals/" + name + "/" + name);
    mdoTest(mdo, MDOType.DOCUMENT_JOURNAL, argumentsAccessor);

    checkAttributeField(mdo.getAttributes().get(0),
      "Графа", "b52d063e-5d2c-498b-a333-4957277f47e3", List.of("passwordMode"));

    checkChildField(mdo.getForms().get(0),
      "ФормаСписка", "8146070f-9d3e-4c87-a25a-dbfea7d44243");

    checkChildField(mdo.getTemplates().get(0),
      "Макет", "0f5a6fe7-3441-49cc-ad50-b4725d15d946");

    checkChildField(mdo.getCommands().get(0),
      "Команда", "d64fc3f1-6681-463f-9177-76d00c90e363");
  }
}
