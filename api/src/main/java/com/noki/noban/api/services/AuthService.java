package com.noki.noban.api.services;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.noki.noban.api.dto.request.LoginRequest;
import com.noki.noban.api.dto.request.RegisterRequest;
import com.noki.noban.api.dto.response.JwtResponse;
import com.noki.noban.api.models.RoleModel;
import com.noki.noban.api.models.UserModel;
import com.noki.noban.api.repository.RoleRepository;
import com.noki.noban.api.repository.UserRepository;
import com.noki.noban.api.security.jwt.JwtService;
import com.noki.noban.api.security.user.CustomUserDetails;

@Service
public class AuthService {
    
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(
            UserRepository userRepository, 
            RoleRepository roleRepository, 
            PasswordEncoder passwordEncoder, 
            JwtService jwtService, 
            AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public JwtResponse register(RegisterRequest request) {
        RoleModel userRole = roleRepository.findByName(RoleModel.RoleType.ROLE_USER);

        UserModel newUser = new UserModel(
            request.name(),
            request.email(),
            passwordEncoder.encode(request.password()),
            List.of(userRole)
        );

        userRepository.save(newUser);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                request.email(),
                request.password()
        );

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();  
			
        return new JwtResponse(jwtService.generateTokenResponse(userDetails.getUsername()));
        
    }

	public JwtResponse login(LoginRequest request) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				request.email(),
				request.password()			
        );

		Authentication authentication = authenticationManager.authenticate(authenticationToken);

		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();  
			
		return new JwtResponse(jwtService.generateTokenResponse(userDetails.getUsername()));
	}
}
