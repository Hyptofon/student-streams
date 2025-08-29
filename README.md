# Student Streams (Java 8, Lambdas & Stream API)

Колекційний приклад про **обробку списку студентів** у декларативному стилі: фільтрація, сортування, групування, агрегації, `Optional`, `Comparator`, `Collectors`, `flatMap`, паралельні стріми. Повністю сумісний з **Java 8**.

---

## Що всередині

* **Модель**: `Student`, `Course`, `Gender` (immutables + builder).
* **Дані**: генератор випадкових студентів `SampleData`.
* **Сервіси (Stream API)**:

  * фільтрація/сортування топ-студентів,
  * групування за групою та статтю,
  * пошук кращого по курсу,
  * середні оцінки по курсах,
  * гістограма груп,
  * паралельні стріми,
  * безпечний пошук `Optional`.
* **Статистика**: `IntSummaryStatistics` по всіх оцінках.
* **Юніт-тести**: JUnit 5.
* **Готовий `Main`** для швидкого запуску.

---

## Техстек

* Java 8
* Maven
* JUnit 5

---

## Вимоги

* JDK **1.8** (SDK і Language Level = 8)
* Maven 3.x
* IntelliJ IDEA (рекомендовано)

---

## Структура проєкту

```
student-streams
├─ pom.xml
├─ README.md
├─ src
│  ├─ main/java/com/example/streams
│  │  ├─ Main.java
│  │  ├─ model/
│  │  │  ├─ Student.java
│  │  │  ├─ Course.java
│  │  │  └─ Gender.java
│  │  ├─ util/
│  │  │  └─ SampleData.java
│  │  └─ service/
│  │     ├─ StudentQueries.java
│  │     └─ StudentStatistics.java
│  └─ test/java/com/example/streams
│     └─ StudentQueriesTest.java
```

---

## Як імпортувати в IntelliJ IDEA

1. **File → New → Project from Existing Sources…**
2. Виберіть папку проєкту `student-streams` → **Open as Project**.
3. **File → Project Structure…**

   * Project SDK: **1.8**
   * Project language level: **8 – Lambdas, type annotations**.
4. Дочекайтеся, поки Maven підтягне залежності.

---

## Запуск

### Варіант 1: з IntelliJ

* Відкрий `Main.java` → натисни ▶ (Run ‘Main’).

### Варіант 2: з терміналу

```bash
mvn -q clean package
java -cp target/student-streams-1.0.0.jar com.example.streams.Main
```

---

## Тести

### IntelliJ

* Відкрий `StudentQueriesTest` → ▶ Run.

### Maven

```bash
mvn -q -Dtest=* test
```

---

## Приклади того, що виводить `Main`

* Топ-5 студентів із середнім ≥ 80, відсортовані за прізвищем.
* Середні по групах (`Map<String,Double>`).
* Найкращий студент по курсу **Java Programming**.
* Топ-10 за глобальним середнім балом.
* Унікальні коди курсів (через `flatMap + distinct`).
* Гістограма груп (кількість у кожній).
* Середні по кожному курсу.
* Глобальне середнє у **parallel stream**.
* Загальна статистика оцінок (`IntSummaryStatistics`).

---

## Ключові фішки Stream API (що показано в коді)

* `filter`, `map`, `flatMap`, `sorted`, `limit`
* `collect` з `Collectors`:

  * `groupingBy`, `mapping`, `averagingInt/Double`, `counting`, `collectingAndThen`
* `Comparator.comparing`, `thenComparing`, `reversed`
* `Optional`, `OptionalDouble`
* Паралельні стріми: `parallelStream()`
* Незмінні сутності, Builder-патерн

---


