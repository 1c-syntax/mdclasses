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
import com.github._1c_syntax.bsl.reader.designer.wrapper.DesignerExchangePlanContent;
import com.github._1c_syntax.bsl.reader.designer.wrapper.DesignerMDO;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.mdclasses.mdo.children.ExchangePlanItem;
import com.github._1c_syntax.mdclasses.mdo.metadata.Metadata;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import io.vavr.control.Either;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Metadata(
  type = MDOType.EXCHANGE_PLAN,
  name = "ExchangePlan",
  nameRu = "ПланОбмена",
  groupName = "ExchangePlans",
  groupNameRu = "ПланыОбмена"
)
@Slf4j
public class MDExchangePlan extends AbstractMDObjectComplex {

  /**
   * Признак распределенной базы
   */
  private boolean distributedInfoBase;
  /**
   * Признак распространения в распределенной базе расширений
   */
  private boolean includeConfigurationExtensions;

  /**
   * Состав плана обмена.
   * Для формата EDT хранится в самом MDO файле, для формата конфигуратора - в отдельном
   */
  @XStreamImplicit
  private List<ExchangePlanItem> content = Collections.emptyList();

  public MDExchangePlan(DesignerMDO designerMDO) {
    super(designerMDO);
    distributedInfoBase = designerMDO.getProperties().isDistributedInfoBase();
    includeConfigurationExtensions = designerMDO.getProperties().isIncludeConfigurationExtensions();
  }

  @Override
  public void supplement() {
    super.supplement();
    var exchangeContext = readExchangeContext(path, name);
    if (!exchangeContext.isEmpty()) {
      content = exchangeContext;
    }
  }

  /**
   * Выполняет преобразование mdoRef объекта в составе на сам объект
   *
   * @param allMDO Все объекты конфигурации
   */
  public void linkToMDO(Map<String, AbstractMDObjectBase> allMDO) {
    content.forEach((ExchangePlanItem item) -> {
      if (item.getMdObject().isLeft()) {
        var mdo = allMDO.get(item.getMdObject().getLeft());
        if (mdo != null) {
          item.setMdObject(Either.right(mdo));
        }
      }
    });
  }

  private static List<ExchangePlanItem> readExchangeContext(@NonNull Path mdoPath,
                                                            @NonNull String mdoName) {
    if (mdoPath.toString().endsWith(".xml")) {
      var path = DesignerPaths.exchangePlanContentPath(mdoPath, mdoName);
      var data = MDOReader.read(path);
      if (data instanceof DesignerExchangePlanContent) {
        return ((DesignerExchangePlanContent) data).getContent();
      } else if (data == null) {
        LOGGER.warn("Missing file " + path);
        return Collections.emptyList();
      }
      throw new IllegalArgumentException("Wrong exchange plan data file " + path);
    }
    return Collections.emptyList();
  }
}
