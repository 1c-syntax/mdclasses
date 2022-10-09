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

import com.github._1c_syntax.bsl.reader.MDOReader;
import com.github._1c_syntax.bsl.reader.designer.DesignerPaths;
import com.github._1c_syntax.bsl.reader.designer.wrapper.DesignerChildObjects;
import com.github._1c_syntax.bsl.reader.designer.wrapper.DesignerMDO;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.mdclasses.mdo.attributes.AbstractMDOAttribute;
import com.github._1c_syntax.mdclasses.mdo.attributes.AccountingFlag;
import com.github._1c_syntax.mdclasses.mdo.attributes.AddressingAttribute;
import com.github._1c_syntax.mdclasses.mdo.attributes.Attribute;
import com.github._1c_syntax.mdclasses.mdo.attributes.Column;
import com.github._1c_syntax.mdclasses.mdo.attributes.Dimension;
import com.github._1c_syntax.mdclasses.mdo.attributes.ExtDimensionAccountingFlag;
import com.github._1c_syntax.mdclasses.mdo.attributes.Recalculation;
import com.github._1c_syntax.mdclasses.mdo.attributes.Resource;
import com.github._1c_syntax.mdclasses.mdo.attributes.TabularSection;
import com.github._1c_syntax.mdclasses.mdo.children.Command;
import com.github._1c_syntax.mdclasses.mdo.children.Form;
import com.github._1c_syntax.mdclasses.mdo.children.Template;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.ToString;
import org.apache.commons.io.FilenameUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Базовый класс для сложных объектов 1с, т.е. имеющих дочерние объекты
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
public abstract class AbstractMDObjectComplex extends AbstractMDObjectBSL implements MDOHasChildren {

  /**
   * Подчиненные формы
   */
  @XStreamImplicit
  private List<Form> forms = Collections.emptyList();

  /**
   * Подчиненные макеты
   */
  @XStreamImplicit
  private List<Template> templates = Collections.emptyList();

  /**
   * Подчиненные команды
   */
  @XStreamImplicit
  private List<Command> commands = Collections.emptyList();

  /**
   * Закэшированные данные о дочерних элементах
   */
  private Set<AbstractMDObjectBase> children;

  /**
   * Реквизиты, табличные части и их реквизиты объекта
   */
  @XStreamImplicit
  private List<AbstractMDOAttribute> attributes = Collections.emptyList();

  protected AbstractMDObjectComplex(DesignerMDO designerMDO) {
    super(designerMDO);

    // для конфигуратора необходимо прочитать дочерние из каталога рядом
    setPath(designerMDO.getMdoPath());
    computeForms(designerMDO.getChildObjects().getForms());
    computeTemplates(designerMDO.getChildObjects().getTemplates());
    computeRecalculations(designerMDO.getChildObjects().getRecalculations());

    // эти данные лежат сразу в файле описания,
    computeCommands(designerMDO.getChildObjects().getCommands());
    computeChildren(designerMDO.getChildObjects());
  }

  @Override
  public Set<AbstractMDObjectBase> getChildren() {
    if (children == null) {
      children = new HashSet<>();

      children.addAll(forms);
      children.addAll(commands);
      children.addAll(templates);
      children.addAll(attributes);
      attributes.stream().filter(MDOHasChildren.class::isInstance)
        .map(MDOHasChildren.class::cast)
        .forEach(mdo -> children.addAll(mdo.getChildren()));
    }
    return Collections.unmodifiableSet(children);
  }

  private void computeForms(List<String> formNames) {
    var childrenFolder = DesignerPaths.childrenFolder(path, MDOType.FORM);
    setForms(readDesignerMDOChildren(childrenFolder, Form.class, formNames));
  }

  private void computeTemplates(List<String> templateNames) {
    var childrenFolder = DesignerPaths.childrenFolder(path, MDOType.TEMPLATE);
    setTemplates(readDesignerMDOChildren(childrenFolder, Template.class, templateNames));
  }

