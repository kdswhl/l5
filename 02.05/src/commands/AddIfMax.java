package commands;

import managers.CollectionManager;
import models.Ask;
import models.Ticket;
import utility.Console;
import utility.ExecutionResponse;

/**
 * Команда 'add_if_max'. Добавляет новый элемент в коллекцию если он больше всех ее элементов.
 */
public class AddIfMax extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public AddIfMax(Console console, CollectionManager collectionManager) {
        super("add_if_max {element}", "добавить новый элемент, если его ID больше максимального в коллекции");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        try {
            if (!arguments[1].isEmpty()) {
                return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
            }

            console.println("Создание нового Ticket:");

            // Запрос ID вручную у пользователя

            Ticket newTicket = Ask.askTicket(console, collectionManager.getFreeId(), collectionManager.getFreeIdVenue());

            if (newTicket == null || !newTicket.validate()) {
                return new ExecutionResponse(false, "Невалидные данные элемента");
            }

            // Находим максимальный ID в коллекции
            Integer maxId = collectionManager.getCollection().stream()
                    .map(Ticket::getId)
                    .max(Integer::compare)
                    .orElse(-1);

            if (newTicket.getId() > maxId) {
                collectionManager.add(newTicket);
                return new ExecutionResponse(true, "Элемент успешно добавлен!");
            } else {
                return new ExecutionResponse(false, "Элемент меньше максимального и не будет сохранен");
            }
        } catch (NumberFormatException e) {
            return new ExecutionResponse(false, "Некорректный формат ID");
        } catch (Ask.AskBreak e) {
            return new ExecutionResponse(false, "Создание элемента прервано");
        }
    }
}