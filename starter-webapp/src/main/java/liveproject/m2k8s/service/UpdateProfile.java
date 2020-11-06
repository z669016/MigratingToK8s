package liveproject.m2k8s.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfile {
        @NotNull(message = "{username.size}")
        @Size(min = 5, max = 16, message = "{username.size}")
        private String username;

        @NotNull(message = "{firstName.size}")
        @Size(min = 2, max = 30, message = "{firstName.size}")
        private String firstName;

        @NotNull(message = "{lastName.size}")
        @Size(min = 2, max = 30, message = "{lastName.size}")
        private String lastName;

        @NotBlank(message = "{email.message}")
        @Email(message = "{email.message}")
        private String email;
}
