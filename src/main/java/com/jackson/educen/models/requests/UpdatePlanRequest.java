package com.jackson.educen.models.requests;

import lombok.Data;

import java.util.Map;
@Data
public class UpdatePlanRequest {
    private Map<String, String> planComments;
}
