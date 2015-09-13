package com.rest;

import com.dto.ProjectRequestDto;
import com.dto.ProjectResponseDto;
import com.entity.ProjectEntity;
import com.entity.TranslationEntity;
import com.repository.IProjectRepository;
import com.repository.ITranslationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aautushk on 9/13/2015.
 */
@Component
@RestController
@RequestMapping("/test")
public class ProjectRest {
    private ModelMapper modelMapper = new ModelMapper(); //read more at http://modelmapper.org/user-manual/

    @Autowired
    private IProjectRepository projectRepository;

    @Autowired
    private ITranslationRepository translationRepository;

    @RequestMapping(value = "/createProject", method = RequestMethod.POST)
    @Transactional
    public void createProject(@Valid @RequestBody ProjectRequestDto projectRequestDto) {
        ProjectEntity project = new ProjectEntity();
        projectRepository.save(project);

        TranslationEntity translationEntity = new TranslationEntity();
        translationEntity.setParentGuid(project.getProjectGuid());
        translationEntity.setParentEntity("Project");
        translationEntity.setField("description");
        translationEntity.setLanguage(LocaleContextHolder.getLocale().getDisplayLanguage());
        translationEntity.setContent(projectRequestDto.getDescription());

        translationRepository.save(translationEntity);
        return;
    }

    private ProjectResponseDto convertToDto(ProjectEntity project) {
        ProjectResponseDto projectResponseDto = modelMapper.map(project, ProjectResponseDto.class);
        TranslationEntity translationEntity = translationRepository.findByParentGuidAndFieldAndLanguage(projectResponseDto.getProjectGuid(), "description", LocaleContextHolder.getLocale().getDisplayLanguage());

        projectResponseDto.setDescription(translationEntity.getContent());
        return projectResponseDto;
    }

    @RequestMapping(value = "/getProjects", method = RequestMethod.GET)
    public Page<ProjectResponseDto> getProjects(Pageable pageable) {
        int totalElements = 0;
        List<ProjectResponseDto> projectsResponseDto = new ArrayList<ProjectResponseDto>();
        Page<ProjectEntity> projects = projectRepository.findAll(pageable);

        if(projects != null){
            totalElements = projects.getNumberOfElements();
            for(ProjectEntity project : projects){
                ProjectResponseDto projectResponseDto = this.convertToDto(project);
                projectsResponseDto.add(projectResponseDto);
            }
        }

        return new PageImpl<>(projectsResponseDto, pageable, totalElements);
    }
}
