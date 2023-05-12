package ssginc_kdt_team3.BE.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Alarm {

    @Id
    @Column(name = "alarm_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "alarm_contents")
    private String contents;

    @Column(name = "alarm_target")
    private String target;

    @Column(name = "alarm_send_condition")
    private String condition;
}
