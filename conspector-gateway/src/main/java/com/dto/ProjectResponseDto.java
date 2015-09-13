package com.dto;

/**
 * Created by aautushk on 9/13/2015.
 */
public class ProjectResponseDto {
    private Long projectGuid;
    private String description;

    public Long getProjectGuid() {
        return projectGuid;
    }

    public void setProjectGuid(Long projectGuid) {
        this.projectGuid = projectGuid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
