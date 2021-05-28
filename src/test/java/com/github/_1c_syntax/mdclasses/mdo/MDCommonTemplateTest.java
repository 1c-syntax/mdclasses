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

import com.github._1c_syntax.mdclasses.mdo.children.template.TemplateType;
import com.github._1c_syntax.mdclasses.mdo.support.MDOType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MDCommonTemplateTest extends AbstractMDOTest {
  MDCommonTemplateTest() {
    super(MDOType.COMMON_TEMPLATE);
  }

  @Override
  @Test
  void testEDT() {
    checkEdtActiveDocument();
    checkEdtSpreadSheetDocument();
    checkEdtHtmlDocument();
    checkEdtBinaryData();
    checkEdtDataCompositionAppearance();
    checkEdtDataCompositionScheme();
    checkEdtTextDocument();
    checkEdtAddin();
    checkEdtGeoScheme();
    checkEdtGraphicalScheme();
  }

  @Override
  @Test
  void testDesigner() {
    checkOriginalActiveDocument();
    checkOriginalSpreadSheetDocument();
    checkOriginalHtmlDocument();
    checkOriginalBinaryData();
    checkOriginalDataCompositionAppearance();
    checkOriginalDataCompositionScheme();
    checkOriginalTextDocument();
    checkOriginalAddin();
    checkOriginalGeoScheme();
    checkOriginalGraphicalScheme();
  }

  private void checkEdtGraphicalScheme() {
    var mdo = getMDObjectEDT("CommonTemplates/ГрафическаяСхема/ГрафическаяСхема.mdo");
    checkBaseField(mdo, MDCommonTemplate.class, "ГрафическаяСхема",
      "4333f027-4fc2-40a0-ae7d-48fbf0cea50b");
    checkNoChildren(mdo);
    checkNoModules(mdo);
    assertThat(((MDCommonTemplate) mdo).getTemplateType()).isEqualTo(TemplateType.GRAPHICAL_SCHEME);
  }

  private void checkEdtGeoScheme() {
    var mdo = getMDObjectEDT("CommonTemplates/ГеографическаяСхема/ГеографическаяСхема.mdo");
    checkBaseField(mdo, MDCommonTemplate.class, "ГеографическаяСхема",
      "1d8d8dfc-e7c5-445a-a88d-6743faad2ab6");
    checkNoChildren(mdo);
    checkNoModules(mdo);
    assertThat(((MDCommonTemplate) mdo).getTemplateType()).isEqualTo(TemplateType.GEOGRAPHICAL_SCHEMA);
  }

  private void checkEdtAddin() {
    var mdo = getMDObjectEDT("CommonTemplates/ВнешняяКомпонента/ВнешняяКомпонента.mdo");
    checkBaseField(mdo, MDCommonTemplate.class, "ВнешняяКомпонента",
      "4a0dab22-affd-4887-a9b6-57a1e88f8377");
    checkNoChildren(mdo);
    checkNoModules(mdo);
    assertThat(((MDCommonTemplate) mdo).getTemplateType()).isEqualTo(TemplateType.ADD_IN);
  }

  private void checkEdtTextDocument() {
    var mdo = getMDObjectEDT("CommonTemplates/ТекстовыйДокумент/ТекстовыйДокумент.mdo");
    checkBaseField(mdo, MDCommonTemplate.class, "ТекстовыйДокумент",
      "3084f392-8f90-4134-a82e-790e225aab29");
    checkNoChildren(mdo);
    checkNoModules(mdo);
    assertThat(((MDCommonTemplate) mdo).getTemplateType()).isEqualTo(TemplateType.TEXT_DOCUMENT);
  }

  private void checkEdtDataCompositionScheme() {
    var mdo = getMDObjectEDT("CommonTemplates/СКД/СКД.mdo");
    checkBaseField(mdo, MDCommonTemplate.class, "СКД",
      "8ae95178-7f50-4a77-aaf8-f8ffb72c65d4");
    checkNoChildren(mdo);
    checkNoModules(mdo);
    assertThat(((MDCommonTemplate) mdo).getTemplateType()).isEqualTo(TemplateType.DATA_COMPOSITION_SCHEME);
  }

  private void checkEdtDataCompositionAppearance() {
    var mdo = getMDObjectEDT("CommonTemplates/МакетОформления/МакетОформления.mdo");
    checkBaseField(mdo, MDCommonTemplate.class, "МакетОформления",
      "f6bbaf46-bc77-412b-9440-3032bfc06c57");
    checkNoChildren(mdo);
    checkNoModules(mdo);
    assertThat(((MDCommonTemplate) mdo).getTemplateType()).isEqualTo(TemplateType.DATA_COMPOSITION_APPEARANCE_TEMPLATE);
  }

  private void checkEdtBinaryData() {
    var mdo = getMDObjectEDT("CommonTemplates/ДвоичныеДанные/ДвоичныеДанные.mdo");
    checkBaseField(mdo, MDCommonTemplate.class, "ДвоичныеДанные",
      "f4ab9283-1110-4808-9cbf-40c71ebb88a2");
    checkNoChildren(mdo);
    checkNoModules(mdo);
    assertThat(((MDCommonTemplate) mdo).getTemplateType()).isEqualTo(TemplateType.BINARY_DATA);
  }

  private void checkEdtHtmlDocument() {
    var mdo = getMDObjectEDT("CommonTemplates/HTML/HTML.mdo");
    checkBaseField(mdo, MDCommonTemplate.class, "HTML",
      "5d76084a-68fa-4579-91da-17d7ffab6225");
    checkNoChildren(mdo);
    checkNoModules(mdo);
    assertThat(((MDCommonTemplate) mdo).getTemplateType()).isEqualTo(TemplateType.HTML_DOCUMENT);
  }

  private void checkEdtSpreadSheetDocument() {
    var mdo = getMDObjectEDT("CommonTemplates/ТабличныйДокумент/ТабличныйДокумент.mdo");
    checkBaseField(mdo, MDCommonTemplate.class, "ТабличныйДокумент",
      "5b54ba38-ec04-4fc6-897f-48d36d0312a6");
    checkNoChildren(mdo);
    checkNoModules(mdo);
    assertThat(((MDCommonTemplate) mdo).getTemplateType()).isEqualTo(TemplateType.SPREADSHEET_DOCUMENT);
  }

  private void checkEdtActiveDocument() {
    var mdo = getMDObjectEDT("CommonTemplates/Active/Active.mdo");
    checkBaseField(mdo, MDCommonTemplate.class, "Active",
      "557665fc-86f5-44e1-9801-490cea841718");
    checkNoChildren(mdo);
    checkNoModules(mdo);
    assertThat(((MDCommonTemplate) mdo).getTemplateType()).isEqualTo(TemplateType.ACTIVE_DOCUMENT);
  }

  private void checkOriginalGraphicalScheme() {
    var mdo = getMDObjectDesigner("CommonTemplates/ГрафическаяСхема.xml");
    checkBaseField(mdo, MDCommonTemplate.class, "ГрафическаяСхема",
      "4333f027-4fc2-40a0-ae7d-48fbf0cea50b");
    checkNoChildren(mdo);
    checkNoModules(mdo);
    assertThat(((MDCommonTemplate) mdo).getTemplateType()).isEqualTo(TemplateType.GRAPHICAL_SCHEME);
  }

  private void checkOriginalGeoScheme() {
    var mdo = getMDObjectDesigner("CommonTemplates/ГеографическаяСхема.xml");
    checkBaseField(mdo, MDCommonTemplate.class, "ГеографическаяСхема",
      "1d8d8dfc-e7c5-445a-a88d-6743faad2ab6");
    checkNoChildren(mdo);
    checkNoModules(mdo);
    assertThat(((MDCommonTemplate) mdo).getTemplateType()).isEqualTo(TemplateType.GEOGRAPHICAL_SCHEMA);
  }

  private void checkOriginalAddin() {
    var mdo = getMDObjectDesigner("CommonTemplates/ВнешняяКомпонента.xml");
    checkBaseField(mdo, MDCommonTemplate.class, "ВнешняяКомпонента",
      "4a0dab22-affd-4887-a9b6-57a1e88f8377");
    checkNoChildren(mdo);
    checkNoModules(mdo);
    assertThat(((MDCommonTemplate) mdo).getTemplateType()).isEqualTo(TemplateType.ADD_IN);
  }

  private void checkOriginalTextDocument() {
    var mdo = getMDObjectDesigner("CommonTemplates/ТекстовыйДокумент.xml");
    checkBaseField(mdo, MDCommonTemplate.class, "ТекстовыйДокумент",
      "3084f392-8f90-4134-a82e-790e225aab29");
    checkNoChildren(mdo);
    checkNoModules(mdo);
    assertThat(((MDCommonTemplate) mdo).getTemplateType()).isEqualTo(TemplateType.TEXT_DOCUMENT);
  }

  private void checkOriginalDataCompositionScheme() {
    var mdo = getMDObjectDesigner("CommonTemplates/СКД.xml");
    checkBaseField(mdo, MDCommonTemplate.class, "СКД",
      "8ae95178-7f50-4a77-aaf8-f8ffb72c65d4");
    checkNoChildren(mdo);
    checkNoModules(mdo);
    assertThat(((MDCommonTemplate) mdo).getTemplateType()).isEqualTo(TemplateType.DATA_COMPOSITION_SCHEME);
  }

  private void checkOriginalDataCompositionAppearance() {
    var mdo = getMDObjectDesigner("CommonTemplates/МакетОформления.xml");
    checkBaseField(mdo, MDCommonTemplate.class, "МакетОформления",
      "f6bbaf46-bc77-412b-9440-3032bfc06c57");
    checkNoChildren(mdo);
    checkNoModules(mdo);
    assertThat(((MDCommonTemplate) mdo).getTemplateType()).isEqualTo(TemplateType.DATA_COMPOSITION_APPEARANCE_TEMPLATE);
  }

  private void checkOriginalBinaryData() {
    var mdo = getMDObjectDesigner("CommonTemplates/ДвоичныеДанные.xml");
    checkBaseField(mdo, MDCommonTemplate.class, "ДвоичныеДанные",
      "f4ab9283-1110-4808-9cbf-40c71ebb88a2");
    checkNoChildren(mdo);
    checkNoModules(mdo);
    assertThat(((MDCommonTemplate) mdo).getTemplateType()).isEqualTo(TemplateType.BINARY_DATA);
  }

  private void checkOriginalHtmlDocument() {
    var mdo = getMDObjectDesigner("CommonTemplates/HTML.xml");
    checkBaseField(mdo, MDCommonTemplate.class, "HTML",
      "5d76084a-68fa-4579-91da-17d7ffab6225");
    checkNoChildren(mdo);
    checkNoModules(mdo);
    assertThat(((MDCommonTemplate) mdo).getTemplateType()).isEqualTo(TemplateType.HTML_DOCUMENT);
  }

  private void checkOriginalSpreadSheetDocument() {
    var mdo = getMDObjectDesigner("CommonTemplates/ТабличныйДокумент.xml");
    checkBaseField(mdo, MDCommonTemplate.class, "Макет",
      "799e0ae7-f5ea-4b50-8853-e2c58ef5d9cd");
    checkNoChildren(mdo);
    checkNoModules(mdo);
    assertThat(((MDCommonTemplate) mdo).getTemplateType()).isEqualTo(TemplateType.SPREADSHEET_DOCUMENT);
  }

  private void checkOriginalActiveDocument() {
    var mdo = getMDObjectDesigner("CommonTemplates/Active.xml");
    checkBaseField(mdo, MDCommonTemplate.class, "Active",
      "557665fc-86f5-44e1-9801-490cea841718");
    checkNoChildren(mdo);
    checkNoModules(mdo);
    assertThat(((MDCommonTemplate) mdo).getTemplateType()).isEqualTo(TemplateType.ACTIVE_DOCUMENT);
  }
}
