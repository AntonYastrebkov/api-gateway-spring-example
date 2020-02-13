package com.example.microservice.foo.controller;

import com.example.microservice.foo.dao.Token;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MockController {
    @GetMapping("/token")
    public ResponseEntity<Token> getToken() {
        return ResponseEntity.ok(new Token()
                .setAccessToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoidXNlcl8xMjMiLCJ1c2VyX25hbWUiOiJ1c2VyIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImV4cCI6MTU4MTA5MjAwNiwiYXV0aG9yaXRpZXMiOlsiVVNFUiJdLCJqdGkiOiIwZTdhODdlMi1mZTJlLTQyYjctYmIwMy04NWMyMDY5Y2U0MWIiLCJjbGllbnRfaWQiOiJjbGllbnRQYXNzd29yZCJ9.SdVHbH_JuFTThqeej_CIHTGkXsxAe1DThPloAxBEqAQ")
                .setTokenType("Bearer")
                .setScope("read write")
                .setRefreshToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoidXNlcl8xMjMiLCJ1c2VyX25hbWUiOiJ1c2VyIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImV4cCI6MTU4MTA5MjAwNiwiYXV0aG9yaXRpZXMiOlsiVVNFUiJdLCJqdGkiOiIwZTdhODdlMi1mZTJlLTQyYjctYmIwMy04NWMyMDY5Y2U0MWIiLCJjbGllbnRfaWQiOiJjbGllbnRQYXNzd29yZCJ9.SdVHbH_JuFTThqeej_CIHTGkXsxAe1DThPloAxBEqAQ"));

    }

    @GetMapping("/right")
    public ResponseEntity<Boolean> right(String token) {
        return ResponseEntity.ok(true);
    }

    @GetMapping("/wrong")
    public ResponseEntity<Boolean> wrong(String token) {
        return ResponseEntity.ok(false);
    }

    @GetMapping("/check")
    public ResponseEntity<String> checkToken(String token) {
        System.out.println("Checking token... " + token);
        if ("right".equals(token)) {
            return ResponseEntity.ok("true");
        } else {
            return ResponseEntity.ok("false");
        }
    }
}
