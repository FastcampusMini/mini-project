package com.fast.miniproject.auth.dto;


import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PatchUserReqDTO {

    private String OldPassword;
    private String newPassword;
    private String phone;
    private Long salary;
    private String job;

}
