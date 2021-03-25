package com.github._1c_syntax.mdclasses.mdo.metadata;

import com.github._1c_syntax.mdclasses.mdo.support.MDOType;
import org.atteo.classindex.IndexAnnotated;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Содержит набор метаинформации о типе метаданных
 */
@IndexAnnotated
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Metadata {

  /**
   * Область использования класса при чтении файлов
   */
  XMLScope xmlScope() default XMLScope.ALL;

  /**
   * Тип метаданных
   */
  MDOType type();

  /**
   * Имя типа на английском
   */
  String name();

  /**
   * Имя типа на русском
   */
  String nameRu();

  /**
   * Имя группы объектов типа на английском
   */
  String groupName();

  /**
   * Имя группы объектов типа на русском
   */
  String groupNameRu();
}
