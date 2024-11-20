package com.zync.network.account.domain.aggregates.account;

import com.zync.network.core.domain.AbstractEntity;
import com.zync.network.core.domain.ZID;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "codes")

public class Code extends AbstractEntity {
    String value;
    int age;
    CodeType type;

    @ManyToOne
    @JoinColumn(name = "account_id")
    Account account;

    public Code(ZID id, String value, int age, CodeType codeType, Account account) {
        super(id);
        this.value = value;
        this.age = age;
        this.type = codeType;
        createdAt = LocalDateTime.now();
        this.account = account;
    }

    public boolean isExpired(){
        LocalDateTime now = LocalDateTime.now();
        return createdAt.plusMinutes(age).isBefore(now);
    }

    public boolean isMatch(String value){
        return this.value.equals(value);
    }

    public boolean isType(CodeType type){
        return this.type == type;
    }


}
