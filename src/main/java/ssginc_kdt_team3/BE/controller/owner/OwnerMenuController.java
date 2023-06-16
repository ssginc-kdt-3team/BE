package ssginc_kdt_team3.BE.controller.owner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ssginc_kdt_team3.BE.DTOs.branch.BranchAddDTO;
import ssginc_kdt_team3.BE.DTOs.menu.MenuAddDTO;
import ssginc_kdt_team3.BE.DTOs.menu.MenuUpdateDTO;
import ssginc_kdt_team3.BE.service.menu.OwnerMenuService;

@Slf4j
@RestController
@RequestMapping("/owner/menu")
@RequiredArgsConstructor
public class OwnerMenuController {

    private final OwnerMenuService menuService;

    @PostMapping("/add")
    public ResponseEntity createNewMenu(@Validated @RequestPart(value = "menuData") MenuAddDTO dto, BindingResult bindingResult,
                                        @RequestPart(value = "menuImg") MultipartFile multipartFile) {
        if (bindingResult.hasErrors()) {
            log.info("error");
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        if (menuService.createNewMenu(dto, multipartFile)) {
            return ResponseEntity.ok().body("신규 메뉴 등록에 성공했습니다.");
        }

        return ResponseEntity.badRequest().body("신규 메뉴 등록에 실패했습니다.");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateMenu(@Validated @RequestPart(value = "menuData") MenuUpdateDTO dto, BindingResult bindingResult,
                                     @RequestPart(value = "menuImg") MultipartFile multipartFile, @PathVariable(name = "id") Long menuId) {

        if (bindingResult.hasErrors()) {
            log.info("error");
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        if (menuService.updateMenu(menuId, dto, multipartFile)) {
            return ResponseEntity.ok().body("메뉴 정보 수정에 성공했습니다.");
        }

        return ResponseEntity.badRequest().body("메뉴 정보 수정에 실패했습니다.");
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteMenu(@PathVariable(name = "id") Long menuId) {
        if (menuService.delete(menuId)) {
            return ResponseEntity.ok().body("메뉴 정보 삭제에 성공했습니다.");
        }
        return ResponseEntity.badRequest().body("메뉴 정보 삭제에 실패했습니다.");
    }
}
