package com.fast.miniproject.auth.controller;


import com.fast.miniproject.auth.dto.LoginReqDTO;
import com.fast.miniproject.auth.dto.PatchUserReqDTO;
import com.fast.miniproject.auth.dto.SignupReqDTO;
import com.fast.miniproject.auth.dto.PatchUserResDTO;
import com.fast.miniproject.auth.service.TokenService;
import com.fast.miniproject.auth.service.UserService;
import com.fast.miniproject.global.response.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final TokenService tokenService;

    @PostMapping("/register")
    public ResponseDTO<?> signUp(SignupReqDTO signupReqDTO) {
        return userService.signup(signupReqDTO);
    }

    @PostMapping("/login")
    public ResponseDTO<?> signIn(LoginReqDTO loginReqDTO) {
        return userService.login(loginReqDTO);
    }

    @PostMapping("/logout")
    public ResponseDTO<?> logout(@RequestHeader(name = "Authorization") String header) {
        return tokenService.logout(header);
    }
    @PostMapping("/api/user")
    public ResponseDTO<?> editUser(@AuthenticationPrincipal LoginReqDTO loginReqDTO) {
        return userService.editUser(loginReqDTO);
    }

    @PatchMapping("/api/user/update")
    public ResponseDTO<?> updateUser(@AuthenticationPrincipal LoginReqDTO loginReqDTO, PatchUserReqDTO patchUserReqDTO) {
        return userService.updateUser(loginReqDTO,patchUserReqDTO);
    }

    @DeleteMapping("/api/user/delete")
    public ResponseDTO<?> deleteUser(@AuthenticationPrincipal LoginReqDTO loginReqDTO, String password) {
        return userService.deleteUser(loginReqDTO,password);
    }

    @GetMapping("/hello")
    @PreAuthorize("hasAnyRole('USER')") // USER 권한만 호출 가능
    public String hello(@AuthenticationPrincipal LoginReqDTO loginReqDTO) {
        return loginReqDTO.getEmail() + ", 안녕하세요!";
    }


    //제발 되라


}
