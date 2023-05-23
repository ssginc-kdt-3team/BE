//package ssginc_kdt_team3.BE.service.admin;
//
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import ssginc_kdt_team3.BE.DTOs.branch.BranchAddDTO;
//
//@SpringBootTest
//class AdminBranchServiceTest {
//
//    @Autowired
//    AdminBranchService adminBranchService;
//
//    @Test
//    void addNewBranch() {
//        BranchAddDTO branchAddDTO = new BranchAddDTO();
//        branchAddDTO.setBranchImgUrl("abc.com");
//        branchAddDTO.setCity("광진시티");
//        branchAddDTO.setDetail("진구길 진구로 123");
//        branchAddDTO.setDistrict("광진구");
//        branchAddDTO.setZipCode("12345");
//        branchAddDTO.setName("산타점");
//        branchAddDTO.setPhone("010-1234-5678");
//        branchAddDTO.setOpenDay("2020-12-25");
//        branchAddDTO.setOpenTime("09:00");
//        branchAddDTO.setCloseTime("21:00");
//
//        Long aLong = adminBranchService.addNewBranch(branchAddDTO);
//    }
//}