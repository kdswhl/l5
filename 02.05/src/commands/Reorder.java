package commands;
import managers.*;
import models.Ticket;
import utility.Console;
import utility.ExecutionResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 * Команда 'reorder' сортирует коллекцию в обратном порядке.
 */
public class Reorder extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public Reorder(Console console, CollectionManager collectionManager) {
        super("reorder", "отсортировать коллекцию в порядке, обратном нынешнему");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Результат выполнения команды.
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        if (!arguments[1].isEmpty()) {
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        }

        ArrayList<Ticket> sortedCollection = new ArrayList<>(collectionManager.getCollection());

        // Сортируем по убыванию ID
        Collections.sort(sortedCollection,
                Comparator.comparingInt(Ticket::getId).reversed()
        );

        if (sortedCollection.isEmpty()) {
            return new ExecutionResponse("Коллекция пуста!");
        }

        StringBuilder result = new StringBuilder();
        for (Ticket ticket : sortedCollection) {
            result.append(ticket).append("\n\n");
        }
        return new ExecutionResponse(result.toString().trim());
    }
}
