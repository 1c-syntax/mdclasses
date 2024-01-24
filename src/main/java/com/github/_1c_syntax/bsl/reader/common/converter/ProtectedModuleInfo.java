/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2024
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
package com.github._1c_syntax.bsl.reader.common.converter;

import lombok.Getter;
import org.apache.commons.io.FilenameUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Описание "защищенности" модуля
 */
@Getter
public class ProtectedModuleInfo {

  private static final byte[] PROTECTED_FILE_HEADER = new byte[]{-1, -1, -1, 127};

  /**
   * Признак защищенности модуля
   */
  private boolean isProtected;

  /**
   * Путь к модулю
   */
  private Path modulePath;

  public ProtectedModuleInfo(Path path, boolean onlyFindBin) {
    modulePath = path;
    isProtected = false;

    if (!modulePath.toFile().exists()) {
      var prtModulePath = Paths.get(FilenameUtils.removeExtension(modulePath.toFile().getPath()) + ".bin");
      if (prtModulePath.toFile().exists()) {
        isProtected = true;
        modulePath = prtModulePath;
      }
    } else if (!onlyFindBin) { // нет смысла читать файлы, если ищем только bin
      var bytes = new byte[PROTECTED_FILE_HEADER.length];
      try (var fis = new FileInputStream(modulePath.toFile())) {
        isProtected = (fis.read(bytes) == PROTECTED_FILE_HEADER.length
          && Arrays.equals(bytes, PROTECTED_FILE_HEADER));
      } catch (IOException e) {
        // ошибка чтения в данном случае неважна
      }
    }
  }
}
