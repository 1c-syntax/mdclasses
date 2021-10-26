package com.github._1c_syntax.bsl.test_utils;

import com.github._1c_syntax.bsl.mdclasses.MDClass;
import com.github._1c_syntax.bsl.mdclasses.MDClasses;
import com.github._1c_syntax.bsl.mdo.MDObject;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.URIConverter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@UtilityClass
public class MDTestUtils {
  /**
   * Для загрузки фикстуры по пути к файлу
   *
   * @param path Путь к файлу
   * @return Содержимое файла
   */
  @SneakyThrows
  public String getFixture(String path) {
    return Files.readString(Paths.get(path));
  }

  /**
   * Генерация Json представления объекта
   *
   * @param md Контейнер или объект метаданных
   * @return Сериализованное в Json представление объекта
   */
  public String createJson(Object md) {
    XStream xstream = new XStream(new JsonHierarchicalStreamDriver());
    xstream.setMode(XStream.NO_REFERENCES);
    xstream.registerConverter(new TestURIConverter());
    return xstream.toXML(md);
  }

  /**
   * Читает контейнер MD из файла
   *
   * @param path Путь к корневому файлу
   * @return Прочитанный контейнер
   */
  public MDClass getMDClass(Path path) {
    return MDClasses.createConfiguration(path);
  }

  /**
   * Читает контейнер MD по строковому пути
   *
   * @param path Строковый путь к файлу
   * @return Прочитанный контейнер
   */
  public MDClass getMDClass(String path) {
    return getMDClass(Paths.get(path));
  }

  /**
   * Возвращает ссылку на объект метаданных конфигурации (расширения), предварительно сверяя с эталоном
   *
   * @param argumentsAccessor Параметры теста
   */
  public MDObject testAndGetMDO(ArgumentsAccessor argumentsAccessor) {
    var pack = argumentsAccessor.getString(0);
    var mdoRef = argumentsAccessor.getString(1);
    var mdc = getMDClass("src/test/resources/metadata/" + pack);
    assertThat(mdc).isNotNull();
    var mdoOptional = mdc.findChild(mdoRef);
    assertThat(mdoOptional).isPresent();
    var mdo = mdoOptional.get();
    var current = createJson(mdo);
    var fixture = getFixture("src/test/resources/fixtures/" + pack + "/" + mdoRef + ".json");
    Assertions.assertThat(current, true).isEqual(fixture);
    return mdo;
  }

}
