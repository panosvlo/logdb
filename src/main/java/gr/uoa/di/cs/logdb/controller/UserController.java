package gr.uoa.di.cs.logdb.controller;

import gr.uoa.di.cs.logdb.dto.AuthenticationResponse;
import gr.uoa.di.cs.logdb.model.User;
import gr.uoa.di.cs.logdb.service.UserService;
import gr.uoa.di.cs.logdb.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User registeredUser = userService.registerUser(user);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        User authenticatedUser = userService.authenticateUser(user.getUsername(), user.getPassword());
        if (authenticatedUser != null) {
            final String jwt = jwtUtil.generateToken(authenticatedUser.getUsername());
            return ResponseEntity.ok(new AuthenticationResponse(jwt));
        } else {
            return ResponseEntity.status(401).body("Unauthorized");
        }
    }
}
