Сервер-клиент CLI приложение на основе Spring Shell

Команды на клиенте:

	disconnect: отключение от сервера
	
	get tasks: получение всех задач текущего пользователя
	
	login: авторизация с получением UUID - UUID хранится на сервере в классе ConnectServer в переменной token
	
	reg: Регистрация нового пользователя на сервере с последующей авторизацией
	
	start task <task name>: создание и запуск новой задачи. Вместо <task name> необходимо указать имя новой задачи.
	
	task <id>: Получение информации о задаче по id. Вместо <id> необходимо указать id задачи. Список всех задач и их id можно посмотреть по команде get tasks.
	
	task history <id>: получение истории смены статусов задачи. Вместо <id> необходимо указать id задачи. Список всех задач и их id можно посмотреть по команде get tasks.

Клиент каждые 10 секунд (@Scheduled(fixedDelay = 10000)) отправляет запрос на сервер и сверяется есть ли среди задач созданных в текущем сенсе выполненые. Если есть выполненые задачи выводит их на экран.

При выходе из сеанса или смене пользователя список текущих задач очищается. И чтобы получить статус задачи необходим прямой запрос по комманде task <id> или get tasks.

На сервере задачи обрабатываются рандомное колличесво времени от 1 минуты до 5 минут.
Общие ресурсф вынесены в общий модуль api