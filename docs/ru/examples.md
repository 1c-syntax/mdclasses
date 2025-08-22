# Примеры использования MDClasses

В этом документе представлены примеры использования библиотеки MDClasses для работы с метаданными 1С.

## Оглавление

- [Базовые операции](#базовые-операции)
- [Работа с конфигурацией](#работа-с-конфигурацией)
- [Работа с метаданными](#работа-с-метаданными)
- [Работа с формами](#работа-с-формами)
- [Работа с модулями](#работа-с-модулями)
- [Поиск и фильтрация объектов](#поиск-и-фильтрация-объектов)
- [Практические сценарии](#практические-сценарии)

## Базовые операции

### Инициализация парсера и чтение конфигурации

```java
// Сначала стандартные библиотеки Java
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

// Затем библиотеки сторонних разработчиков
import com.github._1c_syntax.bsl.mdclasses.MDClasses;
import com.github._1c_syntax.bsl.mdo.AccumulationRegister;
import com.github._1c_syntax.bsl.mdo.Catalog;
import com.github._1c_syntax.bsl.mdo.Configuration;
import com.github._1c_syntax.bsl.mdo.Document;
import com.github._1c_syntax.bsl.mdo.Form;
import com.github._1c_syntax.bsl.mdo.FormItem;
import com.github._1c_syntax.bsl.mdo.InformationRegister;
import com.github._1c_syntax.bsl.mdo.MD;
import com.github._1c_syntax.bsl.mdo.MdoReference;
import com.github._1c_syntax.bsl.mdo.Module;
import com.github._1c_syntax.bsl.mdo.ModuleType;
import com.github._1c_syntax.bsl.mdo.Right;
import com.github._1c_syntax.bsl.mdo.Role;
import com.github._1c_syntax.bsl.mdo.Subsystem;
// ... и другие объекты метаданных

// Путь к каталогу конфигурации
Path configurationPath = Paths.get("path/to/configuration");

// Чтение конфигурации
Configuration configuration = MDClasses.createConfiguration(configurationPath);
```

### Получение основной информации о конфигурации

```java
// Название конфигурации
String name = configuration.getName();
System.out.println("Имя конфигурации: " + name);

// Синоним конфигурации
String synonym = configuration.getSynonym();
System.out.println("Синоним конфигурации: " + synonym);

// Версия конфигурации
String version = configuration.getVersion();
System.out.println("Версия: " + version);

// Поставщик
String vendor = configuration.getVendor();
System.out.println("Поставщик: " + vendor);
```

## Работа с конфигурацией

### Обход подсистем

```java
// Получение всех подсистем верхнего уровня
configuration.getSubsystems().forEach(subsystem -> {
    System.out.println("Подсистема: " + subsystem.getName());
    
    // Получение дочерних подсистем
    subsystem.getSubsystems().forEach(childSubsystem -> {
        System.out.println("  - Дочерняя подсистема: " + childSubsystem.getName());
    });
});
```

### Получение списка всех объектов конфигурации

```java
// Получение всех объектов конфигурации
List<MD> allObjects = configuration.getAllMdo();

// Для работы со справочниками
List<Catalog> catalogs = configuration.getCatalogs();

// Для работы с документами
List<Document> documents = configuration.getDocuments();

// Вывод количества объектов по типам
Map<String, Long> objectCountByType = allObjects.stream()
    .collect(Collectors.groupingBy(
        obj -> obj.getClass().getSimpleName(),
        Collectors.counting()
    ));

objectCountByType.forEach((type, count) -> {
    System.out.println(type + ": " + count);
});
```

## Работа с метаданными

### Работа со справочниками

```java
// Получение всех справочников
List<Catalog> catalogs = configuration.getCatalogs();

catalogs.forEach(catalog -> {
    System.out.println("Справочник: " + catalog.getName());
    
    // Получение реквизитов справочника
    catalog.getAttributes().forEach(attribute -> {
        System.out.println("  Реквизит: " + attribute.getName() + 
                          ", Тип: " + attribute.getType().getDescription());
    });
    
    // Получение табличных частей
    catalog.getTabularSections().forEach(section -> {
        System.out.println("  Табличная часть: " + section.getName());
        
        // Получение реквизитов табличной части
        section.getAttributes().forEach(attribute -> {
            System.out.println("    Реквизит ТЧ: " + attribute.getName() + 
                              ", Тип: " + attribute.getType().getDescription());
        });
    });
});
```

### Работа с документами

```java
// Получение всех документов
List<Document> documents = configuration.getDocuments();

documents.forEach(document -> {
    System.out.println("Документ: " + document.getName());
    
    // Получение реквизитов документа
    document.getAttributes().forEach(attribute -> {
        System.out.println("  Реквизит: " + attribute.getName());
    });
    
    // Получение табличных частей
    document.getTabularSections().forEach(section -> {
        System.out.println("  Табличная часть: " + section.getName());
    });
    
    // Получение регистраторов движений
    document.getRegisterRecords().forEach(registerRecord -> {
        System.out.println("  Регистратор: " + registerRecord.getName());
    });
});
```

### Работа с регистрами

```java
// Работа с регистрами накопления
configuration.getAccumulationRegisters().forEach(register -> {
    System.out.println("Регистр накопления: " + register.getName());
    System.out.println("  Вид регистра: " + register.getRegisterType());
    
    // Получение измерений регистра
    register.getDimensions().forEach(dimension -> {
        System.out.println("  Измерение: " + dimension.getName());
    });
    
    // Получение ресурсов регистра
    register.getResources().forEach(resource -> {
        System.out.println("  Ресурс: " + resource.getName());
    });
});

// Работа с регистрами сведений
configuration.getInformationRegisters().forEach(register -> {
    System.out.println("Регистр сведений: " + register.getName());
    System.out.println("  Периодичность: " + register.getPeriodicity());
    
    // Получение измерений регистра
    register.getDimensions().forEach(dimension -> {
        System.out.println("  Измерение: " + dimension.getName());
    });
});
```

## Работа с формами

### Получение и анализ форм

```java
// Получение всех форм
List<Form> allForms = configuration.getAllForms();
System.out.println("Всего форм в конфигурации: " + allForms.size());

// Анализ конкретной формы
allForms.stream()
    .filter(form -> "ФормаДокумента".equals(form.getName()))
    .findFirst()
    .ifPresent(form -> {
        System.out.println("Анализ формы: " + form.getName());
        
        if (form.getFormData() != null) {
            // Получение элементов формы
            FormItem rootItem = form.getFormData().getItems();
            analyzeFormItem(rootItem, 0);
        }
    });

// Рекурсивный метод для анализа элементов формы
private static void analyzeFormItem(FormItem item, int level) {
    if (item == null) return;
    
    String indent = "  ".repeat(level);
    System.out.println(indent + "Элемент: " + item.getName() + ", Тип: " + item.getType());
    
    // Обработка дочерних элементов
    if (item.getChildItems() != null) {
        item.getChildItems().forEach(child -> analyzeFormItem(child, level + 1));
    }
}
```

## Работа с модулями

### Получение и анализ модулей

```java
// Получение всех модулей
List<Module> allModules = configuration.getAllModules();
System.out.println("Всего модулей в конфигурации: " + allModules.size());

// Группировка модулей по типу
Map<ModuleType, List<Module>> modulesByType = allModules.stream()
    .collect(Collectors.groupingBy(Module::getModuleType));

// Вывод количества модулей каждого типа
modulesByType.forEach((type, modules) -> {
    System.out.println("Модули типа " + type.getName() + ": " + modules.size());
});

// Анализ содержимого конкретного модуля
allModules.stream()
    .filter(module -> module.getModuleType() == ModuleType.ObjectModule)
    .filter(module -> module.getOwner().getName().equals("ИмяОбъекта"))
    .findFirst()
    .ifPresent(module -> {
        System.out.println("Содержимое модуля:");
        String[] lines = module.getContent().split("\n");
        for (int i = 0; i < Math.min(10, lines.length); i++) {
            System.out.println((i+1) + ": " + lines[i]);
        }
    });
```

## Поиск и фильтрация объектов

### Поиск объекта по ссылке

```java
// Создание ссылки на объект метаданных
MdoReference reference = MdoReference.create("Справочник.Товары");

// Поиск объекта по ссылке
Optional<MD> mdoOptional = configuration.findChild(reference);

mdoOptional.ifPresentOrElse(
    mdo -> System.out.println("Найден объект: " + mdo.getName()),
    () -> System.out.println("Объект не найден")
);
```

### Фильтрация объектов по условию

```java
// Найти все справочники с табличными частями
List<Catalog> catalogsWithTabularSections = configuration.getCatalogs().stream()
    .filter(catalog -> !catalog.getTabularSections().isEmpty())
    .collect(Collectors.toList());

System.out.println("Справочники с табличными частями:");
catalogsWithTabularSections.forEach(catalog -> {
    System.out.println(catalog.getName() + " (" + catalog.getTabularSections().size() + " ТЧ)");
});

// Найти все объекты с определенным префиксом имени
String prefix = "ПрефиксИмени";
List<MD> objectsWithPrefix = configuration.getAllMdo().stream()
    .filter(obj -> obj.getName().startsWith(prefix))
    .collect(Collectors.toList());

System.out.println("Объекты с префиксом '" + prefix + "':");
objectsWithPrefix.forEach(obj -> {
    System.out.println(obj.getClass().getSimpleName() + ": " + obj.getName());
});
```

## Практические сценарии

### Построение диаграммы зависимостей объектов

```java
// Упрощенный пример построения графа зависимостей между объектами
Map<String, Set<String>> dependencies = new HashMap<>();

// Сбор зависимостей из типов реквизитов справочников
configuration.getCatalogs().forEach(catalog -> {
    String catalogKey = "Catalog." + catalog.getName();
    dependencies.putIfAbsent(catalogKey, new HashSet<>());
    
    catalog.getAttributes().forEach(attribute -> {
        if (attribute.getType() != null) {
            String typeDescription = attribute.getType().getDescription();
            // Проверяем, что тип ссылается на другой объект метаданных
            if (typeDescription != null && typeDescription.contains("CatalogRef.")) {
                String referencedObject = typeDescription.replace("CatalogRef.", "Catalog.");
                dependencies.get(catalogKey).add(referencedObject);
            }
        }
    });
});

// Вывод зависимостей
dependencies.forEach((object, refs) -> {
    if (!refs.isEmpty()) {
        System.out.println(object + " зависит от:");
        refs.forEach(ref -> System.out.println("  - " + ref));
    }
});
```

### Анализ прав доступа

```java
// Получение всех ролей
List<Role> roles = configuration.getRoles();

// Анализ прав доступа к объектам для каждой роли
roles.forEach(role -> {
    System.out.println("Роль: " + role.getName());
    
    if (role.getRights() != null) {
        // Группировка прав по объектам
        Map<String, List<Right>> rightsByObject = role.getRights().stream()
            .collect(Collectors.groupingBy(Right::getName));
        
        // ... existing code ...
    }
});
```

### Поиск неиспользуемых реквизитов

```java
// Упрощенный пример поиска неиспользуемых реквизитов
// (в реальном сценарии нужен более сложный анализ кода модулей)

// Сбор всех реквизитов справочников
Map<String, Set<String>> catalogAttributes = new HashMap<>();

configuration.getCatalogs().forEach(catalog -> {
    String catalogName = catalog.getName();
    Set<String> attributes = new HashSet<>();
    
    catalog.getAttributes().forEach(attr -> {
        attributes.add(attr.getName());
    });
    
    catalogAttributes.put(catalogName, attributes);
});

// Простой анализ использования в модулях
catalogAttributes.forEach((catalogName, attributes) -> {
    System.out.println("Проверка реквизитов справочника: " + catalogName);
    
    attributes.forEach(attrName -> {
        boolean found = false;
        
        // Поиск использования реквизита в модулях
        for (Module module : configuration.getAllModules()) {
            if (module.getContent().contains(attrName)) {
                found = true;
                break;
            }
        }
        
        if (!found) {
            System.out.println("  Потенциально неиспользуемый реквизит: " + attrName);
        }
    });
});
```

Эти примеры демонстрируют базовые возможности библиотеки MDClasses. В реальных сценариях вы можете комбинировать и расширять их для решения конкретных задач по анализу и обработке метаданных 1С.
