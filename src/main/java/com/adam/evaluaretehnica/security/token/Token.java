package com.adam.evaluaretehnica.security.token;

import com.adam.evaluaretehnica.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Token {
    @Id
    @GeneratedValue
    public Long id;

    @Column(name = "token_value")
    public String token;

    @Column(name = "is_revoked")
    public boolean revoked;

    @Column(name = "is_expired")
    public boolean expired;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;
}
