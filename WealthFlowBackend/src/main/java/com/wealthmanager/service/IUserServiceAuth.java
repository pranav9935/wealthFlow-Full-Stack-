package com.wealthmanager.service;

import com.wealthmanager.dto.RegisterRequestDTO;
import com.wealthmanager.entity.User;

public interface IUserServiceAuth {
    String register(RegisterRequestDTO request);
      User findByEmail(String email);
}
