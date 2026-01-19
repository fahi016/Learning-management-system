package com.example.LMS_sb.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmissionCreateRequestDto {

    @NotBlank
    private String fileUrl;
}
