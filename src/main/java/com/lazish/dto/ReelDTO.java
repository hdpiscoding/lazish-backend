package com.lazish.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReelDTO {
    private String id;
    private String caption;
    private String video;
    private long likes;
}
