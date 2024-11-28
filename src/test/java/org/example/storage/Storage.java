package org.example.storage;


import org.example.interfaces.FileRepository;
import org.springframework.stereotype.Repository;

@Repository
public class Storage<T> implements FileRepository<T> {
}
