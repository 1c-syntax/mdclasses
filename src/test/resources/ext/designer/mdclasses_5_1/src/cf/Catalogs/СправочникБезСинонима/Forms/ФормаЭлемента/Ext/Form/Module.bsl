﻿
&НаСервере
Процедура ПриСозданииНаСервере(Отказ, СтандартнаяОбработка)КонецПроцедуры

&НаСервере
Процедура Команда1НаСервере()
	// Вставить содержимое обработчика.
КонецПроцедуры

&НаКлиенте
Процедура Команда1(Команда)
	ПараметрыСтруктура = Новый Структура;
	ПараметрыСтруктура.Вставить("Измерение1", "123");
	ПараметрыСтруктура.Вставить("Измерение2", "123");  // надо передавать все измерения
	ПараметрыМассив = Новый Массив;
	ПараметрыМассив.Добавить(ПараметрыСтруктура);
	КлючЗаписиРегистра = Новый("РегистрСведенийКлючЗаписи.РегистрСведений1", ПараметрыМассив); 
	ОткрытьФорму("РегистрСведений.РегистрСведений1.Форма", Новый Структура("Ключ", КлючЗаписиРегистра));
КонецПроцедуры
