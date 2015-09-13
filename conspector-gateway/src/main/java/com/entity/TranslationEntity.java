package com.entity;

import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * Created by aautushk on 9/13/2015.
 */
@Entity
@Table(name = "translations")
@EntityListeners(AuditingEntityListener.class)
@Audited
public class TranslationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "translation_guid"   )
    private long translationGuid;

    @Column(name = "parent_guid")
    private long parentGuid;

    @Column(name = "parent_entity")
    private String parentEntity;

    @Column(name = "field")
    private String field;

    @Column(name = "language")
    private String language;

    @Column(name = "content")
    private String content;

    public long getParentGuid() {
        return parentGuid;
    }

    public void setParentGuid(long parentGuid) {
        this.parentGuid = parentGuid;
    }

    public String getParentEntity() {
        return parentEntity;
    }

    public void setParentEntity(String parentEntity) {
        this.parentEntity = parentEntity;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
