package rs.ftn.pma.services;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ftn.pma.dto.*;
import rs.ftn.pma.mappers.UserMapper;
import rs.ftn.pma.mappers.UserSettingsMapper;
import rs.ftn.pma.model.User;
import rs.ftn.pma.model.UserSettings;
import rs.ftn.pma.repository.UserRepository;
import rs.ftn.pma.repository.UserSettingsRepository;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserSettingsRepository userSettingsRepository;


    public User getUser(Long id) {
        return  userRepository.findOneById(id);
    }
    public UserResponse getUserByUsername(String username) {
        User user =  userRepository.findOneByUsername(username);
        return UserMapper.INSTANCE.mapToResponse(user);
    }
    public SettingsResponse getSettings(String username) {
        User user =  userRepository.findOneByUsername(username);
        return UserSettingsMapper.INSTANCE.mapToSettingsResponse(user.getSettings());
    }
    public UserProfileResponse getUserProfileByUsername(String username) {
        User user =  userRepository.findOneByUsername(username);
        UserSettings userSettings = user.getSettings();
        UserProfileResponse userProfile = UserMapper.INSTANCE.mapToProfileResponse(user);
        UserMapper.INSTANCE.mapToProfileResponse2(userSettings, userProfile);
        return  userProfile;
    }

    public UserResponse createUser(UserDto user) throws ConstraintViolationException {
        User newUser = UserMapper.INSTANCE.mapRequestToUser(user);
        newUser.setSettings(new UserSettings());
        newUser = userRepository.save(newUser);
        return UserMapper.INSTANCE.mapToResponse(newUser);
    }

    public ResponseEntity<?> createSetting(UserSettingRequest userSettingRequest) {
        User user = userRepository.findOneById(userSettingRequest.getUserId());
        if (user.getSettings() == null) {
            UserSettings userSettings = UserSettingsMapper.INSTANCE.mapToUserSettings(userSettingRequest);
            userSettings.setUser(user);
            userSettings = userSettingsRepository.save(userSettings);
            user.setSettings(userSettings);
            userRepository.save(user);
            return new ResponseEntity<>(UserSettingsMapper.INSTANCE.mapToUserSettingsResponse(userSettings), HttpStatus.OK);
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("message", "user already has settings");
        map.put("code", "1212");
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }


    public ResponseEntity<?> updateSettings(UserSettingRequest userSettingRequest, Long settingId) {
        UserSettings userSettings = userSettingsRepository.findOneById(settingId);
        UserSettingsMapper.INSTANCE.patchMapping(userSettings, userSettingRequest);
        userSettingsRepository.save(userSettings);
        return new ResponseEntity<>(UserSettingsMapper.INSTANCE.mapToUserSettingsResponse(userSettings), HttpStatus.OK);
    }
    public ResponseEntity<?> updateProfile(UserProfileResponse userProfile, String username) {
        System.out.println("U servisu  je");
        User user = userRepository.findOneByUsername(username);
        UserSettings userSettings = user.getSettings();
        if(user.getSettings() == null){
            System.out.println("null settings");
        }else {
            System.out.println("Nije null");
        }
        UserMapper.INSTANCE.patchMappingUser(user, userProfile);
        UserSettingsMapper.INSTANCE.patchMappingSettings(userSettings, userProfile);
        userRepository.save(user);
        System.out.println("Sacuvan");
       // userSettingsRepository.save(userSettings);
        HashMap<String, String> map = new HashMap<>();
        map.put("message", "Profile  is updated.");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    public ResponseEntity<?> updateReminder(SettingRequest  request, String username) {
        User user = userRepository.findOneByUsername(username);
        UserSettings userSettings = user.getSettings();
        UserSettingsMapper.INSTANCE.patchMappingSettingsReminder(userSettings, request);
        userSettingsRepository.save(userSettings);
        HashMap<String, String> map = new HashMap<>();
        map.put("message", "Reminder has been updated.");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findOneByUsername(s);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }

}
