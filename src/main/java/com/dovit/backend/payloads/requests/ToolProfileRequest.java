package com.dovit.backend.payloads.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToolProfileRequest {

    private Long memberId;
    private Long toolId;
    private Long levelId;
}
