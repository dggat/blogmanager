package blog.manager.application.services;

import javax.inject.Inject;

import blog.manager.domain.model.Role;
import blog.manager.domain.model.User;
import blog.manager.domain.model.UserRepository;

import java.util.Optional;
import java.util.Set;

public class CustomAuthorizationService implements AuthorizationService {
    @Inject
    private UserRepository userRepository;

    @Override
    public boolean authorise(String name, Set<Role> rolesAllowed) {
        Optional<User> user = userRepository.findByName(name);
        if (user.isEmpty()) return false;
        return user.get().getRoles().stream().anyMatch(rolesAllowed::contains);
    }
}
