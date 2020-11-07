package liveproject.m2k8s.config;

import liveproject.m2k8s.service.ProfileService;
import liveproject.m2k8s.service.UpdateProfile;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

import static org.mockito.Mockito.when;

@Configuration
public class TestWebConfig {
    @Bean
    public ProfileService profileService() {
        final ProfileService mock = Mockito.mock(ProfileService.class);
        final Optional<UpdateProfile> zasu = Optional.of(UpdateProfile.builder()
                .firstName("Zasu")
                .lastName("Pitts")
                .email("zasupitts@hollywood.com")
                .username("zasupitts")
                .build());
        when(mock.getProfile("zasupitts")).thenReturn(zasu);
        return mock;
    }
}
