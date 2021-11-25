package pl.kamilnowak.flatrentalmanagementsystem.service;

public interface CRUDOperation<T, ID> extends ReadOperation<T, ID>, CreateOperation<T>, DeleteOperation<ID>, UpdateOperation<T, ID> {
}
