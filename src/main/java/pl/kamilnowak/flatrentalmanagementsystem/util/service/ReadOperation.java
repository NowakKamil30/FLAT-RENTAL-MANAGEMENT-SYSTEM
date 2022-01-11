package pl.kamilnowak.flatrentalmanagementsystem.util.service;

import org.springframework.data.domain.Page;

public interface ReadOperation<T, ID> {
    T getObjectById(ID id);
    Page<T> getAllObject(int page);
}
