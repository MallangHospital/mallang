package com.mallang.backend.controller;

import com.mallang.backend.domain.OnlineRegistration;
import com.mallang.backend.service.OnlineRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registration")
public class OnlineRegistrationController {

    @Autowired
    private OnlineRegistrationService onlineRegistrationService;

    @PostMapping
    public String registerOnline(@RequestBody OnlineRegistration registration) {
        OnlineRegistration savedRegistration = onlineRegistrationService.registerOnline(registration);
        return "온라인 접수가 완료되었습니다. ID: " + savedRegistration.getId();
    }
}