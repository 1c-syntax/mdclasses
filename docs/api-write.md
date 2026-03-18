# API записи метаданных и контракт для BSL Language Server

В mdclasses реализован API записи объектов метаданных в файлы (EDT .mdo, при необходимости — Designer .xml). Для вызова из расширений IDE (например, vscode-1c-platform-tools) предполагается **protocol extension** в BSL Language Server: кастомные LSP-методы, которые вызывают mdclasses.

Реализация самих LSP-методов выполняется в репозитории **bsl-language-server**; здесь описан рекомендуемый контракт для согласования с клиентом.

## Рекомендуемые LSP-методы

### mdclasses/readConfiguration

Чтение конфигурации по пути к корню проекта (EDT или Designer).

**Параметры (JSON-RPC):**

- `path` (string) — путь к каталогу конфигурации (корень EDT-проекта или каталог с `Configuration.xml` для Designer).

**Ответ:**

- JSON-представление конфигурации или упрощённое дерево (имя, uuid, дочерние типы/объекты) для отображения в панели метаданных. Либо путь к считанной конфигурации и минимальный набор полей.

**Вызов на стороне BSL LS:** `MDClasses.createConfiguration(Path.of(params.path))` или `MDOReader.readConfiguration(Path.of(params.path))`.

---

### mdclasses/writeObject

Запись одного объекта метаданных в файл.

**Параметры:**

- `path` (string) — путь к файлу (`.mdo` для EDT или `.xml` для Designer).
- `object` (string) — тип объекта, например `Subsystem`, `Configuration`, `Catalog`.
- `data` (object) — данные объекта для сериализации (соответствуют полям модели mdclasses).

**Ответ:**

- `{ "success": true }` при успехе.
- Ошибка (например, `UnsupportedOperationException`, `IOException`) в формате LSP.

**Вызов на стороне BSL LS:** построить Java-объект (Subsystem, Configuration, Catalog и т.д.) из `data` и вызвать `MDClasses.writeObject(Path.of(path), object)` или `MDOWriter.writeObject(...)`.

---

### mdclasses/addObjectToConfiguration (опционально)

Добавление нового объекта в дерево конфигурации: обновление `Configuration.mdo` (EDT) или корневого файла конфигурации (Designer).

**Параметры:**

- `configurationRoot` (string) — путь к корню конфигурации.
- `objectType` (string) — тип объекта (например, `Subsystem`, `Catalog`).
- `objectName` (string) — имя нового объекта.

**Действие:** прочитать конфигурацию через MDOReader, добавить ссылку в соответствующий список (например, `<subsystems>Subsystem.Имя</subsystems>`), записать обновлённый файл конфигурации через API записи Configuration.

Упрощает сценарий «добавить новую подсистему/справочник» без ручного разбора и записи Configuration.mdo на стороне клиента.

---

## Поддерживаемые типы и форматы (mdclasses)

- **EDT (.mdo):** запись `Subsystem`, `Configuration`, `Catalog` (и при необходимости других типов по мере реализации конвертеров).
- **Designer (.xml):** запись `Subsystem` (и при необходимости других типов).

Формат определяется по расширению пути в `writeObject`.

## См. также

- План [API записи метаданных и интеграция с VSCode extension](https://github.com/1c-syntax/mdclasses/issues/158).
- Реализация в mdclasses: пакеты `writer`, `writer.edt`, `writer.designer`.
