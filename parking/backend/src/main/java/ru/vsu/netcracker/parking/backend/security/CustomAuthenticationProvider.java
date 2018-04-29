package ru.vsu.netcracker.parking.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;
import ru.vsu.netcracker.parking.backend.dao.ObjectsDAO;
import ru.vsu.netcracker.parking.backend.models.Obj;

import java.util.Base64;
import java.util.List;

@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final long ADMIN = 1L;
    private final long OWNER = 2L;
    private final long USER = 3L;
    private final long REST_API_USER = 4L;

    private final long PASSWORD_ATTRIBUTE_ID = 203L;
    private final long ROLE_ATTRIBUTE_ID = 200L;

    private ObjectsDAO objectsDAO;
    private CustomSHA256PasswordEncoder passwordEncoder;

    @Autowired
    public CustomAuthenticationProvider(ObjectsDAO objectsDAO, CustomSHA256PasswordEncoder passwordEncoder) {
        this.objectsDAO = objectsDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String actualPassword = authentication.getCredentials().toString();
        //actualPassword = passwordEncoder.encode(actualPassword, username.getBytes());

        Obj obj;
        try {
            obj = objectsDAO.getObjByUserName(username);
            String expectedPassword = obj.getValues().get(PASSWORD_ATTRIBUTE_ID);

            if (!passwordEncoder.matches(passwordEncoder.decode(actualPassword), passwordEncoder.decode(expectedPassword))) {
                throw new BadCredentialsException("Bad Credentials");
            }
        } catch (EmptyResultDataAccessException e) {
            throw new BadCredentialsException("Bad Credentials");
        }

        long roleId = obj.getReferences().get(ROLE_ATTRIBUTE_ID);

        return new UsernamePasswordAuthenticationToken(username, actualPassword, getGrantedAuthorities(roleId));
    }

    private List<GrantedAuthority> getGrantedAuthorities(long roleId) {
        if (roleId == ADMIN) {
            return AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_OWNER", "ROLE_USER");
        } else if (roleId == OWNER) {
            return AuthorityUtils.createAuthorityList("ROLE_OWNER", "ROLE_USER");
        } else if (roleId == USER) {
            return AuthorityUtils.createAuthorityList("ROLE_USER");
        } else if (roleId == REST_API_USER) {
            return AuthorityUtils.createAuthorityList("ROLE_REST_API_USER");
        }
        return AuthorityUtils.NO_AUTHORITIES;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
