package com.fast.miniproject.auth.service.Impl;

import com.fast.miniproject.auth.dto.LoginReqDTO;
import com.fast.miniproject.auth.dto.SignupReqDTO;
import com.fast.miniproject.auth.dto.UserResDTO;
import com.fast.miniproject.auth.entity.User;
import com.fast.miniproject.auth.jwt.JwtProvider;
import com.fast.miniproject.auth.repository.UserRepository;
import com.fast.miniproject.auth.service.UserService;
import com.fast.miniproject.global.response.ErrorResponseDTO;
import com.fast.miniproject.global.response.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    public ResponseDTO<?> signup(SignupReqDTO signupReqDTO) {


        if (userRepository.findByEmail(signupReqDTO.getEmail()).isPresent()) {
            return new ErrorResponseDTO(500, "이미 존재하는 회원입니다.").toResponse();
        } else {
            String encodingPassword = encodingPassword(signupReqDTO.getPassword());
            signupReqDTO.setPassword(encodingPassword);
            userRepository.save(signupReqDTO.toEntity());
            return new ResponseDTO<>(signupReqDTO.toString());
        }
    }

    @Override
    public ResponseDTO<?> login(LoginReqDTO loginReqDTO) {
        try {
            User user = userRepository.findByEmail(loginReqDTO.getEmail())
                    .orElseThrow(IllegalArgumentException::new);
            passwordMustBeSame(loginReqDTO.getPassword(), user.getPassword());
            return new ResponseDTO<>(jwtProvider.makeJwtToken(user));
        } catch (NoSuchElementException e) {
            return new ErrorResponseDTO(500, "로그인에 실패하였습니다.").toResponse();
        } catch (IllegalArgumentException e) {
            return new ErrorResponseDTO(500, "로그인에 실패하였습니다.").toResponse();
        }

    }


//    @Override
//    public UserResDTO editUser(LoginReqDTO loginReqDTO) {
//        Optional<User> loginUserInfo = userRepository.findByEmail(loginReqDTO.getEmail());
//
//        return new UserResDTO(loginUserInfo);
//    }

    private void passwordMustBeSame(String requestPassword, String password) {
        if (!passwordEncoder.matches(requestPassword, password)) {
            throw new IllegalArgumentException();
        }
    }

    private String encodingPassword(String password) {
        return passwordEncoder.encode(password);
    }

}
