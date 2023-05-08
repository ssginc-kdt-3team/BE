package ssginc_kdt_team3.BE.domain;

import lombok.*;


import javax.persistence.*;

@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "owner")
@Getter
@Setter
@ToString
public class Owner extends User{

}
