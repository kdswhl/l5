package commands;

import managers.CollectionManager;
import utility.Console;
import utility.ExecutionResponse;
import models.Ticket;
import java.util.List;

/**
 * Команда для вывода элемента с минимальным именем.
 */
public class MinByName extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    /**
     * Конструктор команды.
     * @param console Консоль для ввода/вывода.
     * @param collectionManager Менеджер коллекции.
     */
    public MinByName(Console console, CollectionManager collectionManager) {
        super("min_by_name", "вывести объект с минимальным значением поля name");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду.
     * @param arguments Аргументы команды (не используются).
     * @return Результат выполнения команды.
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        if (!arguments[1].isEmpty()) {
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        }

        List<Ticket> collection = collectionManager.getCollection();
        if (collection.isEmpty()) {
            return new ExecutionResponse(false, "Коллекция пуста!");
        }

        Ticket minTicket = null;
        for (Ticket current : collection) {
            if (minTicket == null) {
                minTicket = current;
                continue;
            }

            String currentName = current.getName();
            String minName = minTicket.getName();

            if (currentName == null) {
                minTicket = current;
            } else if (minName == null) {
                // currentName не null, minName null - оставляем minTicket
            } else if (currentName.compareTo(minName) < 0) {
                minTicket = current;
            }
        }

        return new ExecutionResponse(true,
                "Объект с минимальным именем:\n" +
                        (minTicket != null ? minTicket : "Не найден"));
    }
}