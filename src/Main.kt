import kotlinx.coroutines.*

suspend fun main() = coroutineScope {
    println("Программа работы с базой данных сотрудников")
    delay(1000L)
    val personManager = PersonManager()

    while (isActive){
        println("Добавить сотрудника:\n1.Да\n2.Прочитать базу данных")
        print("Введите пункт: ")
        val check = readln()
        when(check){
            "1" -> {
                print("Введите имя сотрудника: ")
                val name = readln()
                print("Введите зарплату сотрудника: ")
                val money = readln().toDouble() ?: 0.0

                val newPerson = Person(name, money)
                personManager.addPerson(newPerson)
                addPassword(newPerson)
                println("Сотрудник создан")
            }
            "2" -> {
                println("Чтение базы данных:")
                val wait = launch { readDataPersonList() }
                launch { if (stopWork()) wait.cancelAndJoin() }
                break
            }
            else -> println("Ошибка: Такого значения нет!")
        }
    }

}

val personList = ArrayList<Person>()
val resultList = HashMap<Person, Int>()

suspend fun addPassword(person: Person) = coroutineScope{
    launch{
        val pass = (100000..999999).random()
        delay(500L)
        resultList[person] = pass
    }
}

suspend fun readDataPersonList() {
    resultList.forEach {
        delay(1000L)
        println("Сотрудник: ${it.key}; пароль: ${it.value}")
    }
}

fun stopWork():Boolean {
    println("Принудительно завершить программу - 0")
    val command = readln()
    return command == "0"
}

data class Person(val name:String, val money:Double)

class PersonManager{
    fun addPerson(person: Person){
        personList.add(person)
    }
}