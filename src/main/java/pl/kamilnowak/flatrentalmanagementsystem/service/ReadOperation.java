package pl.kamilnowak.flatrentalmanagementsystem.service;

import org.springframework.data.domain.Page;

import java.util.List;

public interface ReadOperation<T, ID> {
    T getObjectById(ID id);
    Page<T> getAllObject(int page);
}
