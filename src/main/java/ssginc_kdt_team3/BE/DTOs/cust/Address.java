package ssginc_kdt_team3.BE.DTOs.cust;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {
  private String city; // 도시
  private String district; // 구
  private String detail; // 상세주소
  private String zipCode; // 우편번호
}
