package com.fast.miniproject.auth.service.Impl;

import com.fast.miniproject.auth.dto.LoginReqDTO;
import com.fast.miniproject.auth.dto.SignupReqDTO;
import com.fast.miniproject.auth.dto.PatchUserReqDTO;
import com.fast.miniproject.auth.dto.PatchUserResDTO;
import com.fast.miniproject.auth.entity.User;
import com.fast.miniproject.auth.jwt.JwtProvider;
import com.fast.miniproject.auth.repository.UserRepository;
import com.fast.miniproject.auth.service.UserService;
import com.fast.miniproject.global.response.ErrorResponseDTO;
import com.fast.miniproject.global.response.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    public ResponseDTO<?> signup(SignupReqDTO signupReqDTO) {


        if (userRepository.findByEmail(signupReqDTO.getEmail()).isEmpty()) {
            String encodingPassword = encodingPassword(signupReqDTO.getPassword());
            signupReqDTO.setPassword(encodingPassword);
            userRepository.save(signupReqDTO.toEntity());
            return new ResponseDTO<>(signupReqDTO.toString());
        } else {
            return new ErrorResponseDTO(500, "이미 존재하는 회원입니다.").toResponse();
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
    @Override
    public ResponseDTO<?> editUser(LoginReqDTO loginReqDTO) {
        try {
            User user = userRepository.findByEmail(loginReqDTO.getEmail())
                    .orElseThrow(IllegalArgumentException::new);
            return new ResponseDTO<>(new PatchUserResDTO(user));
        }catch (IllegalArgumentException e) {
            return new ErrorResponseDTO(500, "로그인 정보가 없습니다.").toResponse();
        }
    }



//    @Override
//    public UserResDTO editUser(LoginReqDTO loginReqDTO) {
//        Optional<User> loginUserInfo = userRepository.findByEmail(loginReqDTO.getEmail());
//
//        return new UserResDTO(loginUserInfo);
//    }

    @Override
    @Transactional
    public ResponseDTO<?> updateUser(LoginReqDTO loginReqDTO, PatchUserReqDTO patchUserReqDTO) {
        try {
            User user = userRepository.findByEmail(loginReqDTO.getEmail())
                    .orElseThrow(IllegalArgumentException::new);

            passwordMustBeSame(patchUserReqDTO.getOldPassword(), user.getPassword());
                   patchUserReqDTO.setNewPassword(encodingPassword(patchUserReqDTO.getNewPassword()));

            user.update(patchUserReqDTO.getNewPassword(), patchUserReqDTO.getPhone(), patchUserReqDTO.getSalary(), patchUserReqDTO.getJob());

            return new ResponseDTO<>(200,"회원정보 수정 성공하였습니다.", patchUserReqDTO);
        }catch (IllegalArgumentException e) {
            return new ErrorResponseDTO(500, "기존 비밀번호가 일치하지 않습니다.").toResponse();
        }
    }

    private void passwordMustBeSame(String requestPassword, String password) {
        if (!passwordEncoder.matches(requestPassword, password)) {
            throw new IllegalArgumentException();
        }
    }
    private String encodingPassword(String password) {
        return passwordEncoder.encode(password);
    }


}
