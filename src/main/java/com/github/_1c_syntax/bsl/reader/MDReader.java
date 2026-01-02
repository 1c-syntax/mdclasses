/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2025
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
package com.github._1c_syntax.bsl.reader;

import com.github._1c_syntax.bsl.mdclasses.ExternalSource;
import com.github._1c_syntax.bsl.mdclasses.MDCReadSettings;
import com.github._1c_syntax.bsl.mdclasses.MDClass;
import com.github._1c_syntax.bsl.mdo.storage.FormData;
import com.github._1c_syntax.bsl.reader.common.context.AbstractReaderContext;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendXStream;
import com.github._1c_syntax.bsl.types.ConfigurationSource;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.bsl.types.ModuleType;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import org.jspecify.annotations.Nullable;

import java.nio.file.Path;

/**
 * Интерфейс для ридеров исходников
 */
public interface MDReader {

  /**
   * Возвращает тип исходных файлов ридера
   *
   * @return Тип исходных файлов
   */
  ConfigurationSource getConfigurationSource();

  /**
   * Возвращает путь к корню читаемого контейнера
   *
   * @return Путь
   */
  Path getRootPath();

  /**
   * Возвращает установленные настройки чтения
   *
   * @return Настройки чтения
   */
  MDCReadSettings getReadSettings();

  /**
   * Выполняет чтение конфигурации
   *
   * @return Прочитанная конфигурация
   */
  MDClass readConfiguration();

  /**
   * Выполняет чтение внешнего отчета или внешней обработки
   *
   * @return Прочитанный контейнер
   */
  ExternalSource readExternalSource();

  /**
   * Выполняет чтение объекта по полному имени
   *
   * @param fullName Полное имя объекта
   * @return прочитанный объект либо null, если прочитать не удалось (например не существует)
   */
  @Nullable
  default Object read(String fullName) {
    return read(getRootPath(), fullName);
  }

  /**
   * Выполняет чтение объекта по пути к файлу
   *
   * @param path Путь к файлу
   * @return прочитанный объект либо null, если прочитать не удалось (например не существует)
   */
  @Nullable
  default Object read(Path path) {
    return getXstream().fromXML(path.toFile());
  }

  /**
   * Выполняет чтение объекта по каталогу файлов и имени объекта
   *
   * @param folder   Путь к каталогу файлов
   * @param fullName Полное имя читаемого объекта
   * @return прочитанный объект либо null, если прочитать не удалось (например не существует)
   */
  @Nullable
  Object read(Path folder, String fullName);

  /**
   * Геттер для xstream
   */
  ExtendXStream getXstream();

  /**
   * Читает данные формы
   *
   * @param currentPath Путь к объекту
   * @param name        Имя объекта
   * @param mdoType     тип объекта
   * @return Данные формы
   */
  @Nullable
  FormData readFormData(Path currentPath, String name, MDOType mdoType);

  /**
   * Рассчитывает каталог, в которм должны располагаться модули объекта
   *
   * @param mdoPath Путь к описанию объекта
   * @param mdoType Тип объекта
   * @return Путь к каталогу с модулями
   */
  Path moduleFolder(Path mdoPath, MDOType mdoType);

  /**
   * Определяет путь к файлу модуля объекта
   *
   * @param folder     Каталог модулей
   * @param name       Имя объекта
   * @param moduleType Тип модуля
   * @return Путь к файлу модуля
   */
  Path modulePath(Path folder, String name, ModuleType moduleType);

  /**
   * Определяет путь к каталогу с файлами типа объекта
   *
   * @param mdoPath Путь к файлу объекта
   * @return Путь к каталогу типа
   */
  Path mdoTypeFolderPath(Path mdoPath);

  /**
   * Возвращает имя поля для чтения подсистем
   *
   * @return Имя поля
   */
  String subsystemsNodeName();

  /**
   * Возвращает строку-фильтр для различия расширения и конфигурации
   *
   * @return Строка-фильтр
   */
  String configurationExtensionFilter();

  /**
   * Выполняет чтение объекта из файла
   *
   * @param reader        - Читатель Xstream
   * @param context       - Контекст читателя Xstream
   * @param readerContext - Контекст читателя MDClasses
   */
  void unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context, AbstractReaderContext readerContext);
}
