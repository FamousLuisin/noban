package com.noki.noban.api.unit.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.noki.noban.api.dto.internal.AuthResult;
import com.noki.noban.api.dto.request.LoginRequest;
import com.noki.noban.api.dto.request.RegisterRequest;
import com.noki.noban.api.mocks.RoleMock;
import com.noki.noban.api.mocks.UserDetailsMock;
import com.noki.noban.api.models.RoleModel;
import com.noki.noban.api.models.UserModel;
import com.noki.noban.api.models.RoleModel.RoleType;
import com.noki.noban.api.repository.RoleRepository;
import com.noki.noban.api.repository.UserRepository;
import com.noki.noban.api.security.jwt.JwtService;
import com.noki.noban.api.security.jwt.TokenType;
import com.noki.noban.api.security.user.CustomUserDetails;
import com.noki.noban.api.services.AuthService;

@ExtendWith(SpringExtension.class)
public class AuthServiceTest {

    @InjectMocks
    AuthService authService;
    
    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private Authentication authentication;

    @Captor
    private ArgumentCaptor<UsernamePasswordAuthenticationToken> authenticationCaptor;

    @Captor
    private ArgumentCaptor<UserModel> userModelCaptor;

    private UserDetailsMock userDetailsMock = new UserDetailsMock();

    private RoleMock roleMock = new RoleMock();

    @BeforeEach
    void setUp(){
        RoleModel roleModel = roleMock.getRoleUser();
        CustomUserDetails userDetails = userDetailsMock.getUserDetails(roleModel);

        Mockito.when(roleRepository.findByName(RoleType.ROLE_USER)).thenReturn(roleModel);
        Mockito.when(passwordEncoder.encode(anyString())).thenReturn("senhaCriptografada123!");
        Mockito.when(authenticationManager.authenticate(any())).thenReturn(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
        Mockito.when(jwtService.generateToken(anyString(), eq(TokenType.ACCESS))).thenReturn("access");
        Mockito.when(jwtService.generateToken(anyString(), eq(TokenType.REFRESH))).thenReturn("refresh");
        Mockito.when(jwtService.getAccessExpiresIn()).thenReturn(900L);
        Mockito.when(jwtService.getSubject(anyString(), eq(TokenType.REFRESH))).thenReturn("teste@email");
    }

    @Test
    void login_withValidCredentials() {
        AuthResult result = authService.login(new LoginRequest("teste@email", "senha123!"));

        Mockito.verify(authenticationManager).authenticate(authenticationCaptor.capture());
        Mockito.verify(authentication).getPrincipal();
        Mockito.verify(jwtService, times(1)).generateToken(eq("teste@email"), eq(TokenType.ACCESS));
        Mockito.verify(jwtService, times(1)).generateToken(eq("teste@email"), eq(TokenType.REFRESH));
        Mockito.verify(jwtService).getAccessExpiresIn();

        UsernamePasswordAuthenticationToken auth = authenticationCaptor.getValue();

        assertNotNull(result);
        assertEquals("access", result.accessToken());
        assertEquals("refresh", result.refreshToken());
        assertEquals(900L, result.expiresIn());
        assertEquals("teste@email", auth.getPrincipal());
        assertEquals("senha123!", auth.getCredentials());
    }

    @Test
    void register_withValidCredentials() {
        AuthResult result = authService.register(new RegisterRequest("teste", "teste@email", "senha123!"));
        
        Mockito.verify(authenticationManager, times(1)).authenticate(authenticationCaptor.capture());
        Mockito.verify(authentication, times(1)).getPrincipal();
        Mockito.verify(jwtService, times(1)).generateToken(eq("teste@email"), eq(TokenType.ACCESS));
        Mockito.verify(jwtService, times(1)).generateToken(eq("teste@email"), eq(TokenType.REFRESH));
        Mockito.verify(jwtService, times(1)).getAccessExpiresIn();
        Mockito.verify(userRepository, times(1)).save(userModelCaptor.capture());
        Mockito.verify(roleRepository, times(1)).findByName(RoleType.ROLE_USER);

        UsernamePasswordAuthenticationToken auth = authenticationCaptor.getValue();
        UserModel savedUser = userModelCaptor.getValue();

        assertNotNull(result);
        assertEquals("access", result.accessToken());
        assertEquals("refresh", result.refreshToken());
        assertEquals(900L, result.expiresIn());
        assertEquals("teste@email", auth.getPrincipal());
        assertEquals("senha123!", auth.getCredentials());
        assertEquals("teste", savedUser.getName());
        assertEquals("teste@email", savedUser.getEmail());
        assertEquals("senhaCriptografada123!", savedUser.getPassword());
    }

    @Test
    void refreshToken_withValidCredentials() {
        AuthResult result = authService.refreshToken("token");

        Mockito.verify(jwtService, times(1)).generateToken(eq("teste@email"), eq(TokenType.ACCESS));
        Mockito.verify(jwtService, times(1)).generateToken(eq("teste@email"), eq(TokenType.REFRESH));
        Mockito.verify(jwtService, times(1)).getAccessExpiresIn();
        Mockito.verify(jwtService, times(1)).getSubject(eq("token"), eq(TokenType.REFRESH));

        assertNotNull(result);
        assertEquals("access", result.accessToken());
        assertEquals("refresh", result.refreshToken());
        assertEquals(900L, result.expiresIn());
    }
}
