package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.MDOReference;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SubsystemTest extends AbstractMDOTest {
  SubsystemTest() {
    super(MDOType.SUBSYSTEM);
  }

  @Override
  @Test
  protected void testEDT() {
    var mdo = getMDObjectEDT("Subsystems/ПерваяПодсистема/ПерваяПодсистема.mdo");
    checkBaseField(mdo, Subsystem.class, "ПерваяПодсистема",
      "3d00f7d6-e3b0-49cf-8093-e2e4f6ea2293");
    checkNoChildren(mdo);
    checkNoModules(mdo);

    var children = ((Subsystem) mdo).getChildren();
    assertThat(children).hasSize(2);
    assertThat(children)
      .allMatch(Either::isRight)
      .extracting(Either::get)
      .anyMatch(child -> child.getName().equals("ПодчиненнаяПодсистема"))
      .anyMatch(child -> child.getName().equals("ПочиненнаяСистема2"));

    var subsystem = (Subsystem) children.stream().map(Either::get)
      .filter(child -> child.getName().equals("ПодчиненнаяПодсистема"))
      .findFirst().get();
    assertThat(subsystem.getChildren()).hasSize(2)
      .allMatch(Either::isLeft);
    assertThat(subsystem.getMdoReference())
      .isNotNull()
      .extracting(MDOReference::getType)
      .isEqualTo(MDOType.SUBSYSTEM);
    assertThat(subsystem.getMdoReference().getMdoRef())
      .isEqualTo("Subsystem.ПерваяПодсистема.Subsystem.ПодчиненнаяПодсистема");

    subsystem = (Subsystem) children.stream().map(Either::get)
      .filter(child -> child.getName().equals("ПочиненнаяСистема2"))
      .findFirst().get();
    assertThat(subsystem.getChildren()).hasSize(3);
    assertThat(subsystem.getChildren()).filteredOn(Either::isLeft).hasSize(2);
    assertThat(subsystem.getChildren()).filteredOn(Either::isRight).hasSize(1);
    assertThat(subsystem.getChildren()).filteredOn(Either::isRight)
      .extracting(Either::get)
      .anyMatch(child -> child.getName().equals("ПодчиненнаяПодсистема3Уровня"))
      .anyMatch(child -> child instanceof Subsystem);
    assertThat(subsystem.getMdoReference())
      .isNotNull()
      .extracting(MDOReference::getType)
      .isEqualTo(MDOType.SUBSYSTEM);
    assertThat(subsystem.getMdoReference().getMdoRef())
      .isEqualTo("Subsystem.ПерваяПодсистема.Subsystem.ПочиненнаяСистема2");

    var childSubsystem = (Subsystem) subsystem.getChildren().stream()
      .filter(Either::isRight)
      .map(Either::get)
      .filter(child -> child.getName().equals("ПодчиненнаяПодсистема3Уровня"))
      .findFirst().get();
    assertThat(childSubsystem.getChildren()).hasSize(3);
    assertThat(childSubsystem.getChildren()).filteredOn(Either::isLeft).hasSize(3);
  }

  @Override
  @Test
  protected void testDesigner() {

    var mdo = getMDObjectDesigner("Subsystems/ПерваяПодсистема.xml");
    checkBaseField(mdo, Subsystem.class, "ПерваяПодсистема",
      "3d00f7d6-e3b0-49cf-8093-e2e4f6ea2293");
    checkNoChildren(mdo);
    checkNoModules(mdo);

    var children = ((Subsystem) mdo).getChildren();
    assertThat(children).hasSize(4);
    assertThat(children).filteredOn(Either::isLeft).hasSize(2);
    assertThat(children).filteredOn(Either::isRight).hasSize(2);
    assertThat(children)
      .filteredOn(Either::isRight)
      .extracting(Either::get)
      .anyMatch(child -> child.getName().equals("ПодчиненнаяПодсистема"))
      .anyMatch(child -> child.getName().equals("ПочиненнаяСистема2"));

    var subsystem = (Subsystem) children.stream().filter(Either::isRight).map(Either::get)
      .filter(child -> child.getName().equals("ПодчиненнаяПодсистема"))
      .findFirst().get();
    assertThat(subsystem.getChildren()).hasSize(3)
      .allMatch(Either::isLeft);
    assertThat(subsystem.getMdoReference())
      .isNotNull()
      .extracting(MDOReference::getType)
      .isEqualTo(MDOType.SUBSYSTEM);
    assertThat(subsystem.getMdoReference().getMdoRef())
      .isEqualTo("Subsystem.ПерваяПодсистема.Subsystem.ПодчиненнаяПодсистема");

    subsystem = (Subsystem) children.stream().filter(Either::isRight).map(Either::get)
      .filter(child -> child.getName().equals("ПочиненнаяСистема2"))
      .findFirst().get();
    assertThat(subsystem.getChildren()).hasSize(2);
    assertThat(subsystem.getChildren()).filteredOn(Either::isLeft).hasSize(2);
    assertThat(subsystem.getMdoReference())
      .isNotNull()
      .extracting(MDOReference::getType)
      .isEqualTo(MDOType.SUBSYSTEM);
    assertThat(subsystem.getMdoReference().getMdoRef())
      .isEqualTo("Subsystem.ПерваяПодсистема.Subsystem.ПочиненнаяСистема2");

  }

}