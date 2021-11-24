package pl.kamilnowak.flatrentalmanagementsystem.service;

public interface CreateOperation<T> {
    T createObject(T t);
}
