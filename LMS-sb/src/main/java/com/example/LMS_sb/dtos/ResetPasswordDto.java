package com.example.LMS_sb.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ResetPasswordDto {



    @NotBlank
    @Size(min = 8)
    private String newPassword;
}
