package org.example.nazorat_ishi_varsion_1;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/auth")
class AuthController {
    private List<User> users = new ArrayList<>();
    private VerificationService verificationService = new VerificationService();

    @PostMapping("/register")
    public List<String> register(@RequestParam String email, @RequestParam String password) {
        List<String> responseList = new ArrayList<>();
        if (users.stream().anyMatch(user -> user.getEmail().equals(email))) {
            responseList.add("User already exists!");
            return responseList;
        }
        String code = verificationService.generateCode();
        users.add(new User(email, password, code, "REGISTER"));
        responseList.add("User registered successfully! Please verify your email.");
        responseList.add("Verification code: " + code);
        return responseList;
    }

    @PostMapping("/verification")
    public List<String> verify(@RequestParam String email, @RequestParam String code) {
        List<String> responseList = new ArrayList<>();
        for (User user : users) {
            if (user.getEmail().equals(email) && user.getVerificationCode().equals(code)) {
                user.setStatus("ACTIVE");
                responseList.add("User activated successfully!");
                return responseList;
            }
        }
        responseList.add("Invalid verification code!");
        return responseList;
    }

    @PostMapping("/login")
    public List<String> login(@RequestParam String email, @RequestParam String password) {
        List<String> responseList = new ArrayList<>();
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                if (!user.getStatus().equals("ACTIVE")) {
                    responseList.add("User not activated!");
                    return responseList;
                }
                if (!user.getPassword().equals(password)) {
                    responseList.add("Invalid password!");
                    return responseList;
                }
                String code = verificationService.generateCode();
                user.setVerificationCode(code);
                responseList.add("Login verification code sent to email");
                responseList.add("Code: " + code);
                return responseList;
            }
        }
        responseList.add("User not registered!");
        return responseList;
    }
}
