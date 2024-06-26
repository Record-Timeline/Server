package com.api.RecordTimeline.domain.profile.service;

import com.api.RecordTimeline.domain.common.ResponseDto;
import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.member.repository.MemberRepository;
import com.api.RecordTimeline.domain.profile.domain.Profile;
import com.api.RecordTimeline.domain.profile.dto.response.ProfileResponseDto;
import com.api.RecordTimeline.global.s3.S3FileUploader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;

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
        if (member == null) {
            return ProfileResponseDto.memberNotFound();
        }

        Profile profile = member.getProfile();
        if (profile == null) {
            profile = Profile.builder()
                    .member(member)
                    .build(); // 처음 프로필 생성하는 경우
        }
        profile.updateIntroduction(introduction); // 기존 또는 새 프로필 소개글 업데이트

        memberRepository.save(member);
        return ProfileResponseDto.success();
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
        if (member == null) {
            return ProfileResponseDto.memberNotFound();
        }
        Profile profile = member.getProfile();
        if (profile != null) {
            profile.updateIntroduction(null);
            memberRepository.save(member);
            return ProfileResponseDto.introductionCleared();
        } else {
            return ProfileResponseDto.noProfileFound();
        }
    }
}
