package blog.manager.application.services;

import org.apache.commons.lang3.StringUtils;

import blog.manager.domain.model.UserRepository;

import javax.inject.Inject;

public abstract class HTTPAuthenticationService implements AuthenticationService {
    public static final String REALM = "Restricted access";

    @Inject
    protected UserRepository userRepository;

    public abstract boolean isSecure();

    public abstract boolean validateHeader(String header);

    public String getRealm() {
        return REALM;
    }

    public String getWWWAuthenticateHeader() {
        return StringUtils.capitalize(getAuthenticationScheme().toLowerCase()) + " realm=\"" + getRealm() + "\"";
    }
}
