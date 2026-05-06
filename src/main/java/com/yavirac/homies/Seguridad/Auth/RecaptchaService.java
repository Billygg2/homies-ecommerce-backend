package com.yavirac.homies.Seguridad.Auth;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;


@Service
@RequiredArgsConstructor
public class RecaptchaService {
    private static final String RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";
    private static final String SECRET_KEY = "6Le4eKcqAAAAAM9SLfkzztxNO8TZUT3vEqhViOHt";

    private RestTemplate restTemplate;
    
        @PostConstruct
        public void init() {
            restTemplate = new RestTemplate();
    }

    public boolean validateCaptcha(String captchaResponse) {
        if (captchaResponse == null || captchaResponse.isEmpty()) {
            return false;
        }
        String params = "?secret=" + SECRET_KEY + "&response=" + captchaResponse;
        String uri = RECAPTCHA_VERIFY_URL + params;
        
        try {
            RecaptchaResponse response = restTemplate.exchange(
                uri,
                HttpMethod.POST,
                null,
                RecaptchaResponse.class
            ).getBody();

            return response != null && response.isSuccess();
        } catch (Exception e) {
            return false;
        }
    }
}