package liveproject.m2k8s.service;

import liveproject.m2k8s.data.Profile;
import liveproject.m2k8s.data.ProfileRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class ProfileServiceTest {
    private ProfileRepository profileRepository = Mockito.mock(ProfileRepository.class);
    private ProfileService profileService;

    @Before
    public void setup() {
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
    public void test_getProfile() {
        final UpdateProfile zasupitts = profileService.getProfile("zasupitts");
        assertEquals("Zasu", zasupitts.getFirstName());
    }
}
