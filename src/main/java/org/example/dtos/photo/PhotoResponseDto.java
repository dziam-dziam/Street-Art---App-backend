package org.example.dtos.photo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhotoResponseDto {

    private Long id;

    private String fileName;

    private String contentType;

    private Long sizeBytes;

    private String downloadUrl;
}

