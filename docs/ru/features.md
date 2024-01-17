# Возможности

Ниже перечислены текущие возможности библиотеки.

## Общее

Библиотека предоставляет возможность чтения описания объектов из каталога исходных файлов проектов 1С:Предприятие. Поддерживаются оба официальных формата 
- формат выгрузки конфигуратора
- формат проектов EDT

## Поддерживаемые типы контейнеров объектов метаданных

На данный момент библиотека предоставляет возможность чтения информации 
- конфигураций
- расширений конфигураций
- внешних отчетов и обработок

Эти объекты в контексте библиотеки являются некими "контейнерами метаданных", т.е. для чтения необходимо указывать расположение именно корневых объектов, а не файлов объектов метаданных, форм и т.д.
Реализация объектов-контейнеров метаданных располагается в пакете [mdclasses](com.github._1c_syntax.bsl.mdclasses), там же расположена фабрика для создания объекта из каталога файлов. 

## Поддерживаемые метаданные

На данный момент поддерживается загрузка всех видов метаданных, существующих в версиях платформы 1С до 8.3.24. В заивисимости от типа объекта и потребностей, объем читаемой информации может различаться (реализация чтения дополнительной информации выполняется от задач).
Актуальное содержимое того или иного вида объекта метаданных можно всегда находится в классе его реализации в пакете [mdo](com.github._1c_syntax.bsl.mdo).

Немного о структуре пакета:
- в корне расположены классы видов объектов метаданных (Справочники, Документы, Перечисления и т.д.), базовые интерфейсы
- в пакете `children` находятся классы с дочерними (несамостоятельными) объектами (формы, макеты, модули т.д.)
- в пакете `storage` расположены хранилища вспомогательной информации (содержимое макетов, XDTO-схем и т.д.)
- в пакете `support` находятся вспомогательные классы и перечисления