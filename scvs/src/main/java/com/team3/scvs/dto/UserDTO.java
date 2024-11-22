package com.team3.scvs.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class UserDTO {
    private Long userId;
    private String email;
    private String password;
    private String nickname;
}
