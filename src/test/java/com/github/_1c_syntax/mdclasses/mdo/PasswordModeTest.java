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

import com.github._1c_syntax.mdclasses.Configuration;
import com.github._1c_syntax.mdclasses.mdo.attributes.AbstractMDOAttribute;
import com.github._1c_syntax.mdclasses.mdo.attributes.TabularSection;
import com.github._1c_syntax.mdclasses.mdo.children.Form;
import com.github._1c_syntax.mdclasses.mdo.children.form.FormItem;
import com.github._1c_syntax.mdclasses.mdo.children.form.InputFieldExtInfo;
import com.github._1c_syntax.mdclasses.mdo.metadata.AttributeType;
import com.github._1c_syntax.mdclasses.mdo.support.BWAValue;
import com.github._1c_syntax.mdclasses.mdo.support.MDOType;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class PasswordModeTest {
  private static final String BASE_DIR = "src/test/resources/metadata/passwordmode";

  @Test
  void testOrigin() {
    final var src = Path.of(BASE_DIR, "original");
    var configuration = Configuration.create(src);
    performCheck(configuration);
  }

  @Test
  void testEDT() {
    final var src = Path.of(BASE_DIR, "edt");
    var configuration = Configuration.create(src);
    performCheck(configuration);
  }

  private void performCheck(Configuration configuration) {
    checkCatalog(configuration);
    checkConstant(configuration);
    checkCommonAttribute(configuration);
    checkInformationRegister(configuration);
  }

  private void checkInformationRegister(Configuration configuration) {
    var optionalInformationRegister = configuration.getChildren().stream()
      .filter(abstractMDObjectBase -> abstractMDObjectBase.getType() == MDOType.INFORMATION_REGISTER
        && abstractMDObjectBase.getName().equals("КакойТоРегистр"))
      .findAny();
    assertThat(optionalInformationRegister).isPresent();

    var register = (MDInformationRegister) optionalInformationRegister.get();

    assertThat(register.getAttributes())
      .hasSize(6)
      .anyMatch(attribute -> passedCheckAttribute(attribute,
        AttributeType.DIMENSION, "Реквизит1", false))
      .anyMatch(attribute -> passedCheckAttribute(attribute,
        AttributeType.DIMENSION, "Пароль1", true))
      .anyMatch(attribute -> passedCheckAttribute(attribute,
        AttributeType.RESOURCE, "Пароль2", true))
      .anyMatch(attribute -> passedCheckAttribute(attribute,
        AttributeType.RESOURCE, "Реквизит2", false))
      .anyMatch(attribute -> passedCheckAttribute(attribute,
        AttributeType.ATTRIBUTE, "Пароль3", true))
      .anyMatch(attribute -> passedCheckAttribute(attribute,
        AttributeType.ATTRIBUTE, "Реквизит3", false));
  }

  private boolean passedCheckAttribute(AbstractMDOAttribute attribute, AttributeType type, String name,
                                       boolean passwordMode) {
    return attribute.getAttributeType() == type
      && attribute.getName().equals(name)
      && attribute.isPasswordMode() == passwordMode;
  }

  private void checkConstant(Configuration configuration) {
    var optionalConstant = configuration.getChildren().stream()
      .filter(abstractMDObjectBase -> abstractMDObjectBase.getType() == MDOType.CONSTANT
        && abstractMDObjectBase.getName().equals("Пароль"))
      .findAny();
    assertThat(optionalConstant).isPresent();

    var constant = (MDConstant) optionalConstant.get();
    assertThat(constant.isPasswordMode()).isTrue();
  }

  private void checkCommonAttribute(Configuration configuration) {
    var optionalCommonAttribute = configuration.getChildren().stream()
      .filter(abstractMDObjectBase -> abstractMDObjectBase.getType() == MDOType.COMMON_ATTRIBUTE
        && abstractMDObjectBase.getName().equals("ОбщийРеквизит1"))
      .findAny();
    assertThat(optionalCommonAttribute).isPresent();

    var commonAttribute = (MDCommonAttribute) optionalCommonAttribute.get();
    assertThat(commonAttribute.isPasswordMode()).isTrue();
  }

  private boolean validTabularSection(AbstractMDOAttribute mdoAttribute) {
    var tabularSection = (TabularSection) mdoAttribute;
    assertThat(tabularSection.getAttributes())
      .hasSize(2)
      .anyMatch(attribute -> attribute.getName().equals("Пароль") && attribute.isPasswordMode())
      .anyMatch(attribute -> attribute.getName().equals("Реквизит1") && !attribute.isPasswordMode());
    return true;
  }

  private void checkCatalog(Configuration configuration) {
    var optionalCatalog = configuration.getChildren().stream()
      .filter(abstractMDObjectBase -> abstractMDObjectBase instanceof MDCatalog)
      .findAny();

    assertThat(optionalCatalog).isPresent();
    var catalog = (AbstractMDObjectComplex) optionalCatalog.get();

    assertThat(catalog.getAttributes())
      .hasSize(2)
      .anyMatch(attribute -> attribute.getAttributeType() == AttributeType.ATTRIBUTE && attribute.isPasswordMode())
      .anyMatch(attribute ->
        attribute.getAttributeType() == AttributeType.TABULAR_SECTION && validTabularSection(attribute));

    assertThat(catalog.getForms()).hasSizeGreaterThan(0);
    var form = catalog.getForms().get(0);
    checkForm(form);
  }

  private void checkForm(Form form) {
    assertThat(form.getData().getPlainChildren())
      .anyMatch(item -> item.getName().equals("Пароль") && isPasswordMode(item, BWAValue.TRUE))
      .anyMatch(item -> item.getName().equals("Реквизит1") && isPasswordMode(item, BWAValue.AUTO))
      .anyMatch(item -> item.getName().equals("Реквизит2") && isPasswordMode(item, BWAValue.FALSE));
  }

  private boolean isPasswordMode(FormItem item, BWAValue value) {
    return item.getExtInfo() instanceof InputFieldExtInfo
      && ((InputFieldExtInfo) item.getExtInfo()).getPasswordMode() == value;
  }

}
