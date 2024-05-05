package com.api.RecordTimeline.domain.search.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchPageRecommendDto {
        private String nickname;
        private String profileImageUrl;
        private String introduction;


}
