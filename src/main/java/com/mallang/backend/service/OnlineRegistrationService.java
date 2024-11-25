package com.mallang.backend.service;

import com.mallang.backend.domain.OnlineRegistration;
import com.mallang.backend.repository.OnlineRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OnlineRegistrationService {

    private final OnlineRegistrationRepository registrationRepository;

    @Autowired
    public OnlineRegistrationService(OnlineRegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    /**
     * 온라인 접수 등록
     */
    public OnlineRegistration registerOnline(OnlineRegistration registration) {
        // 등록된 데이터를 저장
        return registrationRepository.save(registration);
    }

    /**
     * 모든 접수 내역 조회
     */
    public List<OnlineRegistration> getAllRegistrations() {
        // 데이터베이스에서 전체 조회
        return registrationRepository.findAll();
    }

    /**
     * 특정 접수 상세 조회
     */
    public OnlineRegistration getRegistrationDetails(Long id) {
        // ID를 사용해 데이터 검색
        return registrationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 접수 정보를 찾을 수 없습니다."));
    }

    /**
     * 접수 삭제
     */
    public void deleteRegistration(Long id) {
        // 데이터가 존재하는지 확인
        Optional<OnlineRegistration> registration = registrationRepository.findById(id);
        if (registration.isEmpty()) {
            throw new IllegalArgumentException("해당 ID의 접수 정보를 찾을 수 없습니다.");
        }
        // 데이터 삭제
        registrationRepository.deleteById(id);
    }
}