package cp.fr.localsqlapp.model;

/**
 * Created by Formation on 10/01/2018.
 */

public class Contact {

    private String name;
    private String firstName;
    private String email;
    private Long id;

    public Contact() {

    }

    public String getName() {
        return name;
    }

    public Contact setName(String name) {
        this.name = name;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Contact setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Contact setEmail(String email) {
        this.email = email;
        return this;
    }

    public Contact(String name, String firstName, String email) {

        this.name = name;
        this.firstName = firstName;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public Contact setId(Long id) {
        this.id = id;
        return this;
    }
}
