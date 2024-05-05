package com.api.RecordTimeline.domain.profile.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class ProfileRequestDto {

    private MultipartFile profileImage;
    private String introduction;
}
