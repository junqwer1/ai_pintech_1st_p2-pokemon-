package org.koreait.global.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@MappedSuperclass /*공통 상위 클래스*/
@EntityListeners(AuditingEntityListener.class) /*변화 감지*/
public abstract class BaseMemberEntity extends BaseEntity{

    @CreatedBy //로그인한 사용자의 이메일 정보
    @Column(length = 65, updatable = false)
    private String createBy;

    @LastModifiedBy
    @Column(length = 65, insertable = false)
    private String modifiedBy;
}
