package com.portfolio.portfolio_project.web.skills;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.google.gson.Gson;
import com.portfolio.portfolio_project.core.dto.ResponseDTO;
import com.portfolio.portfolio_project.service.MySkillsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MySkillsController {

    private final MySkillsService mySkillsService;

    @GetMapping("/skills")
    public String myskillspage(Model model){
    
        MySkillsDTO_Out.FindAllDTO mySkillsAllDTO = mySkillsService.findAllSkills();
        Gson gson = new Gson();
        String jsonSkills = gson.toJson(mySkillsAllDTO.getSkillsMap());
        model.addAttribute("allSkills", jsonSkills);
        
        return "/myskills";
    }

    @PostMapping("/auth/skills")
    public ResponseEntity<?> skills_post(@RequestBody MySkillsDTO_In.PostDTO postDTO_In){
        mySkillsService.mySkills_post(postDTO_In);
        
        return ResponseEntity.ok().body(new ResponseDTO<>().data(""));
    }
}
