package by.tech.project_management_app.entities;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUser implements UserDetails {

    private static final long serialVersionUID = -1895238097460946844L;
    private User user;
    private List<GrantedAuthority> grantList;

    public SecurityUser(User user, List<GrantedAuthority> grantList) {
        this.user = user;
        this.grantList = grantList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantList;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    public int getUserId() {
        return user.getId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

}