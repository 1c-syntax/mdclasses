package com.github._1c_syntax.mdclasses.mdo.metadata;

import org.atteo.classindex.IndexAnnotated;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@IndexAnnotated
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AttributeMetadata {
  /**
   * Тип метаданных
   */
  AttributeType type();

  /**
   * Имя типа на английском
   */
  String name();

  /**
   * Имя поля в формате EDT
   */
  String fieldNameEDT();
}
