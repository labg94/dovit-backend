package com.dovit.backend.services;

import com.dovit.backend.domain.Level;
import com.dovit.backend.domain.Member;
import com.dovit.backend.domain.Tool;
import com.dovit.backend.domain.ToolProfile;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.model.requests.MemberRequest;
import com.dovit.backend.model.requests.ToolProfileRequest;
import com.dovit.backend.repositories.LevelRepository;
import com.dovit.backend.repositories.MemberRepository;
import com.dovit.backend.repositories.ToolProfileRepository;
import com.dovit.backend.repositories.ToolRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

  private final MemberRepository memberRepository;
  private final LevelRepository levelRepository;
  private final ToolRepository toolRepository;
  private final ToolProfileRepository toolProfileRepository;
  private final ModelMapper modelMapper = new ModelMapper();

  @Override
  @Transactional
  public Member agregar(MemberRequest member) {
    Member mappedMember = toMember(member);

    log.info("mappedMember {}", mappedMember);
    Member save = memberRepository.save(mappedMember);
    save.getToolProfile().forEach(toolProfile -> toolProfile.setMemberId(save.getId()));
    List<ToolProfile> toolProfiles = toolProfileRepository.saveAll(save.getToolProfile());

    log.info("MemberServiceImpl - agregar toolProfiles  toolProfiles {}", toolProfiles);
    return save;
  }

  private Member toMember(MemberRequest memberRequest) {

    Member member = modelMapper.map(memberRequest, Member.class);

    List<Long> levelsIds =
        memberRequest.getToolProfileRequest().stream()
            .map(ToolProfileRequest::getLevelId)
            .collect(Collectors.toList());

    List<Long> toolsIds =
        memberRequest.getToolProfileRequest().stream()
            .map(ToolProfileRequest::getToolId)
            .collect(Collectors.toList());

    List<Level> levels = levelRepository.findAllById(levelsIds);
    List<Tool> tools = toolRepository.findAllById(toolsIds);

    List<ToolProfile> collect =
        memberRequest.getToolProfileRequest().stream()
            .map(
                toolProfileRequest -> {
                  Level levelGotten =
                      levels.stream()
                          .filter(
                              level -> level.getLevelId().equals(toolProfileRequest.getLevelId()))
                          .findFirst()
                          .orElseGet(Level::new);

                  Tool toolGotten =
                      tools.stream()
                          .filter(tool -> tool.getId().equals(toolProfileRequest.getToolId()))
                          .findFirst()
                          .orElseGet(Tool::new);

                  ToolProfile toolProfile = new ToolProfile();
                  toolProfile.setLevel(levelGotten);
                  toolProfile.setLevelId(levelGotten.getLevelId());
                  toolProfile.setMember(member);
                  toolProfile.setTool(toolGotten);
                  toolProfile.setToolId(toolGotten.getId());

                  return toolProfile;
                })
            .collect(Collectors.toList());

    member.setToolProfile(collect);
    return member;
  }


  @Override
  public Member findById(Long memberId, Long companyId) throws ResourceNotFoundException {
      Member member = memberRepository
              .findByIdAndCompanyId(memberId,companyId)
              .orElseThrow(() -> new ResourceNotFoundException("Member", "MemberId",memberId));
//TODO: RAMON TERMINA ESTA WEA SOY TURISTA D: es buscar los miembros por memberId y companyId

//TODO: dude no utilices Autowirde, con el RequiredArgsConstructor se inyectan als dependcnecias <3


        return null;
  }
}
