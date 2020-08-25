package rs.ftn.pma.controller;

import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import rs.ftn.pma.dto.*;
import rs.ftn.pma.model.User;
import rs.ftn.pma.services.UserService;
import rs.ftn.pma.utils.JwtUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping(value = "users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @GetMapping(value="/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        try {
            User user = userService.getUser(id);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value="")
    public ResponseEntity<?> createUser(@RequestBody UserDto user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.OK);
    }

    @PostMapping(value="/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest credentials) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword()));
        }
        catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userService
                .loadUserByUsername(credentials.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(jwt));
    }

    @PostMapping(value = "/settings")
    public ResponseEntity<?> createSettings(@RequestBody UserSettingRequest userSettingRequest) {
        try {
            return userService.createSetting(userSettingRequest);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping(value = "/settings/{id}")
    public ResponseEntity<?> updateSettings(@RequestBody UserSettingRequest userSettingRequest, @PathVariable("id") Long settingId) {
        try {
            return userService.updateSettings(userSettingRequest, settingId);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PatchMapping(value = "/updateProfile")
    public ResponseEntity<?> updateProfile(@RequestBody UserProfileResponse userProfile, @RequestHeader("Authorization") String token) {
        System.out.println("U controleru je");
        try {
            String username = jwtTokenUtil.extractUsername(token.substring(7));
            return userService.updateProfile(userProfile, username);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PatchMapping(value = "/updateReminder")
    public ResponseEntity<?> updateReminder(@RequestBody SettingRequest request, @RequestHeader("Authorization") String token) {
        try {
            String username = jwtTokenUtil.extractUsername(token.substring(7));
            return userService.updateReminder(request, username);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/getLogged")
    public ResponseEntity<?> getLoggedUser(@RequestHeader("Authorization") String token) {
        String username = jwtTokenUtil.extractUsername(token.substring(7));
        UserResponse loggedUser = userService.getUserByUsername(username);
        System.out.println("Username  je "+loggedUser.getUsername());
        return new ResponseEntity<>(loggedUser,HttpStatus.OK);
    }

    @GetMapping(value = "/profile")
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String token) {
        String username = jwtTokenUtil.extractUsername(token.substring(7));
        UserProfileResponse loggedUser = userService.getUserProfileByUsername(username);
        System.out.println("Username  je "+loggedUser.getUsername());
        return new ResponseEntity<>(loggedUser,HttpStatus.OK);
    }

    @GetMapping(value = "/waterReminder")
    public ResponseEntity<?> getWaterReminder(@RequestHeader("Authorization") String token) {
        String username = jwtTokenUtil.extractUsername(token.substring(7));
        SettingsResponse settings = userService.getSettings(username);
        return new ResponseEntity<>(settings,HttpStatus.OK);
    }
}
