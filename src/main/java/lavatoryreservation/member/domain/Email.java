package lavatoryreservation.member.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class Email {

    private String email;

    public Email(String email) {
        this.email = email;
    }

    protected Email() {

    }

    public String getEmailString() {
        return email;
    }

    public boolean isSameMember(Email anotherEmail) {
        return email.equals(anotherEmail.email);
    }
}
