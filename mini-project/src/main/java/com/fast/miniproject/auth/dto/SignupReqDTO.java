package com.fast.miniproject.auth.dto;

import com.fast.miniproject.auth.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignupReqDTO {

    private String email;
    private String password;
    private String name;
    private int age;
    private String gender;
    private String phone;
    private Long salary;
    private String job;

    public User toEntity(){
        switch (gender){
            case "MALE": this.gender="m";
                  break;
            case "FEMALE":this.gender="f";
                  break;
            default: this.gender="e";//error
        }
        return User.builder()
                .email(this.email)
                .password(this.password)
                .name(this.name)
                .age(this.age)
                .gender(this.gender)
                .phone(this.phone)
                .salary(this.salary)
                .job(this.job)
                .build();
    }

}
