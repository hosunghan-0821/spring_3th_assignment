package com.example.spring_3th_assignment.Controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {

  @NotBlank
  private String nickname;

  @NotBlank
  private String password;

}
