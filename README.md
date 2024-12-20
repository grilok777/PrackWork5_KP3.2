Опис програми

Дана програма здійснює пошук усіх зображень у вказаній користувачем директорії та її піддиректоріях.

Головні функціональні можливості:

Користувач вводить шлях до директорії для пошуку.

Програма рекурсивно проходить по директорії та всім її піддиректоріям.

Визначає файли, які є зображеннями (за розширеннями .png, .jpg, .jpeg, .bmp, .gif).

Виводить загальну кількість знайдених зображень.

Відкриває останнє знайдене зображення з максимальною глибиною у файловій структурі.

Технічна реалізація:

Використано Fork/Join Framework для ефективного обходу директорій.
Пошук файлів організовано через створення окремих підзадач для кожної піддиректорії.
Кожна підзадача (клас ImageSearchTask) знаходить зображення у своїй директорії та рекурсивно викликає пошук у вкладених директоріях.
Для роботи із зображеннями використовується клас ImageFileWithDepth, який зберігає файл та рівень його вкладеності.

Пояснення вибору підходу

Програма реалізована за допомогою підходу Work Stealing через Fork/Join Framework.

Причини вибору цього підходу:

Простота реалізації:

Fork/Join Framework дозволяє автоматично ділити задачу на підзадачі та ефективно розподіляти їх між потоками.
Мінімальний ручний контроль за потоками, так як "крадіжка задач" здійснюється автоматично.

Швидкодія:

У ситуаціях із нерівномірною кількістю файлів у піддиректоріях, Work Stealing забезпечує оптимальне балансування навантаження між потоками.
Підходить для глибоких дерев директорій, де час пошуку може суттєво залежати від розподілу задач.

Масштабованість:

Підхід дозволяє максимально використовувати доступні процесори, автоматично підлаштовуючись під їх кількість.
Результат:

Введіть директорію: src/test

Кількість знайдених зображень: 11

Відкриття файлу: D:\Mysor2\3kurs\Asunhrone\PrackWork5.2_3\src\test\test0\test03\test000\741726.jpg

![image](https://github.com/user-attachments/assets/52cdf106-bdf3-4371-9039-fc07093ea8a3)
Програма швидко знаходить усі зображення у вказаній директорії та відкриває останнє. Завдяки такій обробці, час виконання задачі мінімізується.
Висновок
Переваги підходу: Використання Fork/Join Framework у Work Stealing дозволяє ефективно обробляти великі обсяги директорій, рівномірно розподіляючи роботу між потоками. Це особливо корисно при великій глибині вкладеності.


