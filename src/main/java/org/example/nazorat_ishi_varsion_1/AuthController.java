package org.example.nazorat_ishi_varsion_1;

import org.apache.catalina.Group;
import org.apache.catalina.Role;
import org.apache.catalina.User;
import org.apache.catalina.UserDatabase;
import org.example.nazorat_ishi_varsion_1.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private EmailService emailService;

    private List<User> users = new ArrayList<>();

    @PostMapping("/register")
    public String register(@RequestParam String email, @RequestParam String password) {
        for (User user : users) {
            if (user.getName().equals(email)) {
                return "Bu email allaqachon ro‘yxatdan o‘tgan!";
            }
        }

        String verificationCode = generateVerificationCode();
        User newUser = new User() {
            @Override
            public boolean equals(Object another) {
                return false;
            }

            @Override
            public String toString() {
                return "";
            }

            @Override
            public int hashCode() {
                return 0;
            }

            @Override
            public String getName() {
                return "";
            }

            @Override
            public String getFullName() {
                return "";
            }

            @Override
            public void setFullName(String s) {

            }

            @Override
            public Iterator<Group> getGroups() {
                return null;
            }

            @Override
            public String getPassword() {
                return "";
            }

            @Override
            public void setPassword(String s) {

            }

            @Override
            public Iterator<Role> getRoles() {
                return null;
            }

            @Override
            public UserDatabase getUserDatabase() {
                return null;
            }

            @Override
            public String getUsername() {
                return "";
            }

            @Override
            public void setUsername(String s) {

            }

            @Override
            public void addGroup(Group group) {

            }

            @Override
            public void addRole(Role role) {

            }

            @Override
            public boolean isInGroup(Group group) {
                return false;
            }

            @Override
            public boolean isInRole(Role role) {
                return false;
            }

            @Override
            public void removeGroup(Group group) {

            }

            @Override
            public void removeGroups() {

            }

            @Override
            public void removeRole(Role role) {

            }

            @Override
            public void removeRoles() {

            }
        };
        users.add(newUser);

        emailService.sendEmail(email, "Tasdiqlash kodi", "Sizning tasdiqlash kodingiz: " + verificationCode);

        return "Tasdiqlash kodi emailga yuborildi: " + email;
    }

    @PostMapping("/verify")
    public String verify(@RequestParam String email, @RequestParam String code) {
        for (User user : users) {
            if (user.getName().equals(email)) {
                if (user.getUserDatabase().equals(code)) {
                    user.setUsername(String.valueOf(true));
                    return "success!";
                } else {
                    return "error verifitycode!";
                }
            }
        }
        return "this email not found!";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        for (User user : users) {
            if (email.equals(user.getName())) {
                if (user.isInRole()) {
                    if (user.getPassword().equals(password)) {
                        return "Muvaffaqiyatli tizimga kirdingiz: " + email;
                    } else {
                        return "Noto‘g‘ri parol!";
                    }
                } else {
                    return "Email tasdiqlanmagan!";
                }
            }
        }
        return "Foydalanuvchi topilmadi!";
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}
