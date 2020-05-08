package com.dovit.backend.services;

import com.dovit.backend.config.ModelMapperConfig;
import com.dovit.backend.repositories.DevOpsCategoryRepository;
import com.dovit.backend.repositories.MemberRepository;
import com.dovit.backend.repositories.ProjectMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Ramón París
 * @since 07-05-20
 */
@ExtendWith(SpringExtension.class)
class ProjectServiceImplTest {

  @InjectMocks ProjectServiceImpl projectService;
  @Mock ProjectMemberRepository projectMemberRepository;
  @Mock MemberRepository memberRepository;
  @Mock DevOpsCategoryRepository devOpsCategoryRepository;
  @Spy ModelMapper modelMapper = new ModelMapperConfig().modelMapper();

  @AfterEach
  void tearDown(TestInfo info) {
    if (!info.getTags().contains("SkipAfter")) modelMapper.validate();
  }

  @Test
  void saveProject() {}

  @Test
  void updateProject() {}

  @Test
  void findAllByCompanyId() {}

  @Test
  void findByProjectId() {}

  @Test
  void findMemberRecommendation() {}
}
