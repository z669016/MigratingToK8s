package liveproject.m2k8s.service;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class NewProfile extends UpdateProfile {
    @NotNull
    @Size(min = 5, max = 25, message = "{password.size}")
    private String password;

    public NewProfile(String username, String password, String firstName, String lastName, String email) {
        super(username, firstName, lastName, email);
        this.password = password;
    }
}
