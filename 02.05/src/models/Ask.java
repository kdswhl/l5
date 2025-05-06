package models;

import managers.CollectionManager;
import utility.Console;

import java.util.NoSuchElementException;

public class Ask {
    public static class AskBreak extends Exception {

    }

    public static Ticket askTicket(Console console, Integer id, Integer idV) throws AskBreak {
        try {
            String name;
            while (true) {
                console.print("name (обязательно для ввода): ");
                name = console.readln().trim();
                if (name.equals("exit")) throw new AskBreak();
                if (!name.isEmpty()) break;
            }
            var coordinates = askCoordinates(console);
            var price = askPrice(console);
            var comment = askCommemt(console);
            var type = askType(console);
            var venue = askVenue(console, idV);
            return new Ticket(id, name, coordinates, price, comment, type, venue);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    public static Venue askVenue(Console console, Integer id) throws AskBreak {
        try {
            String name;
            while (true) {
                console.print("name Venue: (поле обязательно для ввода) ");
                name = console.readln().trim();
                if (name.equals("exit")) throw new AskBreak();
                if (!name.isEmpty()) break;
            }
            var capacity = askCapacity(console);
            var address = askAddress(console);
            return new Venue(id, name, capacity, address);

        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    public static Long askCapacity(Console console) {
        try {
            long capacity;
            while (true) {
                console.print("capacity (нужно ввести число больше 0, обязательно для ввода): ");
                var line = console.readln().trim();
                if (!line.equals("")) {
                    try {
                        capacity = Long.parseLong(line);
                        if (capacity > 0) {
                            break;
                        }
                    } catch (NumberFormatException e) {}
                }
            }
            return capacity;
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    public static Address askAddress(Console console) {
        String street;
        while (true) {
            console.print("street (в названии не более 97 символов, обязательно для ввода): ");
            street = console.readln().trim();
            if (!street.isEmpty() && street.length() <= 97) {break;}
        }
        String zipCode;
        while (true) {
            console.print("zipCode (можно пропустить): ");
            zipCode = console.readln().trim();
            break;
        }
        return new Address(street, zipCode);}

    public static Coordinates askCoordinates(Console console) throws AskBreak {
        try {
            Double x;
            while (true) {
                console.print("coordinate x (число обязательно для ввода): ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.equals("")) {try {x = Double.parseDouble(line); break;} catch (NumberFormatException e) {}}
            }
            int y;
            while (true) {
                console.print("coordinates y (число до 857, обязательно для ввода): ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.equals("")) {try {y = Integer.parseInt(line);if (y <= 857) break;} catch (NumberFormatException e) {}}
            }
            return new Coordinates(x, y);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    public static TicketType askType(Console console) throws AskBreak {
        try {
            TicketType type;
            while (true) {
                console.print("TicketType из списка (" + TicketType.names() + ") обязательно для ввода: ");
                var line = console.readln().trim().toUpperCase();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.equals("")) {
                    try {
                        type = TicketType.valueOf(line);
                        break;
                    } catch (NullPointerException | IllegalArgumentException e) {
                    }
                }
            }
            return type;
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }

    }

    public static Double askPrice(Console console) throws AskBreak {
        try {
            Double price;
            Double price2;
            while (true) {
                console.print("price (число, необязательно вводить): ");
                var line = console.readln().trim();
                if (line.trim().equals("") | line.isEmpty()) {
                    price = null;
                    return null;
                }
                if (!line.equals("")) {
                    try {
                        price2 = Double.parseDouble(line);
                        if (price2 > 0d) { price = price2; break;}
                    } catch (NumberFormatException e) {}
                }
            }
            return price;
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    public static String askCommemt(Console console) throws AskBreak {
        String comment;
        while (true) {
            console.print("comment (длина не более 380 символов, можно пропустить): ");
            var line = console.readln().trim();
            if (line.equals("") | line.length() <= 380) { comment = line; return comment;}
        }
    }
}
