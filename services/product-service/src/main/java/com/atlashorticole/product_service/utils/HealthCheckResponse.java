package com.atlashorticole.product_service.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthCheckResponse {
            private String message;
        private String status;
        private Long timestamp;
}
