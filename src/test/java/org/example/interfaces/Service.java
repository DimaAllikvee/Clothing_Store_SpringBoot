package org.example.interfaces;

import java.util.List;

public interface Service<T> {
    boolean add(); // Без аргументов для консольного ввода
    boolean add(T entity); // С аргументом для REST-контроллера
    boolean edit(T entity);
    boolean remove(Long id);
    boolean print();
    List<T> list();
    T findById(Long id);
}
