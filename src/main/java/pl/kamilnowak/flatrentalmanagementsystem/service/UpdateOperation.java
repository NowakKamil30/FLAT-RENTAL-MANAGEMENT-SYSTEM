package pl.kamilnowak.flatrentalmanagementsystem.service;

public interface UpdateOperation<T, ID> {
    T updateObject(T t, ID id);
}
