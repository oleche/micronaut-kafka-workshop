package demo.ticket;

import javax.validation.constraints.NotBlank;

public class OrderSaveCommand {
    @NotBlank
    private String firstName;
    private String lastName;
    private String email;
    private String price;

    public OrderSaveCommand() {}

    public OrderSaveCommand(String firstName) {
        this.firstName = firstName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
