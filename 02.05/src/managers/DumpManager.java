package managers;

import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;
import java.lang.NullPointerException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import utility.Console;
import models.*;

/**
 * Использует файл для сохранения и загрузки коллекции.
 * @author dim0n4eg
 */
public class DumpManager {
    private final String fileName;
    private final Console console;

    public DumpManager(String fileName, Console console) {
        this.fileName = fileName;
        this.console = console;
    }


    private String collection2CSV(Collection<Ticket> collection) {
        try {
            StringBuilder csv = new StringBuilder();
            for (Ticket ticket : collection) {
                String[] fields = Ticket.toArray(ticket);
                csv.append(String.join("$", fields)).append("\n");
            }
            return csv.toString();
        } catch (Exception e) {
            console.printError("Ошибка сериализации");
            return null;
        }
    }

    /**
     * Записывает коллекцию в файл.
     * @param collection коллекция
     */
    public void writeCollection(Collection<Ticket> collection) {
        OutputStreamWriter writer = null;
        OutputStreamWriter writer2 = null;
        OutputStreamWriter writer3 = null;
        try {
            var csv = collection2CSV(collection);
            if (csv == null) return;

            writer = new OutputStreamWriter(new FileOutputStream(fileName));
            try {
                writer.write(csv);
                writer.flush();
                console.println("Коллекция успешна сохранена в файл!");
            } catch (IOException e) {
                console.printError("Неожиданная ошибка сохранения");
            }
        } catch (FileNotFoundException | NullPointerException e) {
            console.printError("Файл не найден");
        } finally {
            try {
                writer.close();
            } catch(IOException e) {
                console.printError("Ошибка закрытия файла");
            }
        }
    }

    private ArrayList<Ticket> CSV2collection(String s) {
        try (Scanner scanner = new Scanner(s)) {
            ArrayList<Ticket> ds = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;
                String[] record = line.split("$");
                Ticket ticket = Ticket.fromArray(record);
                if (ticket.validate()) {
                    ds.add(ticket);
                } else {
                    console.printError("Файл содержит недействительные данные");
                }
            }
            return ds;
        } catch (Exception e) {
            console.printError("Ошибка десериализации");
            return null;
        }
    }

    /**
     * Считывает коллекцию из файл.
     * @return Считанная коллекция
     */
    public void readCollection(Collection<Ticket> collection) {
        console.println("Попытка чтения файла: " + fileName);
        if (fileName != null && !fileName.isEmpty()) {
            try (var fileReader = new Scanner(new File(fileName))) {
                var s = new StringBuilder("");
                while (fileReader.hasNextLine()) {
                    s.append(fileReader.nextLine());
                    s.append("\n");
                }
                collection.clear();
                for (var e: CSV2collection(s.toString()))
                    collection.add(e);
                if (collection != null) {
                    console.println("Коллекция успешна загружена!");
                    return;
                } else
                    console.printError("В загрузочном файле не обнаружена необходимая коллекция!");
            } catch (FileNotFoundException exception) {
                console.printError("Загрузочный файл не найден!");
            } catch (IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        } else {
            console.printError("Аргумент командной строки с загрузочным файлом не найден!");
        }
        collection = new ArrayList<Ticket>();
    }
}