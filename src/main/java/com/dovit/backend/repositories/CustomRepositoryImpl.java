package com.dovit.backend.repositories;

import com.dovit.backend.payloads.responses.MemberResponseResume;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ramón París
 * @since 14-10-2019
 */
@Repository
@RequiredArgsConstructor
public class CustomRepositoryImpl implements CustomRepository {

  private final JdbcTemplate jdbcTemplate;

  @Override
  public List<MemberResponseResume> findAllMembersResumeByCompanyId(Long companyId, boolean limit) {
    // language=PostgreSQL
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
            + "                coalesce(pm.active_projects_qty,0)                    as active_projects_qty, "
            + "                coalesce(pm.all_projects_qty,0)                      as all_projects_qty "
            + "         FROM members m "
            + "                  left join (select project_qty.member_id, "
            + "                                    count(project_qty.project_id) all_projects_qty, "
            + "                                    count(case "
            + "                                              when not project_qty.finished then 1 "
            + "                                        end) as                   active_projects_qty "
            + "                             from ( "
            + "                                      select member_id, project_id, p.finished "
            + "                                      from project_members "
            + "                                               join project p on project_members.project_id = p.id "
            + "                                      group by member_id, project_id, p.finished) as project_qty "
            + "                             group by project_qty.member_id) pm on pm.member_id = m.id "
            + "                  join companies c on m.company_company_id = c.company_id "
            + "             and c.company_id = ? "
            + "                  join member_profiles mp on m.id = mp.member_id "
            + "                  left join profile p2 on mp.profile_id = p2.id "
            + "         group by m.id, m.name, m.last_name, c.company_id, c.name, m.active, pm.active_projects_qty, pm.all_projects_qty "
            + "     ) AS members "
            + "order by members.all_projects_qty desc ";
    if (limit) {
      SQL += " limit 5";
    }
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
}
