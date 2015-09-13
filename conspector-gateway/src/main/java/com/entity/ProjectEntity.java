package com.entity;

import javax.persistence.*;

/**
 * Created by aautushk on 9/7/2015.
 */
@Entity
@Table(name = "projects")
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_guid"   )
    private long projectGuid;

    @Column(name = "description")
    private String description;

    public long getProjectGuid() {
        return projectGuid;
    }
    public void setProjectGuid(long value){
        this.projectGuid = value;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String value){
        this.description = value;
    }
}
