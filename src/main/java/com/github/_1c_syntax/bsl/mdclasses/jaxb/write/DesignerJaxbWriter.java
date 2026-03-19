/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
 * Tymko Oleg <olegtymko@yandex.ru>, Maximov Valery <maximovvalery@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package com.github._1c_syntax.bsl.mdclasses.jaxb.write;

import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.MetaDataObject;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.ObjectFactory;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Сериализация JAXB MetaDataObject в XML-файл в формате Designer.
 */
public final class DesignerJaxbWriter {

  private static final JAXBContext JAXB_CONTEXT;

  static {
    try {
      JAXB_CONTEXT = JAXBContext.newInstance(MetaDataObject.class);
    } catch (JAXBException e) {
      throw new ExceptionInInitializerError(e);
    }
  }

  private DesignerJaxbWriter() {
  }

  private static final String NS_MDCLASSES = "http://v8.1c.ru/8.3/MDClasses";
  private static final String NS_XCF_READABLE = "http://v8.1c.ru/8.3/xcf/readable";

  /**
   * InternalInfo для Configuration: 7 ContainedObject (Конфигуратор 1С требует ровно 7).
   * ClassId — константы платформы (тип внутреннего объекта), одинаковы во всех выгрузках.
   * ObjectId — уникальный идентификатор экземпляра; в каждой конфигурации свои, генерируем при записи.
   */
  private static String buildConfigurationInternalInfoBlock() {
    return """
        <InternalInfo>
            <xr:ContainedObject>
                <xr:ClassId>9cd510cd-abfc-11d4-9434-004095e12fc7</xr:ClassId>
                <xr:ObjectId>%s</xr:ObjectId>
            </xr:ContainedObject>
            <xr:ContainedObject>
                <xr:ClassId>9fcd25a0-4822-11d4-9414-008048da11f9</xr:ClassId>
                <xr:ObjectId>%s</xr:ObjectId>
            </xr:ContainedObject>
            <xr:ContainedObject>
                <xr:ClassId>e3687481-0a87-462c-a166-9f34594f9bba</xr:ClassId>
                <xr:ObjectId>%s</xr:ObjectId>
            </xr:ContainedObject>
            <xr:ContainedObject>
                <xr:ClassId>9de14907-ec23-4a07-96f0-85521cb6b53b</xr:ClassId>
                <xr:ObjectId>%s</xr:ObjectId>
            </xr:ContainedObject>
            <xr:ContainedObject>
                <xr:ClassId>51f2d5d8-ea4d-4064-8892-82951750031e</xr:ClassId>
                <xr:ObjectId>%s</xr:ObjectId>
            </xr:ContainedObject>
            <xr:ContainedObject>
                <xr:ClassId>e68182ea-4237-4383-967f-90c1e3370bc7</xr:ClassId>
                <xr:ObjectId>%s</xr:ObjectId>
            </xr:ContainedObject>
            <xr:ContainedObject>
                <xr:ClassId>fb282519-d103-4dd3-bc12-cb271d631dfc</xr:ClassId>
                <xr:ObjectId>%s</xr:ObjectId>
            </xr:ContainedObject>
        </InternalInfo>""".formatted(
        UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
        UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());
  }

  /** Плейсхолдер имени справочника (без $, чтобы не интерпретировался в replaceFirst как группа). */
  private static final String CATALOG_NAME_PLACEHOLDER = "\u0000CatalogName\u0000";

  private static final Pattern NAME_TAG = Pattern.compile("<Name>([^<]+)</Name>");

