package com.github._1c_syntax.mdclasses.mdo.metadata;

import com.github._1c_syntax.mdclasses.mdo.MDAccountingRegister;
import com.github._1c_syntax.mdclasses.mdo.attributes.TabularSection;
import com.github._1c_syntax.mdclasses.mdo.children.WEBServiceOperation;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MetadataStorageTest {

  @Test
  void testGetStorage() {
    var meta = MetadataStorage.getStorage();
    assertThat(meta)
      .isNotEmpty()
      .hasSize(51);

    // проверка на ошибки и дубли
    assertThat(meta.values().stream().map(Metadata::type).distinct()).hasSize(meta.size());
    assertThat(meta.values().stream().map(Metadata::name).distinct()).hasSize(meta.size());
    assertThat(meta.values().stream().map(Metadata::nameRu).distinct()).hasSize(meta.size());
    assertThat(meta.values().stream().map(Metadata::groupName).distinct()).hasSize(48);
    assertThat(meta.values().stream().map(Metadata::groupNameRu).distinct()).hasSize(48);
    assertThat(meta.values().stream().map(Metadata::xmlScope).filter(XMLScope.ALL::equals)).hasSize(50);
    assertThat(meta.values().stream().map(Metadata::xmlScope).filter(XMLScope.DESIGNER::equals)).hasSize(1);
    assertThat(meta.values().stream().map(Metadata::xmlScope).filter(XMLScope.EDT::equals)).isEmpty();
  }

  @Test
  void testGetAttributeStorage() {
    var meta = MetadataStorage.getAttributeStorage();
    assertThat(meta)
      .isNotEmpty()
      .hasSize(9);

    // проверка на ошибки и дубли
    assertThat(meta.values().stream().map(AttributeMetadata::type).distinct()).hasSize(meta.size());
    assertThat(meta.values().stream().map(AttributeMetadata::name).distinct()).hasSize(meta.size());
  }

  @Test
  void testMetadataStorageGet() {
    var metadata = MetadataStorage.get(MDAccountingRegister.class);
    assertThat(metadata).isNotNull();
    assertThat(metadata.name()).isEqualTo("AccountingRegister");
  }

  @Test
  void testClassGet() {
    var mdo = new MDAccountingRegister();
    assertThat(mdo.getMetadataName()).isEqualTo("AccountingRegister");

    var childMdo = new WEBServiceOperation();
    assertThat(childMdo.getMetadataName()).isEqualTo("Operation");

    var attribute = new TabularSection();
    assertThat(attribute.getMetadataName()).isEqualTo("TabularSection");
  }

}