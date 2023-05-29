package ssginc_kdt_team3.BE.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/grade")
public class AdminGradeController {

  // 등급 목록 조회
  @GetMapping("")
  public ResponseEntity gradeList(){
    return null;
  }

  // 등급 상세정보 조회
  @GetMapping("/detail/{id}")
  public ResponseEntity gradeDetail(){
    return null;
  }

  // 등급 수정
  @GetMapping("/update")
  public ResponseEntity gradeUpdate(){
    return null;
  }
}
