package com.dovit.backend.repositories;

import com.dovit.backend.model.RecommendationPointsDTO;
import com.dovit.backend.model.ToolRecommendationDTO;
import com.dovit.backend.payloads.responses.MemberResponseResume;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

import static com.dovit.backend.util.Constants.*;

/**
 * @author Ramón París
 * @since 14-10-2019
 */
@Repository
@RequiredArgsConstructor
public class CustomRepositoryImpl implements CustomRepository {

  private final JdbcTemplate jdbcTemplate;

  @Override
  public List<MemberResponseResume> findAllMembersResumeByCompanyId(Long companyId) {
    String SQL =
        "SELECT members.*, "
            + "       case "
            + "           when members.active_projects_qty = 0 then 1 "
            + "           when members.active_projects_qty = 1 then 2 "
            + "           when members.active_projects_qty > 2 then 3 "
            + "           end as available_status_id, "
            + "       case "
            + "           when members.active_projects_qty > 2 then 'Busy' "
            + "           when members.active_projects_qty = 1 then 'Partially available' "
            + "           when members.active_projects_qty = 0 then 'Available' "
            + "           end as available_status_description "
            + "FROM ( "
            + "         SELECT m.id                                      as member_id, "
            + "                m.name                                    as member_name, "
            + "                m.last_name                               as member_lastname, "
            + "                m.active                                  as member_active, "
            + "                c.company_id                              as company_id, "
            + "                c.name                                    as company_name, "
            + "                string_agg(distinct p2.description, ', ') as profiles, "
            + "                count(case "
            + "                          when not p.finished then 1 "
            + "                    end)                                  as active_projects_qty, "
            + "                count(p.id)                               as all_projects_qty "
            + "         FROM members m "
            + "                  left join project_members pm on m.id = pm.member_id "
            + "                  left join project p on pm.project_id = p.id "
            + "                  join companies c on m.company_company_id = c.company_id "
            + "                                  and c.company_id = ? "
            + "                  join member_profiles mp on m.id = mp.member_id "
            + "                  left join profile p2 on mp.profile_id = p2.id "
            + "         group by m.id, m.name, m.last_name, c.company_id, c.name, m.active "
            + "     ) AS members";
    return jdbcTemplate.query(
        SQL,
        new Object[] {companyId},
        ((resultSet, i) ->
            MemberResponseResume.builder()
                .id(resultSet.getLong("member_id"))
                .memberName(resultSet.getString("member_name"))
                .memberLastName(resultSet.getString("member_lastname"))
                .active(resultSet.getBoolean("member_active"))
                .companyId(resultSet.getLong("company_id"))
                .companyName(resultSet.getString("company_name"))
                .profiles(resultSet.getString("profiles"))
                .allProjectsQty(resultSet.getLong("all_projects_qty"))
                .activeProjectsQty(resultSet.getLong("active_projects_qty"))
                .availableStatusId(resultSet.getLong("available_status_id"))
                .availableStatusDescription(resultSet.getString("available_status_description"))
                .build()));
  }

  @Override
  public List<ToolRecommendationDTO> findRecommendationByCompanyLicense(
      Long companyId, Long categoryId) {

    String SQL =
        "SELECT distinct t.tool_id as tool_id, t.name as tool_name, t.description as tool_description, "
            + "                max(cl.start_date) as license_start_date "
            + "FROM company_licenses cl "
            + "         join licenses l on cl.license_id = l.license_id "
            + "                        and l.active = true "
            + "         join tools t on l.tool_id = t.tool_id "
            + "                        and t.active = true "
            + "         join tool_subcategory ts on t.tool_id = ts.tool_id "
            + "         join devops_subcategories ds on ts.subcategory_id = ds.devops_subcategory_id "
            + "                        and ds.active = true "
            + "         join devops_categories dc on ds.devops_category_id = dc.id "
            + "                        and dc.active = true "
            + "    and dc.id = ? "
            + "where cl.company_id = ? "
            + "  and ((cl.expiration_date is null and now() >= cl.start_date) "
            + "    or (now() between cl.start_date and cl.expiration_date)) "
            + "group by t.tool_id, t.name, t.description ";

    return jdbcTemplate.query(
        SQL,
        new Object[] {categoryId, companyId},
        (rs, rowNum) ->
            ToolRecommendationDTO.builder()
                .toolId(rs.getLong("tool_id"))
                .toolName(rs.getString("tool_name"))
                .toolDescription(rs.getString("tool_description"))
                .points(
                    Collections.singletonList(
                        RecommendationPointsDTO.builder()
                            .category(TOOL_POINTS_LICENSE_TXT)
                            .points(TOOL_POINTS_LICENSE)
                            .build()))
                .build());
  }

