package com.entity;

import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * Created by aautushk on 9/7/2015.
 */
@Entity
@Table(name = "projects")
@EntityListeners(AuditingEntityListener.class)
@Audited
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_guid"   )
    private long projectGuid;

    public long getProjectGuid() {
        return projectGuid;
    }
    public void setProjectGuid(long value){
        this.projectGuid = value;
    }

}
