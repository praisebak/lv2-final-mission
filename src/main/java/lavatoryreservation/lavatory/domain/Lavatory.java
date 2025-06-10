package lavatoryreservation.lavatory.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Lavatory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Sex sex;
    private String description;

    public Lavatory(Long id, Sex sex, String description) {
        this.id = id;
        this.sex = sex;
        this.description = description;
    }

    protected Lavatory() {

    }

    public String getDescription() {
        return description;
    }

    public Long getId() {
        return id;
    }
}
