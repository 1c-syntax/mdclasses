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
package com.github._1c_syntax.bsl.mdo.utils;

import org.apache.commons.collections4.map.AbstractMapDecorator;
import org.apache.commons.collections4.map.LRUMap;

import java.util.Collections;
import java.util.Map;

/**
 * Декоратор read-only карты, мемоизирующий результаты {@link #get(Object)} (включая
 * отрицательные — отсутствие ключа) в ограниченном по размеру LRU.
 * <p>
 * Нужен для карт с дорогим {@code get}. В частности
 * {@link org.apache.commons.collections4.map.CaseInsensitiveMap} сворачивает регистр
 * ключа-запроса на каждый вызов (посимвольный {@code toLowerCase} + аллокация строки),
 * что заметно при десятках тысяч обращений. Ключи-запросы приходят из исходного кода и
 * сильно повторяются, поэтому memo резко снижает стоимость повторных обращений, а LRU не
 * даёт кэшу расти неограниченно на «промахах» (имена, которых в карте нет).
 * <p>
 * Потокобезопасен: внутренний LRU обёрнут в {@link Collections#synchronizedMap}. Сама
 * декорируемая карта при этом не модифицируется — декоратор рассчитан на неизменяемые
 * (read-only) карты; запись/итерация делегируются как есть, кэшируется только {@code get}.
 *
 * @param <V> тип значения
 */
public final class LookupCachingMap<V> extends AbstractMapDecorator<String, V> {

  /** Маркер закэшированного отрицательного результата (ключ в карте отсутствует). */
  private static final Object MISS = new Object();

  private final Map<Object, Object> cache;

  /**
   * @param map          декорируемая read-only карта
   * @param maxCacheSize максимальный размер LRU-кэша обращений
   */
  public LookupCachingMap(Map<String, V> map, int maxCacheSize) {
    super(map);
    this.cache = Collections.synchronizedMap(new LRUMap<>(maxCacheSize));
  }

  @SuppressWarnings("unchecked")
  @Override
  public V get(Object key) {
    var cached = cache.get(key);
    if (cached != null) {
      return cached == MISS ? null : (V) cached;
    }
    var value = decorated().get(key);
    cache.put(key, value == null ? MISS : value);
    return value;
  }
}
