package models;
import utility.*;

public class Address implements Validatable {
    private String street; //Длина строки не должна быть больше 97, Поле не может быть null
    private String zipCode; //Поле может быть null

    public Address(String street, String zipCode) {
        this.street = street;
        this.zipCode = zipCode;
    }

    public Address(String s){
        this.street = s.split(" ~ ")[0];
        this.zipCode = s.split(" ~ ")[1];

    }

    @Override
    public String toString() {
        return "Address {street: " + street + " ~ " +
                "zipCode: " + zipCode ;
    }

    @Override
    public boolean validate() {
        if (street.isEmpty()|| street==null) return false;
        if(street.length()>97) return false;
        return true;
    }
}
