package commands;

import managers.CollectionManager;
import utility.Console;
import utility.ExecutionResponse;

import java.util.Collections;
import java.util.LinkedList;

public class MinByName extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public MinByName(Console console, CollectionManager collectionManager) {
        super("min_by_name", "вывести любой объект из коллекции, значение поля name которого является минимальным");
        this.console = console;
        this.collectionManager = collectionManager;
    }


    @Override
    public ExecutionResponse apply(String[] arguments) {
        if (!arguments[1].isEmpty()) return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        var beNull = false;
        var ll = new LinkedList<Double>();
        for (var e: collectionManager.getCollection()){
            if (e.getName() == null){
                beNull = true;
            }
            else {
                ll.add(e.getPrice());
            }
        }

        ll.sort(Collections.reverseOrder());

        var s ="";
        if (beNull){
            s =" null";
        }
        for (var e: ll){
            s+=" "+ e;
        }
        return new ExecutionResponse(s);
    }
}
