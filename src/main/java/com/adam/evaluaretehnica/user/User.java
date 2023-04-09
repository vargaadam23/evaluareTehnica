package com.adam.evaluaretehnica.user;

import com.adam.evaluaretehnica.badge.Badge;
import com.adam.evaluaretehnica.exception.NotEnoughTokensException;
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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails, RankedEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Token> tokens;
    @Column(name = "user_rank")
    private int rank;
    @OneToMany(mappedBy = "questMaster")
    private List<Quest> ownedQuests;
    @OneToMany(mappedBy = "user")
    private List<UserQuest> userQuests;
    private int currencyTokens;
    @ManyToMany(
            cascade = CascadeType.ALL
    )
    @JoinTable(
            name = "user_badges",
            joinColumns = @JoinColumn(name = "user_id") ,
            inverseJoinColumns = @JoinColumn(name = "badge_id")
    )
    private List<Badge> badges;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

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

    @Override
    public void setCurrencyTokens(int amount){
        currencyTokens = amount;
    }

    public void addCurrencyTokensToBalance(int amount) {
        setCurrencyTokens(getCurrencyTokens() + amount);
    }

    public void subtractCurrencyTokensFromBalance(int amount) throws NotEnoughTokensException {
        if (amount > getCurrencyTokens()){
            throw new NotEnoughTokensException("User does not have enough tokens to create this quest!");
        }

        setCurrencyTokens(getCurrencyTokens() - amount);
    }
}
