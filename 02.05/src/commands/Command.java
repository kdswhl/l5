package commands;

import utility.ExecutionResponse;

/**
 * Абстрактный класс для всех команд.
 */
public abstract class Command {
    private final String name;
    private final String description;

    /**
     * Конструктор команды.
     * @param name Название команды.
     * @param description Описание команды.
     */
    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Возвращает название команды.
     * @return Название команды.
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает описание команды.
     * @return Описание команды.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Сравнивает команды .
     * @param obj Объект для сравнения.
     * @return Результат сравнения.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Command command = (Command) obj;
        return name.equals(command.name) && description.equals(command.description);
    }

    /**
     * Возвращает хеш-код команды.
     * @return Хеш-код.
     */
    @Override
    public int hashCode() {
        return name.hashCode() + description.hashCode();
    }

    /**
     * Возвращает строковое представление команды.
     * @return Строка с именем и описанием.
     */
    @Override
    public String toString() {
        return "Command{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    /**
     * Абстрактный метод выполнения команды.
     * @param userCommand Аргументы команды.
     * @return Результат выполнения.
     */
    public abstract ExecutionResponse apply(String[] userCommand);
}