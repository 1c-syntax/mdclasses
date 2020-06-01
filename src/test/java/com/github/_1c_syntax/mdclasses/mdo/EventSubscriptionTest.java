package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EventSubscriptionTest extends AbstractMDOTest {
  EventSubscriptionTest() {
    super(MDOType.EVENT_SUBSCRIPTION);
  }

  @Override
  @Test
  protected void testEDT() {
    var mdo = getMDObjectEDT("EventSubscriptions/ПодпискаНаСобытие1/ПодпискаНаСобытие1.mdo");
    checkBaseField(mdo, EventSubscription.class, "ПодпискаНаСобытие1",
      "4da21a7b-3d07-4e6d-b91f-7e1c8ddcffcd");
    checkNoChildren(mdo);
    checkNoModules(mdo);
    assertThat(((EventSubscription) mdo).getHandler())
      .isEqualTo("CommonModule.ПростойОбщийМодуль.ПодпискаНаСобытие1ПередЗаписью");
  }

  @Override
  @Test
  protected void testDesigner() {
    var mdo = getMDObjectEDT("EventSubscriptions/ПодпискаНаСобытие1/ПодпискаНаСобытие1.mdo");
    checkBaseField(mdo, EventSubscription.class, "ПодпискаНаСобытие1",
      "4da21a7b-3d07-4e6d-b91f-7e1c8ddcffcd");
    checkNoChildren(mdo);
    checkNoModules(mdo);

    assertThat(((EventSubscription) mdo).getHandler())
      .isEqualTo("CommonModule.ПростойОбщийМодуль.ПодпискаНаСобытие1ПередЗаписью");
  }
}