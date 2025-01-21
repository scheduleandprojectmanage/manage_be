package com.project.scheduling.baseentity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.ListIndexBase;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass //BaseEntity를 상속한 엔티티들은 BaseEntity 안에 있는 모든 변수들을 컬럼으로 인식
@EntityListeners(AuditingEntityListener.class) // Auditing 기능을 사용하겠다. 자동으로 값을 매핑하겠다.
public abstract class BaseEntity extends BaseTimeEntity{

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;
}
