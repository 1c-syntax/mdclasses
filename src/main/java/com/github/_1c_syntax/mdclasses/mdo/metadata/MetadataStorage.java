package com.github._1c_syntax.mdclasses.mdo.metadata;

import org.atteo.classindex.ClassIndex;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Используется для хранения кэша метаинформации по MD классам
 */
public final class MetadataStorage {

  private static final Map<Class<?>, Metadata> STORAGE = computeStorage(Metadata.class);

  private static final Map<Class<?>, AttributeMetadata> ATTRIBUTE_STORAGE = computeStorage(AttributeMetadata.class);

  private MetadataStorage() {
    // noop
  }

  public static Map<Class<?>, Metadata> getStorage() {
    return STORAGE;
  }

  public static Map<Class<?>, AttributeMetadata> getAttributeStorage() {
    return ATTRIBUTE_STORAGE;
  }

  /**
   * Используется для получения метаинформации по MD классу из кэша
   *
   * @param clazz MD класс
   * @return Метаданные класса
   */
  public static Metadata get(Class<?> clazz) {
    return STORAGE.get(clazz);
  }

  /**
   * Используется для получения метаинформации по MD классу из кэша
   *
   * @param clazz MD класс
   * @return Метаданные класса
   */
  public static AttributeMetadata getA(Class<?> clazz) {
    return ATTRIBUTE_STORAGE.get(clazz);
  }

  private static <T extends Annotation> Map<Class<?>, T> computeStorage(Class<T> annotation) {
    Map<Class<?>, T> localStorage = new HashMap<>();
    ClassIndex.getAnnotated(annotation).forEach(
      clazz -> localStorage.put(clazz, clazz.getAnnotation(annotation)));
    return Collections.unmodifiableMap(localStorage);
  }
}
