package pl.kamilnowak.flatrentalmanagementsystem.service;

public interface DeleteOperation<T, ID> {
    T deleteById(ID id);
}
