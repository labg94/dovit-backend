package com.dovit.backend.services;

import com.dovit.backend.payloads.responses.charts.ChartTopSeniorMemberResponse;

import java.util.List;

/**
 * @author Ramón París
 * @since 28-06-20
 */
public interface ChartService {

    List<ChartTopSeniorMemberResponse> findTopSeniorMembers(Long companyId);


}
