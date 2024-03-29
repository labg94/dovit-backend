package com.dovit.backend.services;

import com.dovit.backend.domain.*;
import com.dovit.backend.model.LicenseCategoryHelperDTO;
import com.dovit.backend.model.MemberKnowledgeHelperDTO;
import com.dovit.backend.payloads.responses.MemberResponseResume;
import com.dovit.backend.payloads.responses.charts.*;
import com.dovit.backend.repositories.*;
import com.dovit.backend.util.ValidatorUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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
  private final DevOpsCategoryRepository devOpsCategoryRepository;
  private final ProjectTypeRepository projectTypeRepository;
  private final CompanyLicenseRepository companyLicenseRepository;
  private final ProjectRepository projectRepository;
  private final ModelMapper modelMapper;
  private final ValidatorUtil validatorUtil;

  @Override
  @Transactional
  public List<ChartTopSeniorMemberResponse> findTopSeniorMembers(Long companyId) {
    validatorUtil.canActOnCompany(companyId);
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
            .map(this::mapMemberToChartSeniorMemberResponse)
            .collect(Collectors.toList());

    // Agregando todas las categorías siempre
    List<DevOpsCategory> devOpsCategories = devOpsCategoryRepository.findAllByActiveOrderById(true);
    charts.forEach(chart -> setMissingCategoriesInSeniorChart(devOpsCategories, chart));

    charts.stream()
        .map(ChartTopSeniorMemberResponse::getKnowledgeList)
        .flatMap(Collection::stream)
        .map(ChartMemberKnowledge::getValue)
        .max(Integer::compareTo)
        .ifPresent(max -> charts.forEach(chart -> chart.setMaxValue(max)));

    return charts;
  }

  @Override
  public ChartTopSeniorMemberResponse findMemberKnowledgeById(Long memberId) {
    final Optional<Member> memberEntity = memberRepository.findById(memberId);
    memberEntity.ifPresent(member -> validatorUtil.canActOnCompany(member.getCompany().getId()));
    List<DevOpsCategory> devOpsCategories = devOpsCategoryRepository.findAllByActiveOrderById(true);

    return memberEntity
        .map(this::mapMemberToChartSeniorMemberResponse)
        .map(
            chart -> {
              setMissingCategoriesInSeniorChart(devOpsCategories, chart);
              chart.getKnowledgeList().stream()
                  .map(ChartMemberKnowledge::getValue)
                  .max(Integer::compareTo)
                  .ifPresent(chart::setMaxValue);
              return chart;
            })
        .orElseThrow();
  }

  @Override
  public List<ChartMemberProjectQty> findTopWorkers(Long companyId) {
    validatorUtil.canActOnCompany(companyId);
    final List<MemberResponseResume> members =
        customRepository.findAllMembersResumeByCompanyId(companyId, true);
    return members.stream()
        .map(
            member ->
                ChartMemberProjectQty.builder()
                    .fullName(
                        String.format(
                            "%s. %s", member.getMemberName().charAt(0), member.getMemberLastName()))
                    .activeProjectsQty(member.getActiveProjectsQty())
                    .closedProjectsQty(member.getAllProjectsQty() - member.getActiveProjectsQty())
                    .id(member.getId())
                    .build())
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public List<ChartTopToolsByMembersResponse> findTopMemberTools(Long companyId) {
    validatorUtil.canActOnCompany(companyId);
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

  @Override
  @Transactional
  public List<ChartMemberByCategory> findQtyMemberByCategory(Long companyId) {
    validatorUtil.canActOnCompany(companyId);
    final List<DevOpsCategory> categories = devOpsCategoryRepository.findAllByActiveOrderById(true);
    return categories.stream()
        .map(
            category -> {
              final long qty =
                  category.getSubcategories().stream()
                      .map(DevOpsSubcategory::getTools)
                      .flatMap(Collection::stream)
                      .distinct()
                      .filter(
                          tool -> tool.getToolProfile() != null && !tool.getToolProfile().isEmpty())
                      .map(Tool::getToolProfile)
                      .flatMap(Collection::stream)
                      .map(ToolProfile::getMember)
                      .filter(member -> member.getCompany().getId().equals(companyId))
                      .distinct()
                      .count();
              return ChartMemberByCategory.builder()
                  .id(category.getId())
                  .description(category.getDescription())
                  .value(qty)
                  .build();
            })
        .collect(Collectors.toList());
  }

  @Override
  public List<ChartTopToolsByProject> findTopToolsByProject(Long companyId) {
    validatorUtil.canActOnCompany(companyId);
    return customRepository.findTopToolsByProject(companyId);
  }

  @Override
  @Transactional
  public List<ChartTopProjectTypes> findTopProjectTypes(Long companyId) {
    validatorUtil.canActOnCompany(companyId);
    Pageable pageable = PageRequest.of(0, 5);
    Page<Object[]> page = projectTypeRepository.findTopProjectTypes(pageable, companyId);

    return page.get()
        .map(
            objects -> {
              ProjectType projectType = (ProjectType) objects[0];
              return ChartTopProjectTypes.builder()
                  .description(projectType.getDescription())
                  .projectTypeId(projectType.getId())
                  .value((Long) objects[1])
                  .build();
            })
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public List<ChartLicenseResponse> findLicensesExpired(Long companyId) {
    validatorUtil.canActOnCompany(companyId);
    final List<CompanyLicense> licenses =
        companyLicenseRepository.findLicensesExpired(companyId, LocalDate.now());

    return licenses.stream()
        .map(license -> modelMapper.map(license, ChartLicenseResponse.class))
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public List<ChartLicenseResponse> findLicensesExpiring(Long companyId) {
    validatorUtil.canActOnCompany(companyId);
    final List<CompanyLicense> licenses =
        companyLicenseRepository.findLicensesExpiring(companyId, LocalDate.now());

    return licenses.stream()
        .map(license -> modelMapper.map(license, ChartLicenseResponse.class))
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public List<ChartLicenseResponse> findLicensesActives(Long companyId) {
    validatorUtil.canActOnCompany(companyId);
    final List<CompanyLicense> licenses =
        companyLicenseRepository.findLicensesActives(companyId, LocalDate.now());

    return licenses.stream()
        .map(license -> modelMapper.map(license, ChartLicenseResponse.class))
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public List<ChartLicenseConflict> findLicensesConflicts(Long companyId) {
    validatorUtil.canActOnCompany(companyId);
    final List<CompanyLicense> licenses =
        companyLicenseRepository.findLicensesActives(companyId, LocalDate.now());
    final List<ChartLicenseConflict> charts =
        licenses.stream()
            .map(
                companyLicense ->
                    companyLicense.getLicense().getTool().getSubcategories().stream()
                        .map(DevOpsSubcategory::getDevOpsCategory)
                        .distinct()
                        .map(
                            devOpsCategory ->
                                LicenseCategoryHelperDTO.builder()
                                    .companyLicense(companyLicense)
                                    .devOpsCategory(devOpsCategory)
                                    .build())
                        .collect(Collectors.toList()))
            .flatMap(Collection::stream)
            .collect(Collectors.groupingBy(LicenseCategoryHelperDTO::getDevOpsCategory))
            .entrySet()
            .stream()
            .map(
                entrySet -> {
                  DevOpsCategory category = entrySet.getKey();
                  final int qty = entrySet.getValue().size();

                  return ChartLicenseConflict.builder()
                      .categoryId(category.getId())
                      .category(category.getDescription())
                      .qty(qty)
                      .tools(
                          entrySet.getValue().stream()
                              .map(
                                  licenseCategoryHelperDTO ->
                                      licenseCategoryHelperDTO
                                          .getCompanyLicense()
                                          .getLicense()
                                          .getTool()
                                          .getName())
                              .collect(Collectors.joining(", ")))
                      .message(getLicenseConflictMessage(qty))
                      .build();
                })
            .collect(Collectors.toList());

    final List<DevOpsCategory> categories = devOpsCategoryRepository.findAllByActiveOrderById(true);
    final List<Long> categoryIds =
        charts.stream().map(ChartLicenseConflict::getCategoryId).collect(Collectors.toList());
    final List<ChartLicenseConflict> emptyCategories =
        categories.stream()
            .filter(category -> !categoryIds.contains(category.getId()))
            .map(
                category ->
                    ChartLicenseConflict.builder()
                        .categoryId(category.getId())
                        .tools("")
                        .message(this.getLicenseConflictMessage(0))
                        .qty(0)
                        .category(category.getDescription())
                        .build())
            .collect(Collectors.toList());

    charts.addAll(emptyCategories);
    return charts.stream()
        .sorted(Comparator.comparing(ChartLicenseConflict::getCategoryId))
        .collect(Collectors.toList());
  }

  @Override
  public ChartProjectQty findProjectQty(Long companyId) {
    validatorUtil.canActOnCompany(companyId);
    final List<Project> projects = projectRepository.findAllByCompanyId(companyId);
    return ChartProjectQty.builder()
        .allQty(projects.size())
        .activesQty((int) projects.stream().filter(project -> !project.getFinished()).count())
        .build();
  }

  private String getLicenseConflictMessage(int qty) {
    switch (qty) {
      case 0:
        return "There are not licenses for this category";
      case 1:
        return "OK";
      default:
        return "It seems that there are similar licenses active. Check if it is possible to use only one";
    }
  }

  private void setMissingCategoriesInSeniorChart(
      List<DevOpsCategory> devOpsCategories, ChartTopSeniorMemberResponse chart) {
    final List<Long> categoryIds =
        chart.getKnowledgeList().stream()
            .map(ChartMemberKnowledge::getCategoryId)
            .collect(Collectors.toList());

    final List<ChartMemberKnowledge> otherKnowledge =
        devOpsCategories.stream()
            .filter(category -> !categoryIds.contains(category.getId()))
            .map(
                category ->
                    ChartMemberKnowledge.builder()
                        .categoryId(category.getId())
                        .category(category.getDescription())
                        .value(0)
                        .tools("")
                        .build())
            .collect(Collectors.toList());

    chart.getKnowledgeList().addAll(otherKnowledge);
    chart.setKnowledgeList(
        chart.getKnowledgeList().stream()
            .sorted(Comparator.comparing(ChartMemberKnowledge::getCategoryId))
            .collect(Collectors.toList()));
  }

  private ChartTopSeniorMemberResponse mapMemberToChartSeniorMemberResponse(Member member) {
    final String fullName = String.format("%s %s", member.getName(), member.getLastName());

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
            .collect(Collectors.groupingBy(MemberKnowledgeHelperDTO::getDevOpsCategory))
            .entrySet()
            .stream()
            .map(
                entrySet ->
                    ChartMemberKnowledge.builder()
                        .categoryId(entrySet.getKey().getId())
                        .category(entrySet.getKey().getDescription())
                        .value(
                            entrySet.getValue().stream()
                                .map(value -> value.getToolProfile().getLevel().getPoints())
                                .reduce(0, Integer::sum))
                        .tools(
                            entrySet.getValue().stream()
                                .map(tool -> tool.getToolProfile().getTool().getName())
                                .collect(Collectors.joining(", ")))
                        .build())
            .collect(Collectors.toList());
    return ChartTopSeniorMemberResponse.builder()
        .id(member.getId())
        .fullName(fullName)
        .knowledgeList(knowledgeList)
        .build();
  }
}
