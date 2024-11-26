package com.example.women_voice.mapper.impl;

import com.example.women_voice.model.domain.MyFile;
import com.example.women_voice.model.domain.Profile;
import com.example.women_voice.model.domain.User;
import com.example.women_voice.model.dto.profile.ExpertResponse;
import com.example.women_voice.model.dto.profile.ProfileRequest;
import com.example.women_voice.model.dto.profile.ProfileResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProfileMapperImplTest {

    private ProfileMapperImpl profileMapper;

    @BeforeEach
    void setUp() {
        profileMapper = new ProfileMapperImpl();
    }

    @Test
    void toProfile_ShouldMapProfileRequestToProfile() {
        // Arrange
        ProfileRequest profileRequest = new ProfileRequest();
        profileRequest.setFullName("Test User");
        profileRequest.setBio("Test Bio");
        profileRequest.setLocation("Test Location");
        profileRequest.setEducationLevel("Bachelor");

        MyFile file = new MyFile();
        file.setFileName("profile-image.jpg");
        file.setPath("https://example.com/images/profile-image.jpg");

        Profile profile = new Profile();

        // Act
        Profile result = profileMapper.toProfile(profile, profileRequest, file);

        // Assert
        assertNotNull(result, "Profile should not be null");
        assertEquals(profileRequest.getFullName(), result.getFullName(), "Full name should match the input");
        assertEquals(profileRequest.getBio(), result.getBio(), "Bio should match the input");
        assertEquals(profileRequest.getLocation(), result.getLocation(), "Location should match the input");
        assertEquals(profileRequest.getEducationLevel(), result.getEducationLevel(), "Education level should match the input");
        assertEquals(file, result.getImage(), "Image file should match the input file");
    }

    @Test
    void toProfileResponse_ShouldMapProfileToProfileResponse() {
        // Arrange
        Profile profile = new Profile();
        profile.setFullName("Test User");
        profile.setLocation("Test Location");
        profile.setEducationLevel("Bachelor");
        profile.setBio("Test Bio");

        MyFile file = new MyFile();
        file.setFileName("profile-image.jpg");
        file.setPath("https://example.com/images/profile-image.jpg");
        profile.setImage(file);

        // User object mock-up (if needed)
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user.setPassword("password123");
        profile.setUser(user);

        // Act
        ProfileResponse result = profileMapper.toProfileResponse(profile);

        // Assert
        assertNotNull(result, "ProfileResponse should not be null");
        assertEquals(profile.getFullName(), result.getFullName(), "Full name should match the profile's full name");
        assertEquals(profile.getLocation(), result.getLocation(), "Location should match the profile's location");
        assertEquals(profile.getEducationLevel(), result.getEducationLevel(), "Education level should match the profile's education level");
        assertEquals(profile.getBio(), result.getBio(), "Bio should match the profile's bio");
        assertEquals(profile.getImage().getPath(), result.getAvatarUrl(), "Avatar URL should match the profile's image path");
    }

    @Test
    void toExpertResponse_ShouldMapProfileToExpertResponse() {
        // Arrange
        Profile profile = new Profile();
        profile.setFullName("Expert User");
        profile.setEducationLevel("PhD");

        MyFile file = new MyFile();
        file.setFileName("expert-image.jpg");
        file.setPath("https://example.com/images/expert-image.jpg");
        profile.setImage(file);

        // Act
        ExpertResponse result = profileMapper.toExpertResponse(profile);

        // Assert
        assertNotNull(result, "ExpertResponse should not be null");
        assertEquals(profile.getFullName(), result.getName(), "Expert name should match the profile's full name");
        assertEquals(profile.getImage().getPath(), result.getImagePath(), "Image path should match the profile's image path");
        assertEquals(profile.getEducationLevel(), result.getEducationalLevel(), "Educational level should match the profile's education level");
    }

    @Test
    void toExpertResponseList_ShouldMapListOfProfilesToListOfExpertResponses() {
        // Arrange
        Profile profile1 = new Profile();
        profile1.setFullName("Expert 1");
        profile1.setEducationLevel("Master");

        Profile profile2 = new Profile();
        profile2.setFullName("Expert 2");
        profile2.setEducationLevel("PhD");

        List<Profile> profiles = List.of(profile1, profile2);

        // Act
        List<ExpertResponse> result = profileMapper.toExpertResponseList(profiles);

        // Assert
        assertNotNull(result, "Response list should not be null");
        assertEquals(profiles.size(), result.size(), "Size of response list should match the size of profiles list");
        assertEquals(profile1.getFullName(), result.get(0).getName(), "First expert's name should match the first response");
        assertEquals(profile2.getFullName(), result.get(1).getName(), "Second expert's name should match the second response");
    }
}
