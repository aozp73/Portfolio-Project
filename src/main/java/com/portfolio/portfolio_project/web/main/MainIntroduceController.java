package com.portfolio.portfolio_project.web.main;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.portfolio.portfolio_project.core.dto.ResponseDTO;
import com.portfolio.portfolio_project.service.MainIntroduceService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MainIntroduceController {

    private final MainIntroduceService mainIntroduceService;

    @GetMapping("/main")
    public String mainpage(Model model){
        List<MainIntroduceDTO_Out.PostDTO> mainIntroduceList = mainIntroduceService.main_findAll();
        model.addAttribute("mainIntroduceList", mainIntroduceList);

        return "/main";
    }

    @PostMapping("/auth/main")
    public ResponseEntity<?> main_post(@RequestBody MainIntroduceDTO_In.postDTO postDTO_In){
        
        MainIntroduceDTO_Out.PostDTO postDTO_Out = mainIntroduceService.main_post(postDTO_In);

        return ResponseEntity.ok().body(new ResponseDTO<>().data(postDTO_Out));
    }
}