package com.example.demo.oAuth;

import com.example.demo.oAuth.model.AccessToken;
import com.example.demo.oAuth.model.KakaoProfileDto;
import com.example.demo.oAuth.model.ProfileDto;
import com.example.demo.utils.JwtService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ProviderService {

    private final Gson gson;
    private final RestTemplate restTemplate;
    private final JwtService jwtService;

    @Transactional
    public AccessToken getAccessToken(String code, String provider) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params = generateParam(code);
        HttpEntity<MultiValueMap<String, String>> TokenRequest = new HttpEntity<>(params, httpHeaders);
        ResponseEntity<String> response = requestAuth(TokenRequest);
        return gson.fromJson(response.getBody(),AccessToken.class);
    }

    private MultiValueMap<String,String> generateParam(String code){
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type","authorization_code");
        params.add("client_id", "40497aa481ce3faa7ef3251506a95bf5");
        params.add("redirect_uri","http://localhost:9000/oauth/kakao");
        params.add("code",code);
        return params;
    }
    private ResponseEntity<String> requestAuth(HttpEntity request){
        return restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                request,
                String.class
        );
    }

    public ProfileDto getProfile(String accessToken, String provider) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Bearer " + accessToken);
        HttpEntity<MultiValueMap<String,String>> profileRequest = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> response = requestProfile(profileRequest);
        KakaoProfileDto kakaoProfileDto = gson.fromJson(response.getBody(), KakaoProfileDto.class);
        return new ProfileDto(
                kakaoProfileDto.getKakao_account().getEmail()
        );
    }
    private ResponseEntity<String> requestProfile(HttpEntity request){
        return restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                request,
                String.class
        );
    }

}