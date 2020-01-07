package com.github._1c_syntax.mdclasses.metadata;

import com.github._1c_syntax.mdclasses.metadata.additional.SupportVariant;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class SupportDataConfiguration {

  private HashMap<String, SupportVariant> supportMap = new HashMap<>();
  private Path pathToBinFile;

  private static final int POINT_COUNT_CONFIGURATION = 2;
  private static final int SHIFT_CONFIGURATION_VERSION = 3;
  private static final int SHIFT_CONFIGURATION_PRODUCER = 4;
  private static final int SHIFT_CONFIGURATION_NAME = 5;
  private static final int SHIFT_CONFIGURATION_COUNT_OBJECT = 6;
  private static final int SHIFT_OBJECT_COUNT = 7;
  private static final int COUNT_ELEMENT_OBJECT = 4;

  public SupportDataConfiguration(Path pathToBinFile) {
    this.pathToBinFile = pathToBinFile;
    LOGGER.debug("Чтения файла ParentConfigurations.bin");
    load();
  }

  private void load() {

    String data = readBinFile(pathToBinFile);
    String[] dataStrings = data.split(",");
    int countConfiguration = Integer.parseInt(dataStrings[POINT_COUNT_CONFIGURATION]);
    LOGGER.debug("Найдено конфигураций: {}", countConfiguration);

    int startPoint = 3;
    for (int numberConfiguration = 1; numberConfiguration <= countConfiguration; numberConfiguration++) {
      String configurationVersion = dataStrings[startPoint + SHIFT_CONFIGURATION_VERSION];
      String configurationProducer = dataStrings[startPoint + SHIFT_CONFIGURATION_PRODUCER];
      String configurationName = dataStrings[startPoint + SHIFT_CONFIGURATION_NAME];
      int countObjectsConfiguration = Integer.parseInt(dataStrings[startPoint + SHIFT_CONFIGURATION_COUNT_OBJECT]);

      LOGGER.debug(String.format(
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

  public Map<String, SupportVariant> getSupportMap() {
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
      result = Files.readString(Paths.get(path.toUri()));
    } catch (IOException e) {
      LOGGER.error("Don't read bin file", e);
    }
    return result;

  }

}
