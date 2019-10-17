package com.github._1c_syntax.mdclasses.metadata;

import com.github._1c_syntax.mdclasses.metadata.additional.SupportVariant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class SupportDataConfiguration {

  private HashMap<String, SupportVariant> supportMap = new HashMap<>();
  private Path pathToBinFile;

  private final int POINT_COUNT_CONFIGURATION = 2;
  private final int SHIFT_CONFIGURATION_VERSION = 3;
  private final int SHIFT_CONFIGURATION_PRODUCER = 4;
  private final int SHIFT_CONFIGURATION_NAME = 5;
  private final int SHIFT_CONFIGURATION_COUNT_OBJECT = 6;
  private final int SHIFT_OBJECT_COUNT = 7;
  private final int COUNT_ELEMENT_OBJECT = 4;

  private static final Logger LOGGER = LoggerFactory.getLogger(SupportDataConfiguration.class.getSimpleName());

  public SupportDataConfiguration(Path pathToBinFile) {
    this.pathToBinFile = pathToBinFile;
    LOGGER.info("Чтения файла ParentConfigurations.bin");
    load();
  }

  private void load() {

    String data = readBinFile(pathToBinFile);
    String[] dataStrings = data.split(",");
    int countConfiguration = Integer.parseInt(dataStrings[POINT_COUNT_CONFIGURATION]);
    LOGGER.info("Найдено конфигураций: " + countConfiguration);

    int startPoint = 3;
    for (int numberConfiguration = 1; numberConfiguration <= countConfiguration; numberConfiguration++) {
      String configurationVersion = dataStrings[startPoint + SHIFT_CONFIGURATION_VERSION];
      String configurationProducer = dataStrings[startPoint + SHIFT_CONFIGURATION_PRODUCER];
      String configurationName = dataStrings[startPoint + SHIFT_CONFIGURATION_NAME];
      int countObjectsConfiguration = Integer.parseInt(dataStrings[startPoint + SHIFT_CONFIGURATION_COUNT_OBJECT]);

      LOGGER.info(String.format(
          "Конфигурация: %s Версия: %s Поставщик: %s Количество обектов: %s",
          configurationName,
          configurationVersion,
          configurationProducer,
          countObjectsConfiguration));

      int startObjectPoint = startPoint + SHIFT_OBJECT_COUNT;
      for (int numberObject = 0; numberObject < countObjectsConfiguration - 1; numberObject++) {
        int currentObjectPoint = startObjectPoint + numberObject * COUNT_ELEMENT_OBJECT;
        // 0 - не редактируется, 1 - с сохранением поддержки, 2 - снято
        int support = Integer.parseInt(dataStrings[currentObjectPoint + 1]);
        String guidObject = dataStrings[currentObjectPoint + 2];
        SupportVariant supportVariant = getSupportVariantByInt(support);
        supportMap.put(guidObject, supportVariant);
      }
      startPoint = startObjectPoint + 2 + countObjectsConfiguration * COUNT_ELEMENT_OBJECT;
    }
  }

  public HashMap<String, SupportVariant> getSupportMap() {
    return this.supportMap;
  }

  private SupportVariant getSupportVariantByInt(int support) {
    SupportVariant supportVariant;
    if (support == 0) {
      supportVariant = SupportVariant.NOT_EDITABLE;
    } else if (support == 1) {
      supportVariant = SupportVariant.SAVED;
    } else if (support == 2) {
      supportVariant = SupportVariant.OFF;
    } else {
      supportVariant = SupportVariant.NONE;
    }
    return supportVariant;
  }

  private String readBinFile(Path path) {
    String result = "";
    try {
      result = new String(Files.readAllBytes(Paths.get(path.toUri())), StandardCharsets.UTF_8);
    } catch (IOException e) {
      LOGGER.error("Don't read bin file", e);
    }
    return result;

  }

}
