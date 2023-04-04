package com.adam.evaluaretehnica.user;

import com.adam.evaluaretehnica.badge.Badge;
import com.adam.evaluaretehnica.quest.Quest;
import com.adam.evaluaretehnica.security.token.Token;
import com.adam.evaluaretehnica.userquest.UserQuest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;
    @OneToMany(mappedBy = "user")
    private List<Token> tokens;
    @Column(name = "user_rank")
    private Integer rank;
//    @OneToMany(mappedBy = "questMaster")
//    private List<Quest> ownedQuests;
//    @OneToMany(mappedBy = "user")
//    private List<UserQuest> userQuests;
//    @OneToMany(mappedBy = "user")
//    private List<Badge> badges;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

//    public List<Quest> getOwnedQuests() {
//        return ownedQuests;
//    }
//
//    public void setOwnedQuests(List<Quest> ownedQuests) {
//        this.ownedQuests = ownedQuests;
//    }
//
//    public List<UserQuest> getUserQuests() {
//        return userQuests;
//    }
//
//    public void setUserQuests(List<UserQuest> userQuests) {
//        this.userQuests = userQuests;
//    }
//
//    public List<Badge> getBadges() {
//        return badges;
//    }
//
//    public void setBadges(List<Badge> badges) {
//        this.badges = badges;
//    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
