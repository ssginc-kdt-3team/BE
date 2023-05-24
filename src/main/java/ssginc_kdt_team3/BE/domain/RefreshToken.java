package ssginc_kdt_team3.BE.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class RefreshToken {

    @Id
    @Column(name = "refresh_token_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "key_name")
    private String keyName;

    @Column(name = "refresh_token")
    private String refreshToken;
}
