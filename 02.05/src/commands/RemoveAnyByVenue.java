package commands;
import managers.CollectionManager;
import models.*;
import utility.Console;
import utility.ExecutionResponse;
import java.lang.NumberFormatException;

/**
 * Команда для удаления элемента по venue ID.
 */
public class RemoveAnyByVenue extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    /**
     * Конструктор команды.
     * @param console Консоль для ввода/вывода.
     * @param collectionManager Менеджер коллекции.
     */
    public RemoveAnyByVenue(Console console, CollectionManager collectionManager) {
        super("remove_any_by_venue", "удалить из коллекции один элемент, значение поля venue которого эквивалентно заданному");
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
            if (arguments[1].isEmpty()) {
                return new ExecutionResponse(false, "Не указан venue");
            }
            Ticket toRemove = null;

            try {
                int venueId = Integer.parseInt(arguments[1]);
                for (Ticket ticket : collectionManager.getCollection()) {
                    if (ticket.getVenue().getId() == venueId) {
                        toRemove = ticket;
                        break;
                    }
                }
            } catch (NumberFormatException e) {
                return new ExecutionResponse(false, "Некорректный формат ID-Venue");
            }

            if (toRemove != null) {
                collectionManager.remove(toRemove.getId());
                return new ExecutionResponse(true, "Ticket с ID=" + toRemove.getId() + " удален");
            } else {
                return new ExecutionResponse(false, "Ticket с указанным venue не найдены");
            }
        } catch (NumberFormatException e) {
            return new ExecutionResponse(false, "Ввод venue прерван");
        }
    }
}