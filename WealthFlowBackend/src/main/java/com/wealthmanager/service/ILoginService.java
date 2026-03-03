package com.wealthmanager.service;

import com.wealthmanager.dto.LoginRequestDTO;

public interface ILoginService {
    String login(LoginRequestDTO request);

}
