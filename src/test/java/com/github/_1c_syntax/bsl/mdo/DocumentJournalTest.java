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

import com.github._1c_syntax.bsl.mdo.support.ObjectBelonging;
import com.github._1c_syntax.bsl.test_utils.AbstractMDObjectTest;
import com.github._1c_syntax.bsl.types.MDOType;
import org.junit.jupiter.api.Test;

class DocumentJournalTest extends AbstractMDObjectTest<DocumentJournal> {
  DocumentJournalTest() {
    super(DocumentJournal.class);
  }

  @Test
  void test() {
    var mdo = getMDObject("DocumentJournals/ЖурналДокументов1");
    checkBaseField(mdo, MDOType.DOCUMENT_JOURNAL,
      "ЖурналДокументов1", "c6743657-4787-40de-9a45-2493c630f626",
      ObjectBelonging.OWN);
  }

  @Test
  void test2() {
    var mdo = getMDObjectEDT("DocumentJournals/ЖурналДокументов1/ЖурналДокументов1");
    checkBaseField(mdo, MDOType.DOCUMENT_JOURNAL,
      "ЖурналДокументов1", "c6743657-4787-40de-9a45-2493c630f626",
      ObjectBelonging.OWN);
  }
}