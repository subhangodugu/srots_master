package com.srots.dto.analytics;

import com.srots.model.Job.JobType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JobTypeDTO {
    private String name;
    private Long value;

    public JobTypeDTO(JobType type, Long value) {
        this.name = type != null ? type.getDisplay() : "Unknown";
        this.value = value;
    }

    public JobTypeDTO(String name, Long value) {
        this.name = name;
        this.value = value;
    }
}
