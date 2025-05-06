package commands;
import managers.CollectionManager;
import utility.Console;
import java.util.Collections;
import java.util.LinkedList;
import utility.ExecutionResponse;

/**
 * Команда для вывода цен в порядке убывания.
 */
public class PrintFieldDescendingPrice extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    /**
     * Конструктор команды.
     * @param console Консоль для ввода/вывода.
     * @param collectionManager Менеджер коллекции.
     */
    public PrintFieldDescendingPrice(Console console, CollectionManager collectionManager) {
        super("print_field_descending_price", "вывести значения поля price всех элементов в порядке убывания");
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
        if (!arguments[1].isEmpty()) return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        var beNull = false;
        var ll = new LinkedList<Double>();
        for (var e : collectionManager.getCollection()) {
            if (e.getPrice() == null) {
                beNull = true;
            } else {
                ll.add(e.getPrice());
            }
        }

        ll.sort(Collections.reverseOrder());
        var s = "";
        for (var e : ll) {
            s += " " + e;
        }
        if (beNull) {
            s += " null";
        }
        s = s.trim();
        return new ExecutionResponse(s);
    }
}