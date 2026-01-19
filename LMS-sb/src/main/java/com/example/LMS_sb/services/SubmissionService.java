package com.example.LMS_sb.services;


import com.example.LMS_sb.dtos.SubmissionDto;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SubmissionService {

    public List<SubmissionDto> getMySubmissions(Authentication authentication){

    }
}
