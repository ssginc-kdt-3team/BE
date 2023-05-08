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

  public Address(String city, String district, String detail, String zipCode) {
    this.city = city;
    this.district = district;
    this.detail = detail;
    this.zipCode = zipCode;
  }

  public Address() {
  }
  @Override
  public String toString() {
    return String.format("%s %s %s %s", city, district, detail, zipCode);
  }
  //문자열로 변환하려고 코드를 추가했습니다.
  //0506 고신영
}
