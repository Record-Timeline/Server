package com.api.RecordTimeline.domain.profile.service;

import com.api.RecordTimeline.domain.common.ResponseDto;
import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.member.editor.MemberEditor;
import com.api.RecordTimeline.domain.member.repository.MemberRepository;
import com.api.RecordTimeline.domain.member.service.UpdateMemberServiceImpl;
import com.api.RecordTimeline.domain.profile.domain.Profile;
import com.api.RecordTimeline.domain.profile.dto.request.ProfileRequestDto;
import com.api.RecordTimeline.domain.profile.dto.response.ProfileResponseDto;
import com.api.RecordTimeline.global.s3.S3FileUploader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final MemberRepository memberRepository;
    private final S3FileUploader s3FileUploader;

    // 프로필 이미지 등록 및 수정
    public ResponseEntity<ResponseDto> updateProfileImage(String email, MultipartFile profileImage) {
        Member member = memberRepository.findByEmailAndIsDeletedFalse(email);
        if (member != null) {
            String uploadUrl = s3FileUploader.uploadMultipartFile(profileImage);
            member.getProfile().changeProfileImage(uploadUrl);
            memberRepository.save(member);
            return ProfileResponseDto.success();
        } else {
            return ProfileResponseDto.memberNotFound();
        }
    }

    // 소개글 등록 및 수정
    public ResponseEntity<ResponseDto> updateIntroduction(String email, String introduction) {
        Member member = memberRepository.findByEmailAndIsDeletedFalse(email);
        if (member != null) {
            Profile.builder().introduction(introduction).build();
            memberRepository.save(member);
            return ProfileResponseDto.success();
        } else {
            return ProfileResponseDto.memberNotFound();
        }
    }

    // 프로필 이미지 삭제
    public ResponseEntity<ResponseDto> deleteProfileImage(String email) {
        Member member = memberRepository.findByEmailAndIsDeletedFalse(email);
        if (member != null) {
            member.getProfile().deleteProfileImage();
            memberRepository.save(member);
            return ProfileResponseDto.imageDeleted();
        } else {
            return ProfileResponseDto.memberNotFound();
        }
    }

    // 소개글 삭제
    public ResponseEntity<ResponseDto> deleteIntroduction(String email) {
        Member member = memberRepository.findByEmailAndIsDeletedFalse(email);
        if (member != null) {
            Profile.builder().introduction(null).build();
            memberRepository.save(member);
            return ProfileResponseDto.introductionCleared();
        } else {
            return ProfileResponseDto.memberNotFound();
        }
    }




    /*@Transactional
    public ResponseEntity<ResponseDto> createOrUpdateProfile(String email, MultipartFile file, String introduction) {
        try {
            Member member = memberRepository.findByEmailAndIsDeletedFalse(email);
            if (member == null) {
                return ProfileResponseDto.memberNotFound();
            }

            String profileImgUrl = null;
            if (file != null && !file.isEmpty()) {
                profileImgUrl = s3FileUploader.uploadMultipartFile(file);
            }

            Profile newProfile = new Profile(member, profileImgUrl, introduction);
            member.updateProfile(newProfile);

            return ProfileResponseDto.success();
        } catch (Exception e) {
            return ProfileResponseDto.updateFailed();
        }
    }


    @Transactional
    public ResponseEntity<ResponseDto> deleteProfileImage(String email) {
        Member member = memberRepository.findByEmailAndIsDeletedFalse(email);
        if (member == null) {
            return ProfileResponseDto.memberNotFound();
        }

        Profile profile = member.getProfile();
        if (profile != null && profile.getProfileImgUrl() != null) {
            s3FileUploader.deleteFileFromS3(profile.getProfileImgUrl());
            profile.changeProfile(null, profile.getIntroduction());
            return ProfileResponseDto.imageDeleted();
        }
        return ProfileResponseDto.noImageFound();
    }

    @Transactional
    public ResponseEntity<ResponseDto> clearIntroduction(String email) {
        Member member = memberRepository.findByEmailAndIsDeletedFalse(email);
        if (member == null) {
            return ProfileResponseDto.memberNotFound();
        }

        Profile profile = member.getProfile();
        if (profile != null) {
            profile.changeProfile(profile.getProfileImgUrl(), null);
            return ProfileResponseDto.introductionCleared();
        }
        return ProfileResponseDto.noProfileFound();
    }*/
}
