package lavatoryreservation.member.dto;

import lavatoryreservation.lavatory.domain.Sex;

public record SignupDto(String name, String email, Sex sex) {
}
