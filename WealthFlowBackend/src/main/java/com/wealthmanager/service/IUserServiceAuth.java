package com.wealthmanager.service;

import com.wealthmanager.dto.RegisterRequestDTO;

public interface IUserServiceAuth {
    String register(RegisterRequestDTO request);
}
