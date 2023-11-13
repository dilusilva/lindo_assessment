package com.example.restauth.controller;

import com.example.restauth.config.auth.JwtUtil;
import com.example.restauth.dto.auth.LoginRequest;
import com.example.restauth.dto.auth.LoginResponse;
import com.example.restauth.service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class LoginController {

  private final UserDetailsServiceImpl userDetailsService;
  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;

  /**
   * Authenticate user
   *
   * @param request given login request
   * @return {@link LoginResponse} object with access token
   * @throws Exception
   */
  @PostMapping(value = "/authenticate")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) throws Exception {

    authenticate(request.getUsername(), request.getPassword());

    final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

    final LoginResponse response =
        LoginResponse.builder().accessToken(jwtUtil.generateToken(userDetails)).build();

    return ResponseEntity.ok(response);
  }

  private void authenticate(final String username, final String password) throws Exception {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(username, password));
    } catch (DisabledException e) {
      throw new Exception("USER_DISABLED", e);
    } catch (BadCredentialsException e) {
      throw new Exception("INVALID_CREDENTIALS", e);
    }
  }
}
