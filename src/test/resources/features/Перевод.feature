#language:ru

Функциональность: Перевод с одной своей карты на другую
#   Структура сценария: Перевод с одной своей карты на другую
#    Пусть совершен Вход в личный кабинет
#    Когда на странице "Дашбоард" пользователь нажимает на кнопку "Пополнить" напротив своей "0001" карты
#    И страница "Пополнение карты" загрузилась
#    И в поле "Сумма" введено значение "5000" рублей
#    И в поле "Откуда" введен номер "5559 0000 0000 0002"
#    И выполнено нажатие на кнопку "Пополнить"
#    Тогда страница "Дашбоард" загрузилась
#    И баланс карты "0001" должен стать "15 000" рублей

  Структура сценария: Перевод с одной своей карты на другую
    Пусть пользователь залогинен с именем "vasya", паролем "qwerty123" и кодом "12345"
    Когда он переводит "5 000" рублей с карты с номером "2" на свою "1" карту с главной страницы
    Тогда баланс его "1" карты из списка на главной странице должен стать "15 000" рублей
    Примеры:
      | имя   | пароль    | код   |
      | vasya | qwerty123 | 99999 |
    Примеры:
      | рублей | номерС | номерНа |
      | 5000   | 2      | 1       |
    Примеры:
      | номер | баланс |
      | 1     | 15000  |