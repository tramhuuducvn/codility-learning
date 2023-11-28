package org.example.java_core.annotation.JsonTool;

@JsonSerializable
class Person {
    @JsonElement
    private String firstName;
    @JsonElement
    private String lastName;
    @JsonElement(key = "personAge")
    private int age;
    @JsonElement
    private String address;

    public Person() {
    }

    public Person(String firstName, String lastName, int age, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private void initNames() {
        this.firstName = this.firstName.substring(0, 1).toUpperCase() + this.firstName.substring(1).toLowerCase();
        this.lastName = this.lastName.substring(0, 1).toUpperCase() + this.lastName.substring(1).toLowerCase();
    }

}

public class JsonDemo {
    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
        Person person = new Person("Duc", "Tram", 22, "Phu Yen");
        ObjectToJsonConverter objectToJsonConverter = new ObjectToJsonConverter();
        System.out.println(objectToJsonConverter.convertToJson(person));
    }
}