package com.example.women_voice.service.impl;

import com.example.women_voice.exception.CustomException;
import com.example.women_voice.mapper.ProfileMapper;
import com.example.women_voice.model.domain.Profile;
import com.example.women_voice.model.domain.User;
import com.example.women_voice.model.dto.profile.ExpertResponse;
import com.example.women_voice.model.dto.profile.ProfileRequest;
import com.example.women_voice.model.dto.profile.ProfileResponse;
import com.example.women_voice.model.enums.Role;
import com.example.women_voice.repository.ProfileRepository;
import com.example.women_voice.repository.UserRepository;
import com.example.women_voice.service.AmazonService;
import com.example.women_voice.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProfileServiceImplTest {

    @InjectMocks
    private ProfileServiceImpl profileService;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProfileMapper profileMapper;

    @Mock
    private AuthService authService;

    @Mock
    private AmazonService amazonService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateProfile_ShouldThrowCustomException_WhenProfileNotFound() {
        // Arrange
        String token = "valid-token";
        ProfileRequest profileRequest = new ProfileRequest();
        MultipartFile file = mock(MultipartFile.class);

        User user = new User();

        when(authService.getUserFromToken(token)).thenReturn(user);
        when(profileRepository.findByUser(user)).thenReturn(Optional.empty());

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> profileService.updateProfile(token, profileRequest, file));
        assertEquals("Profile not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());

        verify(authService).getUserFromToken(token);
        verify(profileRepository).findByUser(user);
        verifyNoInteractions(amazonService, profileMapper);
    }

    @Test
    void getProfile_ShouldReturnProfileResponse_WhenProfileExists() {
        // Arrange
        String token = "valid-token";
        User user = new User();
        Profile profile = new Profile();
        ProfileResponse profileResponse = new ProfileResponse();

        when(authService.getUserFromToken(token)).thenReturn(user);
        when(profileRepository.findByUser(user)).thenReturn(Optional.of(profile));
        when(profileMapper.toProfileResponse(profile)).thenReturn(profileResponse);

        // Act
        ProfileResponse result = profileService.getProfile(token);

        // Assert
        assertEquals(profileResponse, result);
        verify(authService).getUserFromToken(token);
        verify(profileRepository).findByUser(user);
        verify(profileMapper).toProfileResponse(profile);
    }

    @Test
    void getProfile_ShouldThrowCustomException_WhenProfileNotFound() {
        // Arrange
        String token = "valid-token";
        User user = new User();

        when(authService.getUserFromToken(token)).thenReturn(user);
        when(profileRepository.findByUser(user)).thenReturn(Optional.empty());

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> profileService.getProfile(token));
        assertEquals("Profile not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());

        verify(authService).getUserFromToken(token);
        verify(profileRepository).findByUser(user);
        verifyNoInteractions(profileMapper);
    }

    @Test
    void getExperts_ShouldReturnListOfExpertResponses_WhenExpertsExist() {
        // Arrange
        int page = 0;
        int size = 5;
        PageRequest pageable = PageRequest.of(page, size);

        User expertUser = new User();
        expertUser.setRole(Role.EXPERT);
        Profile expertProfile = new Profile();
        expertUser.setProfile(expertProfile);

        List<User> expertUsers = List.of(expertUser);
        List<Profile> expertProfiles = List.of(expertProfile);
        List<ExpertResponse> expertResponses = List.of(new ExpertResponse());

        when(userRepository.findAllByRole(Role.EXPERT, pageable)).thenReturn(expertUsers);
        when(profileMapper.toExpertResponseList(expertProfiles)).thenReturn(expertResponses);

        // Act
        List<ExpertResponse> result = profileService.getExperts(page, size);

        // Assert
        assertEquals(expertResponses, result);
        verify(userRepository).findAllByRole(Role.EXPERT, pageable);
        verify(profileMapper).toExpertResponseList(expertProfiles);
    }

    @Test
    void getExperts_ShouldReturnEmptyList_WhenNoExpertsExist() {
        // Arrange
        int page = 0;
        int size = 5;
        PageRequest pageable = PageRequest.of(page, size);

        when(userRepository.findAllByRole(Role.EXPERT, pageable)).thenReturn(List.of());

        // Act
        List<ExpertResponse> result = profileService.getExperts(page, size);

        // Assert
        assertTrue(result.isEmpty());
        verify(userRepository).findAllByRole(Role.EXPERT, pageable);
        verify(profileMapper).toExpertResponseList(List.of());
    }
}
