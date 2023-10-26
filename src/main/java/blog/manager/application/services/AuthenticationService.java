package blog.manager.application.services;

public interface AuthenticationService {
    boolean authenticate(String name, String password);

    String getAuthenticationScheme();
}
