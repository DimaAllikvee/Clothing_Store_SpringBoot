package org.example.interfaces;

import java.util.List;
import java.util.Optional;

public interface Service<T> {
    /**
     * Добавляет новый объект.
     * @param entity объект для добавления
     * @return true, если объект добавлен успешно, иначе false
     */
    boolean add(T entity);

    /**
     * Редактирует существующий объект.
     * @param entity объект с изменёнными данными
     * @return true, если объект успешно обновлён, иначе false
     */
    boolean edit(T entity);

    /**
     * Удаляет объект по его идентификатору.
     * @param id идентификатор объекта
     * @return true, если объект успешно удалён, иначе false
     */
    boolean remove(Long id);

    /**
     * Выводит все объекты.
     * @return true, если данные успешно выведены, иначе false
     */
    boolean print();

    /**
     * Возвращает список всех объектов.
     * @return список объектов
     */
    List<T> list();
}
