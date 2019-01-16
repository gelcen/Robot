# Robot
Программа была сделана с помощью NetBeans 8.2, JDK 8.0, драйвер jdbc 6. В качестве базы данных используется Oracle DB 11. <br>
* Main.java - главный класс, где считываются данные для подключения к БД и рассчитываются индексы для каждого потока. По умолчанию количество потоков равно 1000, если количество записей меньше количества потоков, то запускается только два потока. 
* Robot.java - класс для сущности-потока. Подключается к БД и проверяет доступность сайтов. 
* DateReader.java - преобразует дату из файла настроек в формат для Sql Date. 
* properties.txt - настройки подключения и дата проверки. <br>
Таблица до работы программы:
![before](https://pp.userapi.com/c844416/v844416483/17dd1c/ZGjXUGajTso.jpg) <br>
После: <br>
![before](https://pp.userapi.com/c844416/v844416483/17dd14/C234Fdp4RfE.jpg) <br>
## Недоработки ##
* Набор тестовых данных брался из сервиса mockaroo.com. Некоторые тестовые url выдают исключения, в связи с этим выброс исключений в той части программы, где делается запрос к этим URL, был закомментирован(в классе Robot). 
* Также в бесплатной версии данного сервиса возможна генерации только 1000 записей, но программа должна работать и с большим количеством записей. Генерация таблицы и вставка тестовых данных в нее есть в файле init.sql. 
## Сборка и запуск ##
Сборку и запуск можно осуществить с помощью maven в консоли или в любой современной IDE с поддержкой Maven.

