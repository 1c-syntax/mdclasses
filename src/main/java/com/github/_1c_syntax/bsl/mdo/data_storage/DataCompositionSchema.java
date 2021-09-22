package com.github._1c_syntax.bsl.mdo.data_storage;

import com.github._1c_syntax.bsl.mdo.support.DataSetType;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

/**
 * Реализация хранения содержимого макета-схемы компоновки данных
 */
@Value
public class DataCompositionSchema implements TemplateData {

  @Override
  public boolean isEmpty() {
    return false;
  }

  /**
   * Дерево наборов данных
   */
  List<DataSet> dataSets;

  /**
   * Плоский список наборов данных
   */
  List<DataSet> plainDataSets;

  public DataCompositionSchema(@NonNull List<DataSet> dataSets) {
    this.dataSets = dataSets;
    plainDataSets = new ArrayList<>();
    fillPlaintDataSetByList(dataSets);
  }

  @Value
  @ToString(of = {"name"})
  @EqualsAndHashCode(of = {"name"})
  public static class DataSet {
    /**
     * Имя набора данных
     */
    String name;

    /**
     * Тип набора данных
     */
    DataSetType type;

    /**
     * Имя источника данных
     */
    String dataSource;

    /**
     * Подчиненные наборы данных
     */
    List<DataSet> items;

    /**
     * Текста запроса (опционально)
     */
    QuerySource querySource;

    /**
     * Поля набора данных
     */
    List<DataSetField> fields;
  }

  @Value
  @ToString(of = {"name"})
  @EqualsAndHashCode(of = {"name"})
  public static class DataSetField {
    /**
     * Путь к данным поля
     */
    String dataPath;

    /**
     * Имя поля
     */
    String name;
  }

  private void fillPlaintDataSetByList(List<DataSet> items) {
    items.forEach((DataSet dataSet) -> {
      plainDataSets.add(dataSet);
      fillPlaintDataSetByList(dataSet.getItems());
    });
  }
}
