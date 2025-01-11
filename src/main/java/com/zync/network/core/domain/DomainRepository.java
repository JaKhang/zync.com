package com.zync.network.core.domain;


import java.util.Optional;

public interface DomainRepository<T extends AggregateRoot>{
    Optional<T> findById(ZID id);
    void delete(ZID id);
    ZID save(T t);

}
