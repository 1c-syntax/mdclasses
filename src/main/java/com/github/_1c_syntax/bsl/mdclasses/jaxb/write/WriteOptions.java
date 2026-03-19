/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
 * Tymko Oleg <olegtymko@yandex.ru>, Maximov Valery <maximovvalery@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package com.github._1c_syntax.bsl.mdclasses.jaxb.write;

import com.github._1c_syntax.bsl.types.MDOType;

import java.util.Set;

/**
 * Опции записи конфигурации в формате Designer.
 */
public final class WriteOptions {

  private final boolean useShortNamesInChildObjects;
  private final String version;
  private final Set<MDOType> typeFilter;

  private WriteOptions(Builder builder) {
    this.useShortNamesInChildObjects = builder.useShortNamesInChildObjects;
    this.version = builder.version;
    this.typeFilter = builder.typeFilter;
  }

  /**
   * Использовать короткие имена (имя объекта) в ChildObjects вместо полной MDO-ссылки.
   * По умолчанию true (как в выгрузке платформы).
   */
  public boolean isUseShortNamesInChildObjects() {
    return useShortNamesInChildObjects;
  }

  /**
   * Версия формата выгрузки в атрибуте version корневого MetaDataObject (например "2.20", "2.27").
   * Не путать с версией платформы (8.3). По умолчанию {@link JaxbWriteDefaults#DEFAULT_FORMAT_VERSION}.
   */
  public String getVersion() {
    return version;
  }

  /**
   * Фильтр типов метаданных для записи в папку. Если null — записываются все поддерживаемые типы.
   * Позволяет выгружать только выбранные типы (например, только Catalogs и Documents).
   */
  public Set<MDOType> getTypeFilter() {
    return typeFilter;
  }

  /** Создаёт построитель опций записи. */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Опции по умолчанию: короткие имена, версия формата выгрузки по умолчанию, без фильтра типов.
   */
  public static WriteOptions defaults() {
    return builder().build();
  }

  /** Построитель опций записи конфигурации. */
  public static final class Builder {
    private boolean useShortNamesInChildObjects = true;
    private String version = JaxbWriteDefaults.DEFAULT_FORMAT_VERSION;
    private Set<MDOType> typeFilter = null;

    public Builder useShortNamesInChildObjects(boolean useShortNamesInChildObjects) {
      this.useShortNamesInChildObjects = useShortNamesInChildObjects;
      return this;
    }

    /**
     * Версия формата выгрузки (2.20, 2.27 и т.д.). Если null — используется {@link JaxbWriteDefaults#DEFAULT_FORMAT_VERSION}.
     */
    public Builder version(String version) {
      this.version = version != null ? version : JaxbWriteDefaults.DEFAULT_FORMAT_VERSION;
      return this;
    }

    public Builder typeFilter(Set<MDOType> typeFilter) {
      this.typeFilter = typeFilter;
      return this;
    }

    /** Создаёт опции записи. */
    public WriteOptions build() {
      return new WriteOptions(this);
    }
  }
}
