package lavatoryreservation.member.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lavatoryreservation.lavatory.domain.Sex;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private Sex sex;

    public Member(Long id, String name, String email, Sex sex) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.sex = sex;
    }

    protected Member() {

    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public Sex getSex() {
        return sex;
    }

    public String getName() {
        return name;
    }
}
