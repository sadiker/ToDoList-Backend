package com.sadiker.mobisem.model;

// import java.util.Collection;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;

import com.sadiker.mobisem.model.response.IResponse;

// import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "kullanicilar")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements IResponse {

    @Id
    private String id;
    private String username;
    private String password;
    private String role;
    private List<ToDo> todo;

    // @Override
    // public Collection<? extends GrantedAuthority> getAuthorities() {
    // return List.of(new SimpleGrantedAuthority(this.role));

    // }

    // @Override
    // public boolean isAccountNonExpired() {
    // return true;
    // }

    // @Override
    // public boolean isAccountNonLocked() {
    // return true;
    // }

    // @Override
    // public boolean isCredentialsNonExpired() {
    // return true;
    // }

    // @Override
    // public boolean isEnabled() {
    // return true;
    // }

}
