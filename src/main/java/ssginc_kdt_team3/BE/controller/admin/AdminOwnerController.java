package ssginc_kdt_team3.BE.controller.admin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssginc_kdt_team3.BE.DTOs.admin.AdminBranchOwnerDTO;
import ssginc_kdt_team3.BE.DTOs.admin.AdminOwnerDetailDTO;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerJoinDTO;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerUpdateDTO;
import ssginc_kdt_team3.BE.domain.Owner;
import ssginc_kdt_team3.BE.service.admin.AdminOwnerDetailService;
import ssginc_kdt_team3.BE.service.admin.AdminOwnerService;
import ssginc_kdt_team3.BE.service.admin.branch.BranchOwnerService;
import ssginc_kdt_team3.BE.service.owner.OwnerJoinService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/owner")
public class AdminOwnerController {

    private final OwnerJoinService joinService;
    private final AdminOwnerService ownerService;
    private final BranchOwnerService branchOwnerService;
    private final AdminOwnerDetailService adminOwnerDetailService;

    @PostMapping("/join")
    public ResponseEntity<String> ownerJoin(@RequestBody OwnerJoinDTO ownerJoinDTO) {
        try {
            joinService.join(ownerJoinDTO);
            return ResponseEntity.ok("회원가입이 완료되었습니다!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/findAll/{page}")
    public ResponseEntity<Page<Owner>> findAll( @PathVariable(name = "page") int page) {
        Pageable pageable = PageRequest.of(page-1, 5);
        ResponseEntity<Page<Owner>> response = ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(ownerService.findAllOwner(pageable));

        return response;
    }
    @GetMapping("/findAll/{id}/{page}")
    public ResponseEntity<Page<AdminBranchOwnerDTO>> AdminBranchOwnerList
            (@PathVariable(name = "id")Long id
            , @PathVariable(name = "page") int page) {

        Pageable pageable = PageRequest.of(page-1, 10);
        ResponseEntity<Page<AdminBranchOwnerDTO>> response = ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(branchOwnerService.branchMatchOwner(id, pageable));

        return response;

    }
    @GetMapping("/findOne/{id}")
    public ResponseEntity<AdminOwnerDetailDTO> AdminOwnerDetailController(@PathVariable("id")Long id){

            AdminOwnerDetailDTO CheckAdminOwnerDetail = adminOwnerDetailService.checkAdminOwnerDetail(id);

            try {
                ResponseEntity<AdminOwnerDetailDTO> response = ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_VALUE)
                        .body(CheckAdminOwnerDetail);
                return response;
            }catch (Exception e){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(null);
            }

    }



//    @GetMapping("/findOne/{id}")
//    public ResponseEntity<Owner> findOne(@PathVariable(name = "id") Long ownerId) {
//
//        Optional<Owner> one = ownerService.findOne(ownerId);
//
//        if (one.isPresent()) {
//            return ResponseEntity.ok(one.get());
//        } else {
//            return null;
//        }
//    }

//    @PostMapping("/update/{id}")
//    public ResponseEntity<String> updateOwner(@PathVariable(name = "id") Long ownerId,
//                                @RequestBody OwnerUpdateDTO ownerUpdateDTO) {
//        try {
//            ownerService.updateOwnerInfo(ownerId,ownerUpdateDTO);
//            return new ResponseEntity<>("업데이트가 성공적으로 처리되었습니다!",HttpStatus.OK);
//        }catch (Exception e){
//
//            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
//        }
    }