  private void computeCommands(List<DesignerMDO> commandsDesigner) {
    List<Command> computedCommands = new ArrayList<>();
    commandsDesigner.forEach((DesignerMDO designerMDO) -> computedCommands.add(new Command(designerMDO)));
    setCommands(computedCommands);
  }

  private void computeRecalculations(List<String> recalculationNames) {
    var childrenFolder = DesignerPaths.childrenFolder(path, MDOType.RECALCULATION);
    var recalculations = readDesignerMDOChildren(childrenFolder, Recalculation.class, recalculationNames);
    setAttributes(recalculations.stream().map(AbstractMDOAttribute.class::cast).collect(Collectors.toList()));
  }

  private void computeChildren(DesignerChildObjects childObjects) {
    List<AbstractMDOAttribute> computedAttributes = new ArrayList<>(getAttributes());

    childObjects.getAccountingFlags().forEach((DesignerMDO designerMDO)
      -> computedAttributes.add(new AccountingFlag(designerMDO)));

    childObjects.getAddressingAttributes().forEach((DesignerMDO designerMDO)
      -> computedAttributes.add(new AddressingAttribute(designerMDO)));

    childObjects.getAttributes().forEach((DesignerMDO designerMDO)
      -> computedAttributes.add(new Attribute(designerMDO)));

    childObjects.getColumns().forEach((DesignerMDO designerMDO)
      -> computedAttributes.add(new Column(designerMDO)));

    childObjects.getDimensions().forEach((DesignerMDO designerMDO)
      -> computedAttributes.add(new Dimension(designerMDO)));

    childObjects.getExtDimensionAccountingFlags().forEach(designerMDO
      -> computedAttributes.add(new ExtDimensionAccountingFlag(designerMDO)));

    childObjects.getResources().forEach((DesignerMDO designerMDO)
      -> computedAttributes.add(new Resource(designerMDO)));

    childObjects.getTabularSections().forEach((DesignerMDO designerMDO)
      -> computedAttributes.add(new TabularSection(designerMDO)));

    setAttributes(computedAttributes);
  }

  private static <T extends AbstractMDObjectBase> List<T> readDesignerMDOChildren(Path childrenFolder,
                                                                                  Class<T> childClass,
                                                                                  List<String> childNames) {
    List<T> children = new ArrayList<>();
    if (childrenFolder.toFile().exists()) {
      getMDOFilesInFolder(childrenFolder, childNames)
        .forEach((Path mdoFile) -> {
          var child = MDOReader.readMDObject(mdoFile);
          if (child != null) {
            children.add(childClass.cast(child));
          }
        });
    }
    return children;
  }

  @Override
  public void supplement() {
    super.supplement();

    forms.stream().filter(child -> child.getPath() == null).forEach(child -> child.setPath(path));
    forms.forEach(child -> child.supplement(this));

    templates.stream().filter(child -> child.getPath() == null).forEach(child -> child.setPath(path));
    templates.forEach(child -> child.supplement(this));

    commands.stream().filter(child -> child.getPath() == null).forEach(child -> child.setPath(path));
    commands.forEach(child -> child.supplement(this));

    attributes.forEach(child -> child.supplement(this));
  }

  @SneakyThrows
  private static List<Path> getMDOFilesInFolder(Path folder, List<String> childNames) {
    List<Path> childrenNames;
    var maxDepth = 1;
    AtomicReference<String> extension = new AtomicReference<>(DesignerPaths.EXTENSION_DOT);
    try (Stream<Path> files = Files.walk(folder, maxDepth)) {
      childrenNames = files
        .parallel()
        .filter((Path path) -> path.toString().endsWith(extension.get()))
        .filter((Path path) -> childNames.contains(FilenameUtils.getBaseName(path.toString())))
        .collect(Collectors.toList());
    }

    return childrenNames;
  }

  /**
   * Добавление реквизита объекта
   *
   * @param attribute Добавляемый реквизит
   */
  public void addAttribute(AbstractMDOAttribute attribute) {
    List<AbstractMDOAttribute> computedAttributes = new ArrayList<>(getAttributes());
    computedAttributes.add(attribute);
    setAttributes(computedAttributes);
  }
}
