package pl.kamilnowak.flatrentalmanagementsystem.util.service;

public interface UpdateOperation<T, ID> {
    T updateObject(T t, ID id);
}
