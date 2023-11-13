package com.example.restauth;

import com.example.restauth.config.auth.JwtUtil;
import com.example.restauth.controller.LoginController;
import com.example.restauth.dto.auth.LoginRequest;
import com.example.restauth.dto.auth.LoginResponse;
import com.example.restauth.service.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

class LoginControllerTest {
  @Mock private UserDetailsServiceImpl userDetailsService;
  @Mock private AuthenticationManager authenticationManager;
  @Mock private JwtUtil jwtUtil;
  @InjectMocks private LoginController loginController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testLogin() throws Exception {

    LoginRequest loginRequest = new LoginRequest("username", "password");
    UserDetails userDetails = Mockito.mock(UserDetails.class);
    Mockito.when(userDetailsService.loadUserByUsername("username")).thenReturn(userDetails);
    Mockito.when(jwtUtil.generateToken(userDetails)).thenReturn("mockedToken");

    ResponseEntity<LoginResponse> responseEntity = loginController.login(loginRequest);

    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    Assertions.assertEquals("mockedToken", responseEntity.getBody().getAccessToken());

    Mockito.verify(authenticationManager, Mockito.times(1))
        .authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));
  }
}
