package com.mallang.backend.service;

import com.mallang.backend.domain.OnlineRegistration;
import com.mallang.backend.repository.OnlineRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OnlineRegistrationService {

    @Autowired
    private OnlineRegistrationRepository onlineRegistrationRepository;

    public OnlineRegistration registerOnline(OnlineRegistration registration) {
        return onlineRegistrationRepository.save(registration);
    }
}