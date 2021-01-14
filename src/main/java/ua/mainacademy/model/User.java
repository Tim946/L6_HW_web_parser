package ua.mainacademy.model;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)

public class User {
    private String login;
    private String password;
    private String firstName;
    private String lastName;

}
