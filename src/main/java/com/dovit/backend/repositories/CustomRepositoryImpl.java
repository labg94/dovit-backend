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
}
