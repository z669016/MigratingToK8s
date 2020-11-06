package liveproject.m2k8s.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewProfile extends UpdateProfile {
    @NotNull
    @Size(min = 5, max = 25, message = "{password.size}")
    private String password;
}
