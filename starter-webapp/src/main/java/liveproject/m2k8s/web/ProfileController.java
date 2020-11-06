package liveproject.m2k8s.web;

import liveproject.m2k8s.service.NewProfile;
import liveproject.m2k8s.service.ProfileService;
import liveproject.m2k8s.service.UpdateProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@Slf4j
@RequestMapping("/profile")
public class ProfileController {

    public static final String PROFILE = "profile";
    public static final String REGISTER_FORM = "registerForm";
    public static final String PROFILE_FORM = "profile";
    private ProfileService profileService;

    @Value("${images.directory:/tmp}")
    private String uploadFolder;

    @Value("classpath:ghost.jpg")
    private Resource defaultImage;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping(value = "/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute(PROFILE, new NewProfile());
        return REGISTER_FORM;
    }

    @PostMapping(value = "/register")
    @Transactional
    public String processRegistration(
            @Valid NewProfile newProfile,
            Errors errors) {
        if (errors.hasErrors()) {
            return REGISTER_FORM;
        }

        profileService.save(newProfile);
        return "redirect:/profile/" + newProfile.getUsername();
    }

    @GetMapping(value = "/{username}")
    public String showProfile(@PathVariable String username, Model model) {
        log.debug("Reading model for: " + username);
        final UpdateProfile updateProfile = profileService.getProfile(username);
        model.addAttribute(PROFILE, updateProfile);
        return PROFILE_FORM;
    }

    @PostMapping(value = "/{username}")
    @Transactional
    public String updateProfile(@PathVariable String username, @ModelAttribute UpdateProfile updateProfile, Model model) {
        if (!username.equals(updateProfile.getUsername())) {
            throw new RuntimeException("Cannot change username for Profile");
        }

        log.debug("Updating model for: " + username);
        profileService.update(updateProfile);
        model.addAttribute(PROFILE, updateProfile);
        return PROFILE_FORM;
    }
}
