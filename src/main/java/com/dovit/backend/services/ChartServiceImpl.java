package com.dovit.backend.services;

import com.dovit.backend.domain.DevOpsSubcategory;
import com.dovit.backend.domain.Member;
import com.dovit.backend.domain.Tool;
import com.dovit.backend.model.MemberKnowledgeHelperDTO;
import com.dovit.backend.payloads.responses.MemberResponseResume;
import com.dovit.backend.payloads.responses.charts.ChartMemberKnowledge;
import com.dovit.backend.payloads.responses.charts.ChartTopSeniorMemberResponse;
import com.dovit.backend.payloads.responses.charts.ChartTopToolsByMembersResponse;
import com.dovit.backend.repositories.CustomRepository;
import com.dovit.backend.repositories.MemberRepository;
import com.dovit.backend.repositories.ToolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ramón París
 * @since 28-06-20
 */
@Service
@RequiredArgsConstructor
public class ChartServiceImpl implements ChartService {

  private final MemberRepository memberRepository;
  private final CustomRepository customRepository;
  private final ToolRepository toolRepository;

  @Override
  @Transactional
  public List<ChartTopSeniorMemberResponse> findTopSeniorMembers(Long companyId) {
    Pageable pageable = PageRequest.of(0, 3);
    final Page<Object[]> page = memberRepository.findTopSeniorMembers(companyId, pageable);

    // Se tuvo que hacer este workaround porque el método findTopSeniorMembers
    // retorna un Object[] con dos items dentro: El member y la suma total. Así forzamos que siempre
    // tome el member
    final List<Member> topMembers =
        page.getContent().stream()
            .map(content -> content[0])
            .map(object -> (Member) object)
            .collect(Collectors.toList());
    final List<ChartTopSeniorMemberResponse> charts =
        topMembers.stream()
            .map(
                member -> {
                  final String fullName =
                      String.format("%s %s", member.getName(), member.getLastName());

                  final List<ChartMemberKnowledge> knowledgeList =
                      member.getToolProfile().stream()
                          .map(
                              toolProfile ->
                                  toolProfile.getTool().getSubcategories().stream()
                                      .map(DevOpsSubcategory::getDevOpsCategory)
                                      .distinct()
                                      .map(
                                          category ->
                                              MemberKnowledgeHelperDTO.builder()
                                                  .toolProfile(toolProfile)
                                                  .devOpsCategory(category)
                                                  .build())
                                      .collect(Collectors.toList()))
                          .flatMap(Collection::stream)
                          .collect(
                              Collectors.groupingBy(MemberKnowledgeHelperDTO::getDevOpsCategory))
                          .entrySet()
                          .stream()
                          .map(
                              entrySet ->
                                  ChartMemberKnowledge.builder()
                                      .category(entrySet.getKey().getDescription())
                                      .value(
                                          entrySet.getValue().stream()
                                              .map(
                                                  value ->
                                                      value.getToolProfile().getLevel().getPoints())
                                              .reduce(0, Integer::sum))
                                      .tools(
                                          entrySet.getValue().stream()
                                              .map(
                                                  tool -> tool.getToolProfile().getTool().getName())
                                              .collect(Collectors.joining(", ")))
                                      .build())
                          .collect(Collectors.toList());
                  return ChartTopSeniorMemberResponse.builder()
                      .id(member.getId())
                      .fullName(fullName)
                      .knowledgeList(knowledgeList)
                      .build();
                })
            .collect(Collectors.toList());

    charts.stream()
        .map(ChartTopSeniorMemberResponse::getKnowledgeList)
        .flatMap(Collection::stream)
        .map(ChartMemberKnowledge::getValue)
        .max(Integer::compareTo)
        .ifPresent(max -> charts.forEach(chart -> chart.setMaxValue(max)));

    return charts;
  }

  @Override
  public List<MemberResponseResume> findTopWorkers(Long companyId) {
    return customRepository.findAllMembersResumeByCompanyId(companyId, true);
  }

  @Override
  public List<ChartTopToolsByMembersResponse> findTopMemberTools(Long companyId) {
    Pageable pageable = PageRequest.of(0, 5);
    final Page<Object[]> topMembersTool = toolRepository.findTopMembersTool(pageable, companyId);
    return topMembersTool
        .get()
        .map(
            objects -> {
              Tool tool = (Tool) objects[0];
              return ChartTopToolsByMembersResponse.builder()
                  .id(tool.getId())
                  .name(tool.getName())
                  .value((Long) objects[1])
                  .build();
            })
        .collect(Collectors.toList());
  }
}
