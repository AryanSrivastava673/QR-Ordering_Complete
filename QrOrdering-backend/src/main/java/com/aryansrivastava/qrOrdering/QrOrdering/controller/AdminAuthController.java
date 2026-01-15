package com.aryansrivastava.qrOrdering.QrOrdering.controller;

import com.aryansrivastava.qrOrdering.QrOrdering.dto.AdminLoginRequestDTO;
import com.aryansrivastava.qrOrdering.QrOrdering.dto.AdminLoginResponseDTO;
import com.aryansrivastava.qrOrdering.QrOrdering.security.JwtUtil;
import com.aryansrivastava.qrOrdering.QrOrdering.service.AdminAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminAuthController {

    @Autowired
    private AdminAuthService adminAuthService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<AdminLoginResponseDTO> login(@Valid @RequestBody AdminLoginRequestDTO request) {
        Optional<String> tokenOpt = adminAuthService.authenticate(request);
        AdminLoginResponseDTO resp = new AdminLoginResponseDTO();
        if (tokenOpt.isEmpty()) {
            resp.setSuccess(false);
            resp.setError("Invalid credentials");
            return ResponseEntity.ok(resp);
        }

        String token = tokenOpt.get();
        // set cookie (HttpOnly)
        ResponseCookie cookie = ResponseCookie.from("ADMIN_TOKEN", token)
                .httpOnly(true)
                .path("/")
                .maxAge(Duration.ofDays(1))
                .sameSite("Lax")
                .build();

        resp.setSuccess(true);
        return ResponseEntity.ok().header("Set-Cookie", cookie.toString()).body(resp);
    }

    // Check session via cookie ADMIN_TOKEN
    @GetMapping("/session")
    public ResponseEntity<Map<String, Object>> session(@CookieValue(value = "ADMIN_TOKEN", required = false) String token) {
        Map<String, Object> body = new HashMap<>();
        if (token != null && jwtUtil.validateToken(token)) {
            body.put("authenticated", true);
            body.put("username", jwtUtil.getUsernameFromToken(token));
        } else {
            body.put("authenticated", false);
        }
        return ResponseEntity.ok(body);
    }

    // Logout clears the cookie
    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout() {
        ResponseCookie cookie = ResponseCookie.from("ADMIN_TOKEN", "")
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();
        Map<String, Object> body = Map.of("success", true);
        return ResponseEntity.ok().header("Set-Cookie", cookie.toString()).body(body);
    }
}
