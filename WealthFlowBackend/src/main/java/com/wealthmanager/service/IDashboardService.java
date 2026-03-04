package com.wealthmanager.service;

import com.wealthmanager.dto.DashboardResponseDTO;
import com.wealthmanager.entity.User;

public interface IDashboardService {

    DashboardResponseDTO getDashboard(User user);

}