/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
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
package com.github._1c_syntax.bsl.reader.designer.converter;

import com.github._1c_syntax.bsl.mdclasses.Configuration;
import com.github._1c_syntax.bsl.mdclasses.MDClasses;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendXStream;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class MetaDataObjectConverterTest {

  /**
   * Корень MetaDataObject и начало конфигурации корректны, поэтому чтение доходит до конвертера,
   * а файл обрывается внутри свойств, и XStream падает уже в нем
   */
  private static final String TRUNCATED_CONFIGURATION = """
    <?xml version="1.0" encoding="UTF-8"?>
    <MetaDataObject xmlns="http://v8.1c.ru/8.3/MDClasses" xmlns:v8="http://v8.1c.ru/8.1/data/core" version="2.17">
    \t<Configuration uuid="46c7c1d0-b04d-4295-9b04-ae3207c18d29">
    \t\t<Properties>
    \t\t\t<Name>Конфигурация</Name>
    \t\t\t<Synonym>
    """;

  private final Logger readerLogger = Logger.getLogger("com.github._1c_syntax.bsl.reader");
  private final CapturingAppender appender = new CapturingAppender();

  @BeforeEach
  void attachAppender() {
    readerLogger.addAppender(appender);
  }

  @AfterEach
  void detachAppender() {
    readerLogger.removeAppender(appender);
  }

  @Test
  void brokenConfigurationIsSkippedWithWarningOnly(@TempDir Path rootPath) throws IOException {
    // given
    var configurationPath = rootPath.resolve("Configuration.xml");
    Files.writeString(configurationPath, TRUNCATED_CONFIGURATION, StandardCharsets.UTF_8);

    // when
    var configuration = MDClasses.createConfiguration(rootPath);

    // then
    assertThat(configuration).isSameAs(Configuration.EMPTY);
    assertThat(appender.events)
      .filteredOn(event -> event.getLevel().isGreaterOrEqual(Level.ERROR))
      .extracting(event -> event.getLoggerName() + ": " + event.getRenderedMessage())
      .isEmpty();
    assertThat(appender.events)
      .filteredOn(event -> event.getLevel().equals(Level.WARN))
      .filteredOn(event -> ExtendXStream.class.getName().equals(event.getLoggerName()))
      .extracting(LoggingEvent::getRenderedMessage)
      .anyMatch(message -> message.contains(configurationPath.toString()));
  }

  private static final class CapturingAppender extends AppenderSkeleton {
    private final List<LoggingEvent> events = new CopyOnWriteArrayList<>();

    @Override
    protected void append(LoggingEvent event) {
      events.add(event);
    }

    @Override
    public void close() {
      // ресурсов нет
    }

    @Override
    public boolean requiresLayout() {
      return false;
    }
  }
}
