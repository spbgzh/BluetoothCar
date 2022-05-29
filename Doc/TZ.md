# BluetoothCar

## Общие сведенияx

1. Цель проекта: Используйте макетную плату stm32, чтобы сделать автомобиль, управляемый Bluetooth. Используйте приложение Android для управления движением автомобиля.  
2. Команда исполнителей: Ляо Ихун, Го Цзыхань  

## Технические требования

1. Требования к функциональным характеристикам:
    1. Возможно планирование маршрута. То есть пользователь может заранее ввести маршрут автомобиля, и тогда автомобиль будет действовать согласно введенному сценарию.
    2. Мобильный телефон отображает скорость в реальном времени
    3. Bluetooth-управление автомобилем на Android приложение
2. Требования к надежности
    Соединение между различными электронными компонентами достаточно прочное, корпус устойчив к ударам, соединение сигнала Bluetooth стабильно, и Bluetooth будет активно искать соединение после отключения.
3. Условия эксплуатации
    1. Для правильной работы требуется только один оператор, который умеет использовать разработанное нами программное обеспечение.
    2. Температура окружающей среды выше 15 градусов и ниже 40 градусов. Относительная влажность не выше 90%. Автомобиль не является водонепроницаемым, будьте осторожны, не прикасайтесь к жидкостям.
    3. Bluetooth для обмена данными  

4. Требования к составу и параметрам технических средств
    1. Технология Bluetooth: это беспроводное соединение малого радиуса действия, которое используется для управления вождением автомобиля в этом проекте.
    2. Android-приложение: основной компонент смартфона с системой Android, в данном проекте в качестве контроллера bluetooth-автомобиля.
    3. Встроенная разработка stm32: используйте Inetrrupt для реализации функции взаимодействия данных между автомобилем и главным компьютером и используйте I2C для реализации OLED-дисплея.
    4. 3D-печать: используйте 3D-печать для изготовления кузова, шасси и других компонентов автомобиля.   
5. Требования к информационной и программной совместимости
    1. Разработанное программное обеспечение доступно только на платформе Android
    2. Язык программирования - C
    3. Программное средство: CubeIDE
    4. разработка библиотеки HAL

6. Требования к транспортированию и хранению
    Требования к доставке: автомобиль Bluetooth достаточно мал, чтобы поместиться в рюкзаке. Используя батареи AA, сам продукт не имеет батарей, которые могут удовлетворить потребности в транспортировке по суше, морю и воздуху.  
    Требования к хранению: автомобиль с Bluetooth содержит электронные компоненты, избегайте попадания прямых солнечных лучей, во избежание старения его необходимо хранить в условиях низкой температуры и низкой влажности.

## Требования к документации
1. Документация соответствует требованиям GOST 7.32-2001
2. В документации должны быть указаны конечная функция и условия работы тележки.
3. Документация тележки должна быть размещена на Gitlab
4. Документация должна содержать объяснение API программы.

## Технико-экономические показатели

1. плата stm32 600p
2. 3д печать 50р
3. Bluetooth-модуль hc-08 400p
4. Моторный привод 300p
5. Мотор*2 200р
6. oled 200p
7. Остальные детали 150р.
2100р всего
По сравнению с другими аналогичными продуктами корпус нашего продукта состоит из 3
d полиграфическое производство, поддержка индивидуальной настройки клиента. Продукт не удобен для массового производства, но сама цена производства очень дешевая.

## Cтадии и этапы разработки

1. Постановка цели
2. Оценка проекта (экономическая, рисковая)
3. Разработать встроенную систему (автомобиль)
4. Используйте Bluetooth Controller, чтобы протестировать написанную систему для получения обратной связи, а затем вернитесь к третьему шагу, пока не получите идеальную встроенную систему.
5. Разработайте собственное программное обеспечение для Android
6. Используйте написанную встроенную систему для тестирования, создайте обратную связь по результатам, вернитесь к пятому шагу и узнайте, как получить программное обеспечение, которое может соответствовать написанной встроенной системе.
7. Общий тест
8. Оптимизация
9. Закрытие проекта

## Порядок контроля и приемки

1. Проверьте срок службы батареи с момента замены новой батареи до момента, когда она перестанет работать.
2. Проверьте самое дальнее расстояние управления Bluetooth
3. Проверьте ударопрочность автомобиля
4. Проверьте помехоустойчивость автомобильного сигнала.

## Ссылки на источники

1. ГОСТ 19.201-78 Техническое задание. Требования к содержанию и оформлению