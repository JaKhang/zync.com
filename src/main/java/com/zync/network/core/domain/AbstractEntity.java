package com.zync.network.core.domain;

import com.zync.network.core.persistence.ZIDJavaType;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JavaTypeRegistration;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Setter
@SuperBuilder
@JavaTypeRegistration(javaType = ZID.class, descriptorClass = ZIDJavaType.class)
public abstract class AbstractEntity {

    @Id
    protected ZID id;

    @CreatedDate
    protected LocalDateTime createdAt;
    @LastModifiedDate
    protected LocalDateTime updateAt;
    @ColumnDefault("false")
    @Setter
    protected boolean deleted;


    public AbstractEntity(ZID id) {
        this.id = id;
    }

    public AbstractEntity() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractEntity that = (AbstractEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
