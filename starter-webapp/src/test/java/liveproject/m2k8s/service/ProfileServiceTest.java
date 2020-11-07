package liveproject.m2k8s.service;

import liveproject.m2k8s.data.Profile;
import liveproject.m2k8s.data.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ProfileServiceTest {
    private Profile zasu;
    private NewProfile newProfile;
    private UpdateProfile updateProfile;
    private ProfileRepository profileRepository;
    private ProfileService profileService;

    @BeforeEach
    void setup() {
        zasu = Profile.builder()
                .id(1L)
                .firstName("Zasu")
                .lastName("Pitts")
                .email("zasu@hollywood.com")
                .username("zasupitts")
                .password("changeme")
                .build();
        newProfile = new NewProfile("USERNAME", "PASSWORD", "FIRST", "LAST", "FIRST@LAST.COM");
        updateProfile = new UpdateProfile(zasu.getUsername(), newProfile.getFirstName(), newProfile.getLastName(), newProfile.getEmail());

        profileRepository = mock(ProfileRepository.class);
        profileService = new ProfileService(profileRepository);
        when(profileRepository.findByUsername("zasupitts")).thenReturn(zasu);
    }

    @Test
    void getProfile() {
        final Optional<UpdateProfile> zasupitts = profileService.getProfile("zasupitts");
        assertThat(zasupitts.isPresent()).isTrue();
        assertThat(zasupitts.get().getFirstName()).isEqualTo("Zasu");
    }

    @Test
    void saveExisting() {
        final NewProfile newProfile = new NewProfile(zasu.getUsername(),
                zasu.getPassword(),
                zasu.getFirstName(),
                zasu.getLastName(),
                zasu.getEmail());
        profileService.save(newProfile);

        verify(profileRepository, times(0)).save(any());
    }

    @Test
    void save() {
        final Profile profile = Profile.builder()
                .username(newProfile.getUsername())
                .password(newProfile.getPassword())
                .firstName(newProfile.getFirstName())
                .lastName(newProfile.getLastName())
                .email(newProfile.getEmail())
                .build();
        profileService.save(newProfile);
        verify(profileRepository, times(1)).save(profile);
    }

    @Test
    void updateNonExisting() {
        final UpdateProfile updateProfile = new UpdateProfile(newProfile.getUsername(),
                newProfile.getFirstName(),
                newProfile.getLastName(),
                newProfile.getEmail());
        profileService.update(updateProfile);
        verify(profileRepository, times(0)).save(any());
    }

    @Test
    void updateUnchanged() {
        final UpdateProfile updateProfile = new UpdateProfile(zasu.getUsername(),
                zasu.getFirstName(),
                zasu.getLastName(),
                zasu.getEmail());
        profileService.update(updateProfile);
        verify(profileRepository, times(0)).save(any());
    }

    @Test
    void update() {
        final Profile profile = Profile.builder()
                .id(zasu.getId())
                .username(updateProfile.getUsername())
                .password(zasu.getPassword())
                .firstName(updateProfile.getFirstName())
                .lastName(updateProfile.getLastName())
                .email(updateProfile.getEmail())
                .build();
        profileService.update(updateProfile);
        verify(profileRepository).save(profile);
    }
}
