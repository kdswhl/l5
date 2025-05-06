import commands.*;
import managers.*;
import utility.*;


public class Main {
    public static void main(String[] args) {
        var console = new StandardConsole();

        String fileName = System.getenv("INPUT_FILE");
        if (fileName == null || fileName.isEmpty()) {
            console.println("Установите переменную окружения INPUT_FILE с именем файла");
            System.exit(1);
        }

        // Используем переменную окружения вместо args[0]
        var dumpManager = new DumpManager(fileName, console);
        var collectionManager = new CollectionManager(dumpManager);
        if (!collectionManager.loadCollection()) {
            System.exit(1);
        }

        var commandManager = new CommandManager() {{
            register("help", new Help(console, this));
            register("info", new Info(console, collectionManager));
            register("show", new Show(console, collectionManager));
            register("add", new Add(console, collectionManager));
            register("update", new Update(console, collectionManager));
            register("remove_by_id", new RemoveById(console, collectionManager));
            register("clear", new Clear(console, collectionManager));
            register("save", new Save(console, collectionManager));
            register("execute_script", new ExecuteScript(console));
            register("exit", new Exit(console));
            register("remove_at", new RemoveAt(console, collectionManager));
            register("add_if_max", new AddIfMax(console, collectionManager));
            register("reorder", new Reorder(console, collectionManager));
            register("remove_any_by_venue", new RemoveAnyByVenue(console, collectionManager));
            register("min_by_name", new MinByName(console, collectionManager));
            register("print_field_descending_price", new PrintFieldDescendingPrice(console, collectionManager));
        }};

        new Runner(console, commandManager).interactiveMode();
    }
}

