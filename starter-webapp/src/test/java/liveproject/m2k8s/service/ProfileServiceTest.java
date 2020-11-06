package liveproject.m2k8s.service;

import liveproject.m2k8s.data.Profile;
import liveproject.m2k8s.data.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class ProfileServiceTest {
    final private ProfileRepository profileRepository = Mockito.mock(ProfileRepository.class);

    private ProfileService profileService;

    @BeforeEach
    void setup() {
        profileService = new ProfileService(profileRepository);
        final Profile zasu = Profile.builder()
                .id(1L)
                .firstName("Zasu")
                .lastName("Pitts")
                .email("zasu@hollywood.com")
                .username("zasupitts")
                .password("changeme")
                .build();
        when(profileRepository.findByUsername("zasupitts")).thenReturn(zasu);
    }

    @Test
    void test_getProfile() {
        final UpdateProfile zasupitts = profileService.getProfile("zasupitts");
        assertThat(zasupitts.getFirstName()).isEqualTo("Zasu");
    }
}
