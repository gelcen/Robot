# Robot
Программа была сделана с помощью NetBeans 8.2, JDK 8.0, драйвер jdbc 6. В качестве базы данных используется Oracle DB 11. <br>
Таблица до работы робота:
![before](https://pp.userapi.com/c844416/v844416483/17dd1c/ZGjXUGajTso.jpg) <br>
После: <br>
![before](https://pp.userapi.com/c844416/v844416483/17dd14/C234Fdp4RfE.jpg)
Набор тестовых данных брался из сервиса mockaroo.com. Некоторые тестовые url выдают исключения, в связи с этим выброс исключений в программе был закомментирован(в классе Robot). Также в бесплатной версии данного сервиса возможна генерации только 1000 записей. Генерация таблицы и вставка тестовых данных в нее есть в файле init.sql. 
## Сборка и запуск ##
Сборку и запуск можно осуществить с помощью maven в консоли или в любой современной IDE с поддержкой Maven.
