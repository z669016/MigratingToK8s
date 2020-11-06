package liveproject.m2k8s.web;

import liveproject.m2k8s.config.TestWebConfig;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestWebConfig.class, ProfileController.class})
public class ProfileControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ProfileController controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @SneakyThrows
    @Test
    void showRegistrationForm() {
        mockMvc.perform(get("/profile/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("registerForm"))
                .andExpect(model().attribute("newProfile", hasProperty("password", is(nullValue()))));
    }

    @SneakyThrows
    @Test
    void showProfile() {
        mockMvc.perform(get("/profile/zasupitts"))
                .andExpect(status().isOk())
                .andExpect(view().name("profile"))
                .andExpect(model().attribute("updateProfile", hasProperty("email", is("zasupitts@hollywood.com"))));
    }

    @SneakyThrows
    @Test
    void processRegistration() {
        mockMvc.perform(post("/profile/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "USERNAME")
                .param("password", "PASSWORD")
                .param("firstName", "FIRST")
                .param("lastName", "LAST")
                .param("email", "FIRST@LAST.COM"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/profile/USERNAME"));
    }

    @SneakyThrows
    @Test
    void processRegistrationWithErrors() {
        mockMvc.perform(post("/profile/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("registerForm"))
                .andExpect(model().errorCount(5));
    }

    @SneakyThrows
    @Test()
    void updateProfile() {
        mockMvc.perform(post("/profile/USERNAME")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "USERNAME")
                .param("firstName", "FIRST")
                .param("lastName", "LAST")
                .param("email", "different@email.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("profile"))
                .andExpect(model().attribute("updateProfile", hasProperty("email", is("different@email.com"))));
    }

    @SneakyThrows
    @Test()
    void updateProfileWithErrors() {
        mockMvc.perform(post("/profile/USERNAME")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("profile"))
                .andExpect(model().errorCount(3));
    }

    @SneakyThrows
    @Test()
    void updateProfileCannotChangeUserName() {
        assertThatThrownBy(() -> mockMvc.perform(post("/profile/USERNAME")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "DIFFERENT_NAME")
                .param("firstName", "FIRST")
                .param("lastName", "LAST")
                .param("email", "FIRST@LAST.COM"))
                .andExpect(status().isFound())
                .andExpect(view().name("profile"))
                .andExpect(model().errorCount(1)))
                .isInstanceOf(NestedServletException.class)
                .hasMessageContaining("Cannot change username for Profile");
    }
}
