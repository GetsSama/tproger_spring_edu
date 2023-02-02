package edu.zhuravlev.tproger_spring.clients;

public interface ClientService {
    void register(String clientId, String clientSecret);
    void checkCredentials(String clientId, String clientSecret);
}
