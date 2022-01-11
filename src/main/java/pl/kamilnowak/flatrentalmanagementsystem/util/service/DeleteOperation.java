package pl.kamilnowak.flatrentalmanagementsystem.util.service;

public interface DeleteOperation<ID> {
    void deleteById(ID id);
}
