package edu.zhuravlev.tproger_spring.clients;

import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<ClientEntity, String> {
}
