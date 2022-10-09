/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2022
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
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MDOSynonymTest extends AbstractMDOTest {

  public MDOSynonymTest() {
    super(MDOType.ACCOUNTING_REGISTER);
  }

  @Override
  @Test
  void testEDT() {
    var mdo = getMDObjectEDT("AccountingRegisters.РегистрБухгалтерии1");
    assertThat(mdo).isInstanceOf(AbstractMDObjectBase.class);
    performTwoLanguagesCheck((AbstractMDObjectBase) mdo);

    var secondMdo = getMDObjectEDT("AccountingRegisters.РегистрБухгалтерии2");
    assertThat(secondMdo).isInstanceOf(AbstractMDObjectBase.class);
    performOneLanguageCheck((AbstractMDObjectBase) secondMdo);

    var thirdMdo = getMDObjectEDT("AccountingRegisters.РегистрБухгалтерии3");
    assertThat(thirdMdo).isInstanceOf(AbstractMDObjectBase.class);
    performEmptyLanguageCheck((AbstractMDObjectBase) thirdMdo);
  }

  @Override
  @Test
  void testDesigner() {
    var mdo = getMDObjectDesigner("AccountingRegisters.РегистрБухгалтерии1");
    assertThat(mdo).isInstanceOf(AbstractMDObjectBase.class);
    performTwoLanguagesCheck((AbstractMDObjectBase) mdo);

    var secondMdo = getMDObjectDesigner("AccountingRegisters.РегистрБухгалтерии2");
    assertThat(secondMdo).isInstanceOf(AbstractMDObjectBase.class);
    performOneLanguageCheck((AbstractMDObjectBase) secondMdo);

    var thirdMdo = getMDObjectDesigner("AccountingRegisters.РегистрБухгалтерии3");
    assertThat(thirdMdo).isInstanceOf(AbstractMDObjectBase.class);
    performEmptyLanguageCheck((AbstractMDObjectBase) thirdMdo);
  }

  private void performEmptyLanguageCheck(AbstractMDObjectBase mdo) {
    assertThat(mdo.getSynonyms()).isEmpty();
  }

  private void performOneLanguageCheck(AbstractMDObjectBase mdo) {
    assertThat(mdo.getSynonyms())
      .hasSize(1);
    assertThat(mdo.getSynonyms().get(0).getLanguage()).isEqualTo("ru");
    assertThat(mdo.getSynonyms().get(0).getContent()).isEqualTo("Регистр бухгалтерии");
  }

  private void performTwoLanguagesCheck(AbstractMDObjectBase mdo) {
    assertThat(mdo.getSynonyms())
      .hasSize(2);
    assertThat(mdo.getSynonyms().get(0).getLanguage()).isEqualTo("ru");
    assertThat(mdo.getSynonyms().get(0).getContent()).isEqualTo("Регистр бухгалтерии");
    assertThat(mdo.getSynonyms().get(1).getLanguage()).isEqualTo("en");
    assertThat(mdo.getSynonyms().get(1).getContent()).isEqualTo("Accounting register");
  }
}
