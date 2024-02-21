package com.kakao.saramaracommunity.bucket.dto.business.response;

import lombok.Builder;

import java.util.List;

@Builder
public record BucketUploadResponse(
        List<String> images
) {
    public static BucketUploadResponse of(List<String> images) {
        return BucketUploadResponse.builder()
                .images(images)
                .build();
    }
}
