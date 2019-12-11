package com.dovit.backend.services;

import com.dovit.backend.domain.*;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.model.requests.MemberRequest;
import com.dovit.backend.model.requests.ToolProfileRequest;
import com.dovit.backend.model.responses.MemberResponse;
import com.dovit.backend.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
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
    private final ProfileRepository profileRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Value("${api.image.route}")
    private String BASE_IMAGE_URL;

    @Override
    @Transactional
    public Member save(MemberRequest member) {
        Member mappedMember = mapMemberRequestToMember(member);
        Member saved = memberRepository.save(mappedMember);
        saved.getToolProfile().forEach(toolProfile -> toolProfile.setMemberId(saved.getId()));
        List<ToolProfile> toolProfiles = toolProfileRepository.saveAll(saved.getToolProfile());
        return saved;
    }

    private Member mapMemberRequestToMember(MemberRequest memberRequest) {

        Member member = modelMapper.map(memberRequest, Member.class);

        mapToolProfile(memberRequest, member);

        member.setProfiles(profileRepository.findAllByIdIn(memberRequest.getProfiles()));

        return member;
    }

    @Override
    @Transactional
    public MemberResponse findById(Long memberId) throws ResourceNotFoundException {
        Member member = memberRepository
                .findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member", "MemberId", memberId));

        return new MemberResponse(member, BASE_IMAGE_URL);
    }

    @Override
    @Transactional
    public List<MemberResponse> findAllByCompanyId(Long companyId) throws ResourceNotFoundException {
        return memberRepository.findAllByCompanyId(companyId).stream().map(m->new MemberResponse(m, BASE_IMAGE_URL)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Member update(MemberRequest memberRequest) {
        toolProfileRepository.deleteAllByMemberId(memberRequest.getId());

        Member member = memberRepository
                .findById(memberRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Member", "MemberId", memberRequest.getId()));

        member.setName(memberRequest.getName());
        member.setLastName(memberRequest.getLastName());
        member.setActive(memberRequest.getActive());

        List<Profile> profiles = profileRepository.findAllByIdIn(memberRequest.getProfiles());
        member.setProfiles(profiles);

        mapToolProfile(memberRequest, member);

        member.getToolProfile().forEach(toolProfile -> toolProfile.setMemberId(memberRequest.getId()));

        toolProfileRepository.saveAll(member.getToolProfile());
        member = memberRepository.save(member);
        return member;
    }

    private void mapToolProfile(MemberRequest memberRequest, Member member) {
        List<Long> levelsIds =
                memberRequest.getToolProfile().stream()
                        .map(ToolProfileRequest::getLevelId)
                        .collect(Collectors.toList());

        List<Long> toolsIds =
                memberRequest.getToolProfile().stream()
                        .map(ToolProfileRequest::getToolId)
                        .collect(Collectors.toList());

        List<Level> levels = levelRepository.findAllById(levelsIds);
        List<Tool> tools = toolRepository.findAllById(toolsIds);

        List<ToolProfile> collect =
                memberRequest.getToolProfile().stream()
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
    }
}
