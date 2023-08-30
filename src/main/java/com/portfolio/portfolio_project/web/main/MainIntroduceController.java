package com.portfolio.portfolio_project.web.main;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.portfolio.portfolio_project.core.dto.ResponseDTO;
import com.portfolio.portfolio_project.service.MainIntroduceService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MainIntroduceController {

    private final MainIntroduceService mainIntroduceService;

    // FindAll
    @GetMapping("/mainpage")
    public String main_findAll(Model model){
        System.out.println("테스트 : ");
        List<MainIntroduceDTO_Out.FindAllDTO> mainIntroduceList = mainIntroduceService.main_findAll();
        model.addAttribute("mainIntroduceList", mainIntroduceList);

        return "/mainpage";
    }

    @PostMapping("/auth/main")
    public ResponseEntity<?> main_post(@RequestBody MainIntroduceDTO_In.postDTO postDTO_In){
        MainIntroduceDTO_Out.PostDTO postDTO_Out = mainIntroduceService.main_post(postDTO_In);

        return ResponseEntity.ok().body(new ResponseDTO<>().data(postDTO_Out));
    }

    @PutMapping("/auth/main")
    public ResponseEntity<?> main_put(@RequestBody MainIntroduceDTO_In.putDTO putDTO_In){
        MainIntroduceDTO_Out.PutDTO putDTO_Out = mainIntroduceService.main_put(putDTO_In);

        return ResponseEntity.ok().body(new ResponseDTO<>().data(putDTO_Out));
    }

    @DeleteMapping("/auth/main")
    public ResponseEntity<?> main_delete(@RequestParam("postPK") Long postPK){
        mainIntroduceService.main_delete(postPK);

        return ResponseEntity.ok().body(new ResponseDTO<>().data(""));
    }
}
