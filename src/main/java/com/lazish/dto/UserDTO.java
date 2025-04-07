package com.lazish.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lazish.utils.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private UUID id;
    private String fullname;
    private int age;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String phone;
    private Date dob;
    private String avatar;
    private long diamond = 0;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<ReelDTO> reels;
}