  @Override
  public List<ToolRecommendationDTO> findRecommendationByProjectType(
      Long categoryId, String projectTypeIds) {
    String SQL =
        "SELECT distinct t.tool_id as tool_id, t.name as tool_name, t.description as tool_description "
            + "FROM tools t "
            + "         join tool_project_type tpt on t.tool_id = tpt.tool_id "
            + "    and tpt.project_type_id in ("
            + projectTypeIds
            + ") "
            + "         join tool_subcategory ts on t.tool_id = ts.tool_id "
            + "         join devops_subcategories ds on ts.subcategory_id = ds.devops_subcategory_id "
            + "    and ds.active = true "
            + "         join devops_categories dc on ds.devops_category_id = dc.id "
            + "    and dc.id = ? "
            + "    and dc.active = true "
            + "where t.active = true";

    return jdbcTemplate.query(
        SQL,
        new Object[] {categoryId},
        (rs, rowNum) ->
            ToolRecommendationDTO.builder()
                .toolId(rs.getLong("tool_id"))
                .toolName(rs.getString("tool_name"))
                .toolDescription(rs.getString("tool_description"))
                .points(
                    Collections.singletonList(
                        RecommendationPointsDTO.builder()
                            .category(TOOL_POINTS_PROJECT_TYPE_TXT)
                            .points(TOOL_POINTS_PROJECT_TYPE)
                            .build()))
                .build());
  }

  @Override
  public List<ToolRecommendationDTO> findRecommendationByProjectHistory(
      Long categoryId, Long companyId) {
    String SQL =
        "SELECT distinct t.tool_id     as tool_id, "
            + "                t.name        as tool_name, "
            + "                t.description as tool_description, "
            + "                max(project.start) "
            + "FROM project "
            + "         join pipeline p on project.id = p.project_id "
            + "    and p.recommended = false "
            + "         join pipeline_tool pt on p.id = pt.pipeline_id "
            + "    and pt.category_id = ? "
            + "         join tools t on pt.tool_id = t.tool_id "
            + "    and t.active = true "
            + "where project.company_company_id = ? "
            + "group by t.tool_id, t.name, t.description";

    return jdbcTemplate.query(
        SQL,
        new Object[] {categoryId, companyId},
        (rs, rowNum) ->
            ToolRecommendationDTO.builder()
                .toolId(rs.getLong("tool_id"))
                .toolName(rs.getString("tool_name"))
                .toolDescription(rs.getString("tool_description"))
                .points(
                    Collections.singletonList(
                        RecommendationPointsDTO.builder()
                            .category(TOOL_POINTS_HISTORY_TXT)
                            .points(TOOL_POINTS_HISTORY)
                            .build()))
                .build());
  }

  @Override
  public List<ToolRecommendationDTO> findRecommendationByMembers(
      Long categoryId, String membersId) {
    String SQL =
        "SELECT distinct t.tool_id     as tool_id, "
            + "                t.name        as tool_name, "
            + "                t.description as tool_description, "
            + "                max(l.points) as level_points "
            + "FROM tools t "
            + "         join tool_profile tp on t.tool_id = tp.tool_id "
            + "    and member_id in ("
            + membersId
            + ")"
            + "         join level l on tp.level_id = l.level_id "
            + "         join tool_subcategory ts on t.tool_id = ts.tool_id "
            + "         join devops_subcategories ds on ts.subcategory_id = ds.devops_subcategory_id "
            + "    and ds.active = true "
            + "         join devops_categories dc on ds.devops_category_id = dc.id "
            + "    and dc.active = true "
            + "    and dc.id = ? "
            + "where t.active = true "
            + "group by t.tool_id, t.name, t.description";

    return jdbcTemplate.query(
        SQL,
        new Object[] {categoryId},
        (rs, rowNum) ->
            ToolRecommendationDTO.builder()
                .toolId(rs.getLong("tool_id"))
                .toolName(rs.getString("tool_name"))
                .toolDescription(rs.getString("tool_description"))
                .points(
                    Collections.singletonList(
                        RecommendationPointsDTO.builder()
                            //
                            // .category(this.findMemberPointsTxt(rs.getInt("level_points")))
                            .points(rs.getInt("level_points"))
                            .build()))
                .build());
  }
}
