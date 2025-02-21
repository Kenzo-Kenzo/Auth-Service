package org.example.nazorat_ishi_varsion_1;

import java.util.Random;

class VerificationService {
    private Random random = new Random();

    public String generateCode() {
        return String.valueOf(100000 + random.nextInt(900000));
    }
}
