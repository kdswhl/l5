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
        if (s.split(" ~ ").length!=2){
            this.street = null;
            this.zipCode = null;
        }
        else{
            this.street = s.split(" ~ ")[0];
            if(s.split(" ~ ")[1]!="null"){
                this.zipCode = s.split(" ~ ")[1];
            }
            else {this.zipCode = null;}
        }
    }

    @Override
    public String toString() {
        return "Address {street: " + street + " ~ " +
                "zipCode: " + zipCode ;
    }

    public String getStreet(){
        return street;
    }
    public String toString(boolean a) {
        return  street + " ~ " + zipCode ;
    }

    @Override
    public boolean validate() {
        if (street.isEmpty()|| street==null) return false;
        if(street.length()>97) return false;
        return true;
    }
}
