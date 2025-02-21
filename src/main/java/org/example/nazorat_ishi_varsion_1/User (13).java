package org.example.nazorat_ishi_varsion_1;

class User {
    private String email;
    private String password;
    private String verificationCode;
    private String status;

    public User(String email, String password, String verificationCode, String status) {
        this.email = email;
        this.password = password;
        this.verificationCode = verificationCode;
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getPassword() {
        return password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
