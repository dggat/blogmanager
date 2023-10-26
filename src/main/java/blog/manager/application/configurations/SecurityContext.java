package blog.manager.application.configurations;
import java.security.Principal;
import blog.manager.domain.model.Role;
import blog.manager.domain.model.User;

public class SecurityContext implements javax.ws.rs.core.SecurityContext {
    private User user;

    @Override
    public Principal getUserPrincipal() {
        return user;
    }

    @Override
    public boolean isUserInRole(String role) {
        return user != null && user.getRoles().contains(Role.valueOf(role));
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public String getAuthenticationScheme() {
        return BASIC_AUTH;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
