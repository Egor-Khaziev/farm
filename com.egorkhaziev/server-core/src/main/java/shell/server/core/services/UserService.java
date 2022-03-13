package shell.server.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shell.api.exceptions.ResourceNotFoundException;
import shell.api.utils.AuthMessage;
import shell.api.utils.Credential;
import shell.server.core.entities.User;
import shell.server.core.repositories.UserRepository;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Map<String, User> getActivUserTokenMap() {
        return Collections.unmodifiableMap(activUserTokenMap);
    }

    private Map<String, User> activUserTokenMap;

    @PostConstruct
    private void init (){
        activUserTokenMap = new HashMap<>();
    }


    public AuthMessage login(Credential credential) {
        User user = userRepository.findUserByUsername(credential.getUsername()).orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));

        if(activUserTokenMap.containsValue(user)){
            for (Map.Entry<String,User> entry : activUserTokenMap.entrySet()){
                if(entry.getValue().equals(user)){
                    activUserTokenMap.remove(entry.getKey());
                }
            }
        }

        if(user.getPassword().equals(credential.getPassword())){
            String uuid = UUID.randomUUID().toString();

            activUserTokenMap.put(uuid, user);
            return new AuthMessage(true, uuid);
        }

        return new AuthMessage(false,"");
    }

    @Transactional
    public AuthMessage registration(Credential credential) {

        if(!userRepository.existsByUsername(credential.getUsername())){
            User user = new User(credential.getUsername(),credential.getPassword());
            String uuid = UUID.randomUUID().toString();

            userRepository.save(user);
            activUserTokenMap.put(uuid, user);
            return new AuthMessage(true, uuid);
        }

        return new AuthMessage(false, "");
    }

    public AuthMessage quit(String token) {
        if(activUserTokenMap.containsKey(token)){
            activUserTokenMap.remove(token);
        }
        return new AuthMessage(false, "");
    }
}
