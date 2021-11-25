package pl.kamilnowak.flatrentalmanagementsystem.service;

public interface DeleteOperation<ID> {
    void deleteById(ID id);
}
