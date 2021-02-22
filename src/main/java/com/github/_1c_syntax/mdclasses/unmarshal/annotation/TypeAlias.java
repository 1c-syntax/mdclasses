package com.github._1c_syntax.mdclasses.unmarshal.annotation;

import org.atteo.classindex.IndexAnnotated;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Связь класса и имени типа в xml\mdo файлах для XStream
 */
@IndexAnnotated
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TypeAlias {
  /**
   * Имя класса для обоих форматов
   */
  String name() default "";

  /**
   * Имя класса для формата EDT
   */
  String edtName() default "";

  /**
   * Имя класса для формата Конфигуратора
   */
  String designerName() default "";
}
