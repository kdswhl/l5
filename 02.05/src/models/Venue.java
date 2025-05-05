package models;
import utility.*;

public class Venue {
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private long capacity; //Значение поля должно быть больше 0
    private Address address; //Поле не может быть null

    public Venue(Integer id, String name, long capacity, Address address) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.address = address;
    }

    public Venue(String s){
        try{ this.id = Integer.parseInt(s.split("| ")[0]);} catch (NumberFormatException e){return;}
        this.name = s.split("| ")[1];
        try{ this.capacity = Long.parseLong(s.split("| ")[2]);} catch (NumberFormatException e){return;}
        this.address = new Address(s.split("| ")[3]);

    }


    @Override
    public String toString() {
        return "Venue {id: " + id + "| " +
                "name: " + name + "| " +
                "capacity: " + capacity + "| " +
                address.toString();
    }

}