  /**
   * InternalInfo для справочника: xr:GeneratedType (Object, Ref, Selection, List, Manager),
   * как в фикстурах. Имя подставляется из плейсхолдера после вставки (из первого тега &lt;Name&gt;).
   */
  private static String buildCatalogInternalInfoBlock() {
    String n = CATALOG_NAME_PLACEHOLDER;
    UUID u1 = UUID.randomUUID();
    UUID u2 = UUID.randomUUID();
    UUID u3 = UUID.randomUUID();
    UUID u4 = UUID.randomUUID();
    UUID u5 = UUID.randomUUID();
    UUID u6 = UUID.randomUUID();
    UUID u7 = UUID.randomUUID();
    UUID u8 = UUID.randomUUID();
    UUID u9 = UUID.randomUUID();
    UUID u10 = UUID.randomUUID();
    return """
        <InternalInfo>
            <xr:GeneratedType name="CatalogObject.%s" category="Object">
                <xr:TypeId>%s</xr:TypeId>
                <xr:ValueId>%s</xr:ValueId>
            </xr:GeneratedType>
            <xr:GeneratedType name="CatalogRef.%s" category="Ref">
                <xr:TypeId>%s</xr:TypeId>
                <xr:ValueId>%s</xr:ValueId>
            </xr:GeneratedType>
            <xr:GeneratedType name="CatalogSelection.%s" category="Selection">
                <xr:TypeId>%s</xr:TypeId>
                <xr:ValueId>%s</xr:ValueId>
            </xr:GeneratedType>
            <xr:GeneratedType name="CatalogList.%s" category="List">
                <xr:TypeId>%s</xr:TypeId>
                <xr:ValueId>%s</xr:ValueId>
            </xr:GeneratedType>
            <xr:GeneratedType name="CatalogManager.%s" category="Manager">
                <xr:TypeId>%s</xr:TypeId>
                <xr:ValueId>%s</xr:ValueId>
            </xr:GeneratedType>
        </InternalInfo>""".formatted(n, u1, u2, n, u3, u4, n, u5, u6, n, u7, u8, n, u9, u10);
  }

  /**
   * Сериализует MetaDataObject в XML и записывает в файл.
   *
   * @param path   путь к .xml файлу
   * @param object корневой JAXB MetaDataObject (подсистема, справочник, конфигурация и т.д.)
   * @throws JAXBException       при ошибке маршаллинга
   * @throws java.io.IOException при ошибке записи файла
   */
  public static void write(Path path, MetaDataObject object)
    throws JAXBException, java.io.IOException {
    Marshaller m = JAXB_CONTEXT.createMarshaller();
    m.setProperty(Marshaller.JAXB_ENCODING, StandardCharsets.UTF_8.name());
    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
    m.setProperty("org.glassfish.jaxb.namespacePrefixMapper", new DesignerNamespacePrefixMapper());
    JAXBElement<MetaDataObject> root = new ObjectFactory().createMetaDataObject(object);
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    m.marshal(root, buffer);
    String xml = buffer.toString(StandardCharsets.UTF_8);
    xml = makeMDClassesDefaultNamespace(xml);
    try (OutputStream out = Files.newOutputStream(path)) {
      out.write(xml.getBytes(StandardCharsets.UTF_8));
    }
  }

  /**
   * Приводит XML к виду выгрузки платформы 1С: дефолтный неймспейс MDClasses, без ns2,
   * у Configuration только uuid (без formatVersion), первым дочерним — InternalInfo.
   */
  private static String makeMDClassesDefaultNamespace(String xml) {
    xml = xml.replace("xmlns=\"" + NS_XCF_READABLE + "\"", "xmlns:xr=\"" + NS_XCF_READABLE + "\"");
    xml = xml.replace("xmlns:ns2=\"" + NS_MDCLASSES + "\"", "xmlns=\"" + NS_MDCLASSES + "\"");
    xml = xml.replace("<ns2:", "<");
    xml = xml.replace("</ns2:", "</");
    // Как в фикстурах: у Configuration только uuid, без formatVersion
    xml = xml.replace(" formatVersion=\"2.20\"", "");
    // Configuration: InternalInfo с ContainedObject (как в фикстурах платформы)
    xml = xml.replaceFirst(
        "(<Configuration uuid=\"[^\"]+\">)\\s*\\n\\s*<Properties>",
        "$1\n        " + buildConfigurationInternalInfoBlock() + "\n        <Properties>");
    // Catalog: InternalInfo с GeneratedType (Object, Ref, Selection, List, Manager)
    xml = xml.replaceFirst(
        "(<Catalog uuid=\"[^\"]+\">)\\s*\\n\\s*<Properties>",
        "$1\n        " + buildCatalogInternalInfoBlock() + "\n        <Properties>");
    // Подставить имя справочника из первого тега <Name>
    if (xml.contains(CATALOG_NAME_PLACEHOLDER)) {
      Matcher m = NAME_TAG.matcher(xml);
      if (m.find()) {
        xml = xml.replace(CATALOG_NAME_PLACEHOLDER, m.group(1));
      }
    }
    return xml;
  }
}
