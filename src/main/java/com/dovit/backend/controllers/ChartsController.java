package com.dovit.backend.controllers;

import com.dovit.backend.annotations.IsAuthenticated;
import com.dovit.backend.services.ChartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ramón París
 * @since 28-06-20
 */
@RestController
@IsAuthenticated
@RequiredArgsConstructor
@RequestMapping("/api/company/{companyId}")
public class ChartsController {

  private final ChartService chartService;

  @GetMapping("/chart/seniors")
  public ResponseEntity<?> findTopSeniorMembers(@PathVariable Long companyId) {
    return ResponseEntity.ok(chartService.findTopSeniorMembers(companyId));
  }

  /** Returns a list of members that has worked on most projects */
  @GetMapping("/chart/workers")
  public ResponseEntity<?> findTopWorkerMembers(@PathVariable Long companyId) {
    return ResponseEntity.ok(chartService.findTopWorkers(companyId));
  }

  @GetMapping("/chart/members/tools")
  public ResponseEntity<?> findTopToolsByMembers(@PathVariable Long companyId) {
    return ResponseEntity.ok(chartService.findTopMemberTools(companyId));
  }

  @GetMapping("/chart/categories/members")
  public ResponseEntity<?> findQtyMemberByCategory(@PathVariable Long companyId) {
    return ResponseEntity.ok(chartService.findQtyMemberByCategory(companyId));
  }

  @GetMapping("/chart/projects/tools")
  public ResponseEntity<?> findTopProjectTools(@PathVariable Long companyId) {
    return ResponseEntity.ok(chartService.findTopToolsByProject(companyId));
  }

  @GetMapping("/chart/projects/types")
  public ResponseEntity<?> findTopProjectTypes(@PathVariable Long companyId) {
    return ResponseEntity.ok(chartService.findTopProjectTypes(companyId));
  }

  @GetMapping("/chart/licenses/expiring")
  public ResponseEntity<?> findLicensesExpiring(@PathVariable Long companyId) {
    return ResponseEntity.ok(chartService.findLicensesExpiring(companyId));
  }

  @GetMapping("/chart/licenses/expired")
  public ResponseEntity<?> findLicensesExpired(@PathVariable Long companyId) {
    return ResponseEntity.ok(chartService.findLicensesExpired(companyId));
  }

  @GetMapping("/chart/licenses/actives")
  public ResponseEntity<?> findLicensesActives(@PathVariable Long companyId) {
    return ResponseEntity.ok(chartService.findLicensesActives(companyId));
  }
}
