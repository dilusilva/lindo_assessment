package com.example.restauth.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Log4j2
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Override
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    if ("root".equals(username)) {
      return new User(
          "root",
          "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
          Collections.emptyList());
    } else {
      log.warn("User not found with username: {}", username);
      throw new UsernameNotFoundException("User not found with username: " + username);
    }
  }
}
