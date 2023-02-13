package com.fast.miniproject.auth.service.Impl;

import com.fast.miniproject.auth.entity.Token;
import com.fast.miniproject.auth.repository.TokenRepository;
import com.fast.miniproject.auth.service.TokenService;
import com.fast.miniproject.global.response.ErrorResponseDTO;
import com.fast.miniproject.global.response.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    @Override
    public ResponseDTO<?> logout(String header){
        if (checkLogout(header)){
            return new ErrorResponseDTO(500,"이미 만료된 토큰입니다.").toResponse();
        }else {
            try {
                tokenRepository.save(Token.builder().token(header).build());
                return new ResponseDTO<>(null);
            } catch (Exception e) {
                return new ErrorResponseDTO(500,"로그아웃 시도중 에러가 발생 했습니다.").toResponse();
            }
        }
    }

    @Override
    public boolean checkLogout(String token){
        return tokenRepository.existsByToken(token);
    }

}