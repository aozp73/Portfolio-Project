package com.portfolio.portfolio_project.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portfolio.portfolio_project.core.util.myproject_utils.MyProjectUtils;
import com.portfolio.portfolio_project.core.util.s3_utils.S3Utils;
import com.portfolio.portfolio_project.domain.jpa.myproject.my_project.MyProject;
import com.portfolio.portfolio_project.domain.jpa.myproject.my_project.MyProjectRepository;
import com.portfolio.portfolio_project.web.myproject.MyProjectDTO_In;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MyProjectService {
    
    private final MyProjectRepository myProjectRepository;
    private final S3Utils s3Utils;
    private final MyProjectUtils myProjectUtils;

    // POST
    @Transactional
    public String main_post(MyProjectDTO_In.postDTO postDTO_In){
        // 프로젝트 이미지, 개인수행 이미지 S3 업로드
        List<String> projectImg_nameAndUrl = s3Utils.uploadImageToS3(postDTO_In.getProjectImgBase64(), 
                                                                     postDTO_In.getProjectImageName(), 
                                                                     postDTO_In.getProjectImageType(),
                                                                     "my_project");
        List<String> individualPerformanceImg_nameAndUrl = s3Utils.uploadImageToS3(postDTO_In.getIndividualPerformanceBase64(), 
                                                                                   postDTO_In.getIndividualPerformanceImageName(), 
                                                                                   postDTO_In.getIndividualPerformanceImageType(),
                                                                                   "my_project_performance");

        // MyProject 엔터티 저장
        MyProject myProject = postDTO_In.toEntity(projectImg_nameAndUrl, individualPerformanceImg_nameAndUrl);
        myProjectRepository.save(myProject);

        // 역할 파싱 및 MyProjectRole 엔터티 저장
        myProjectUtils.saveRolesForProject(postDTO_In.getSelectedRoles(), myProject);

        return "";
    }
}
