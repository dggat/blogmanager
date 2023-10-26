package blog.manager.application.services;

import java.util.Set;

import blog.manager.domain.model.Role;

public interface AuthorizationService {
    boolean authorise(String name, Set<Role> rolesAllowed);
}
