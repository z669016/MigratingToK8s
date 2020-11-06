package liveproject.m2k8s.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfile {
        @NotNull
        @Size(min = 5, max = 16, message = "{username.size}")
        private String username;

        @NotNull
        @Size(min = 2, max = 30, message = "{firstName.size}")
        private String firstName;

        @NotNull
        @Size(min = 2, max = 30, message = "{lastName.size}")
        private String lastName;

        @NotNull
        @Email
        private String email;
}
