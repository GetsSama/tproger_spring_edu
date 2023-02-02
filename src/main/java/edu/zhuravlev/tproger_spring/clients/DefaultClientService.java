package edu.zhuravlev.tproger_spring.clients;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.management.MBeanRegistrationException;
import javax.security.auth.login.LoginException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultClientService implements ClientService{
    private final ClientRepository clientRepository;

    @Override
    @SneakyThrows
    public void register(String clientId, String clientSecret) {
        if (clientRepository.findById(clientId).isPresent())
            throw new RegistrationException("Client with id: " + clientId + " already registered");

        String hash = BCrypt.hashpw(clientSecret, BCrypt.gensalt());
        clientRepository.save(new ClientEntity(clientId, hash));
    }

    @Override
    @SneakyThrows
    public void checkCredentials(String clientId, String clientSecret) {
        Optional<ClientEntity> clientEntity = clientRepository.findById(clientId);
        if (clientEntity.isEmpty())
            throw new LoginException("Client with id: " + clientId + " not found");

        ClientEntity client = clientEntity.get();

        if(!BCrypt.checkpw(clientSecret, client.getHash()))
            throw new LoginException("Secret is incorrect");
    }
}
