package org.sale.project.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReCaptchaService {
    
    @Value("${google.recaptcha.secret}")
    private String recaptchaSecret;
    
    private static final String GOOGLE_RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";
    
    public boolean verifyRecaptcha(String recaptchaResponse) {
        RestTemplate restTemplate = new RestTemplate();
        
        String params = String.format("?secret=%s&response=%s", recaptchaSecret, recaptchaResponse);
        
        ReCaptchaResponse apiResponse = restTemplate.getForObject(
            GOOGLE_RECAPTCHA_VERIFY_URL + params, ReCaptchaResponse.class);
            
        if(apiResponse == null) {
            return false;
        }
        
        return apiResponse.isSuccess();
    }
    
    private static class ReCaptchaResponse {
        private boolean success;
        private String challenge_ts;
        private String hostname;
        
        public boolean isSuccess() {
            return success;
        }
        
        public void setSuccess(boolean success) {
            this.success = success;
        }
        
        public String getChallenge_ts() {
            return challenge_ts;
        }
        
        public void setChallenge_ts(String challenge_ts) {
            this.challenge_ts = challenge_ts;
        }
        
        public String getHostname() {
            return hostname;
        }
        
        public void setHostname(String hostname) {
            this.hostname = hostname;
        }
    }
} 