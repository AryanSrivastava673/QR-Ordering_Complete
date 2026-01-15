package com.aryansrivastava.qrOrdering.QrOrdering.service;

import com.aryansrivastava.qrOrdering.QrOrdering.dto.AdminLoginRequestDTO;
import com.aryansrivastava.qrOrdering.QrOrdering.model.User;
import com.aryansrivastava.qrOrdering.QrOrdering.repository.UserRepository;
import com.aryansrivastava.qrOrdering.QrOrdering.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public Optional<String> authenticate(AdminLoginRequestDTO request) {
        Optional<User> userOpt = userRepository.findByUsername(request.getUsername());
        if (userOpt.isEmpty()) return Optional.empty();

        User user = userOpt.get();
        // check role 'ADMIN' and active
        if (!"ADMIN".equalsIgnoreCase(user.getRole()) || !user.isActive()) {
            return Optional.empty();
        }

        // If stored password looks like bcrypt (starts with $2a$" or "$2b$"), use encoder, else compare raw
        String stored = user.getPassword();
        boolean matches;
        if (stored != null && (stored.startsWith("$2a$") || stored.startsWith("$2b$") || stored.startsWith("$2y$"))) {
            matches = passwordEncoder.matches(request.getPassword(), stored);
        } else {
            matches = request.getPassword().equals(stored);
        }

        if (!matches) return Optional.empty();

        String token = jwtUtil.generateToken(user.getUsername());
        return Optional.of(token);
    }
}
