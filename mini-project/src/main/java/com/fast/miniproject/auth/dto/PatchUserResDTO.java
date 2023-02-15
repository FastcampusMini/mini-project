package com.fast.miniproject.auth.dto;

import com.fast.miniproject.auth.entity.User;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PatchUserResDTO {

    private String email;
    private String password;
    private String name;
    private int age;
    private String gender;
    private String phone;
    private Long salary;
    private String job;


    public PatchUserResDTO(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.name = user.getName();
        this.age = user.getAge();
        this.gender = user.getGender();
        this.phone = user.getPhone();
        this.salary = user.getSalary();
        this.job = user.getJob();
    }
}
