package liveproject.m2k8s.service;

import liveproject.m2k8s.data.Profile;
import liveproject.m2k8s.data.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class ProfileService {
    private ProfileRepository profileRepository;

    @Autowired
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Optional<UpdateProfile> getProfile(String username) {
        final Profile profile = profileRepository.findByUsername(username);
        return profile == null ? Optional.empty() : Optional.of(new UpdateProfile(profile.getUsername(),
                profile.getFirstName(),
                profile.getLastName(),
                profile.getEmail()));
    }

    public void save(NewProfile newProfile) {
        Profile profile = profileRepository.findByUsername(newProfile.getUsername());
        if (profile == null) {
            profile = new Profile(newProfile.getUsername(),
                    newProfile.getPassword(),
                    newProfile.getFirstName(),
                    newProfile.getLastName(),
                    newProfile.getEmail());
            profileRepository.save(profile);
        }
    }

    public void update(UpdateProfile updateProfile) {
        final Profile dbProfile = profileRepository.findByUsername(updateProfile.getUsername());
        if (dbProfile != null) {
            boolean dirty = false;
            if (!StringUtils.isEmpty(updateProfile.getEmail())
                    && !updateProfile.getEmail().equals(dbProfile.getEmail())) {
                dbProfile.setEmail(updateProfile.getEmail());
                dirty = true;
            }
            if (!StringUtils.isEmpty(updateProfile.getFirstName())
                    && !updateProfile.getFirstName().equals(dbProfile.getFirstName())) {
                dbProfile.setFirstName(updateProfile.getFirstName());
                dirty = true;
            }
            if (!StringUtils.isEmpty(updateProfile.getLastName())
                    && !updateProfile.getLastName().equals(dbProfile.getLastName())) {
                dbProfile.setLastName(updateProfile.getLastName());
                dirty = true;
            }
            if (dirty) {
                profileRepository.save(dbProfile);
            }
        }
    }
}
