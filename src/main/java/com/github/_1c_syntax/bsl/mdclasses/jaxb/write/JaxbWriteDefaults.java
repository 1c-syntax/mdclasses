/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
 * Tymko Oleg <olegtymko@yandex.ru>, Maximov Valery <maximovvalery@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package com.github._1c_syntax.bsl.mdclasses.jaxb.write;

import com.github._1c_syntax.bsl.mdclasses.jaxb.v8_1_data_core.LocalStringItemType;
import com.github._1c_syntax.bsl.mdclasses.jaxb.v8_1_data_core.LocalStringType;
import com.github._1c_syntax.bsl.mdclasses.jaxb.v8_2_managed_application_logform.ChoiceHistoryOnInput;
import com.github._1c_syntax.bsl.mdclasses.jaxb.v8_2_managed_application_logform.OnMainServerUnavalableBehavior;
import com.github._1c_syntax.bsl.mdclasses.jaxb.v8_3_xcf_enums.AccumulationRegisterType;
import com.github._1c_syntax.bsl.mdclasses.jaxb.v8_3_xcf_enums.CalculationRegisterPeriodicity;
import com.github._1c_syntax.bsl.mdclasses.jaxb.v8_3_xcf_enums.ChoiceMode;
import com.github._1c_syntax.bsl.mdclasses.jaxb.v8_3_xcf_enums.DefaultDataLockControlMode;
import com.github._1c_syntax.bsl.mdclasses.jaxb.v8_3_xcf_enums.FullTextSearchUsing;
import com.github._1c_syntax.bsl.mdclasses.jaxb.v8_3_xcf_enums.ObjectBelonging;
import com.github._1c_syntax.bsl.mdclasses.jaxb.v8_3_xcf_readable.CharacteristicsDescriptions;
import com.github._1c_syntax.bsl.mdclasses.jaxb.v8_3_xcf_readable.FieldList;
import com.github._1c_syntax.bsl.mdclasses.jaxb.v8_3_xcf_readable.MDListType;
import com.github._1c_syntax.bsl.mdclasses.jaxb.v8_3_xcf_readable.Picture;
import com.github._1c_syntax.bsl.mdclasses.jaxb.v8_3_xcf_readable.StandardAttributeDescriptions;

/**
 * Значения по умолчанию для JAXB-типов при записи Designer XML.
 */
public final class JaxbWriteDefaults {

  /** Версия формата выгрузки Designer XML (атрибут version в MetaDataObject). */
  public static final String DEFAULT_FORMAT_VERSION = "2.20";

  private static final String DEFAULT_LANG = "ru";

  private JaxbWriteDefaults() {
  }

  /** LocalStringType с одним элементом (язык ru). */
  public static LocalStringType localStringType(String content) {
    LocalStringType t = new LocalStringType();
    LocalStringItemType item = new LocalStringItemType();
    item.setLang(DEFAULT_LANG);
    item.setContent(content != null ? content : "");
    t.getItem().add(item);
    return t;
  }

  /** Пустой MDListType. */
  public static MDListType emptyMDListType() {
    return new MDListType();
  }

  /** Пустая картинка (все поля null/false). */
  public static Picture emptyPicture() {
    return new Picture();
  }

  /** ObjectBelonging = Native. */
  public static ObjectBelonging objectBelongingNative() {
    return ObjectBelonging.NATIVE;
  }

  /** Пустое описание стандартных реквизитов. */
  public static StandardAttributeDescriptions emptyStandardAttributeDescriptions() {
    return new StandardAttributeDescriptions();
  }

  /** Пустое описание характеристик. */
  public static CharacteristicsDescriptions emptyCharacteristicsDescriptions() {
    return new CharacteristicsDescriptions();
  }

  /** ChoiceMode = FromForm. */
  public static ChoiceMode choiceModeFromForm() {
    return ChoiceMode.FROM_FORM;
  }

  /** ChoiceHistoryOnInput = Auto. */
  public static ChoiceHistoryOnInput choiceHistoryOnInputAuto() {
    return ChoiceHistoryOnInput.AUTO;
  }

  /** DefaultDataLockControlMode = Automatic. */
  public static DefaultDataLockControlMode defaultDataLockControlModeAutomatic() {
    return DefaultDataLockControlMode.AUTOMATIC;
  }

  /** FullTextSearchUsing = DontUse. */
  public static FullTextSearchUsing fullTextSearchDontUse() {
    return FullTextSearchUsing.DONT_USE;
  }

  /** AccumulationRegisterType = Balance. */
  public static AccumulationRegisterType accumulationRegisterTypeBalance() {
    return AccumulationRegisterType.BALANCE;
  }

  /** CalculationRegisterPeriodicity = Year. */
  public static CalculationRegisterPeriodicity calculationRegisterPeriodicityYear() {
    return CalculationRegisterPeriodicity.YEAR;
  }

  /** Пустой FieldList. */
  public static FieldList emptyFieldList() {
    return new FieldList();
  }

  /** OnMainServerUnavalableBehavior = Auto (для команд). */
  public static OnMainServerUnavalableBehavior onMainServerUnavalableBehaviorAuto() {
    return OnMainServerUnavalableBehavior.AUTO;
  }
}
