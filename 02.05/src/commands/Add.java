package commands;

import managers.CollectionManager;
import models.Ask;
import models.Ticket;
import utility.Console;
import utility.ExecutionResponse;

/**
 * Команда 'add'. Добавляет новый элемент в коллекцию.
 */
public class Add extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public Add(Console console, CollectionManager collectionManager) {
        super("add {element}", "добавить новый элемент в коллекцию");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */

    public ExecutionResponse apply(String[] arguments) {
        try {
            if (!arguments[1].isEmpty()) return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");

            console.println("* Создание нового Ticket:");
            Ticket d = Ask.askTicket(console, collectionManager.getFreeId(), collectionManager.getFreeIdVenue());

            if (d != null && d.validate()) {
                collectionManager.add(d);
                return new ExecutionResponse("Ticket успешно добавлен!");
            } else return new ExecutionResponse(false,"Поля не валидны, Ticket не создан!");
        } catch (Ask.AskBreak e) {
            return new ExecutionResponse(false,"Отмена...");
        }
    }
}