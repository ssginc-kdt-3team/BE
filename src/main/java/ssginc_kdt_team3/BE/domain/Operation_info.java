package ssginc_kdt_team3.BE.domain;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "operation_info")
public class Operation_info {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
}
