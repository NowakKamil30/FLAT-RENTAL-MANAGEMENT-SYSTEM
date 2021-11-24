package pl.kamilnowak.flatrentalmanagementsystem.service;

import java.util.List;

public interface ReadOperation<T, ID> {
    T getObjectById(ID id);
    List<T> getAllObject();
}
