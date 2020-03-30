package com.dovit.backend.services;

import com.dovit.backend.domain.License;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.model.LicenseDTO;
import com.dovit.backend.model.responses.LicensePricingResponse;
import com.dovit.backend.repositories.LicenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ramón París
 * @since 01-10-2019
 */
@Service
@RequiredArgsConstructor
public class LicenseServiceImpl implements LicenseService {

    private final LicenseRepository licenseRepository;

    @Override
    @Transactional
    public List<LicenseDTO> findAllByToolId(Long toolId) {
        List<License> licenses = licenseRepository.findAllByToolId(toolId);
        return licenses.stream()
                .map(
                        l -> {
                            LicenseDTO licenseDTO = new LicenseDTO();
                            licenseDTO.setLicenseId(l.getId());
                            licenseDTO.setLicenseDesc(l.getName());
                            licenseDTO.setObservation(l.getObservation());
                            licenseDTO.setPayCycle(l.getPayCycle());
                            licenseDTO.setLicenseTypeId(l.getLicenseType().getId());
                            licenseDTO.setLicenseTypeDesc(l.getLicenseType().getDescription());
                            licenseDTO.setLicensePricing(
                                    l.getPricings().stream()
                                            .map(
                                                    p ->
                                                            new LicensePricingResponse(
                                                                    p.getId(), p.getMinUsers(), p.getMaxUsers(), p.getPrice()))
                                            .collect(Collectors.toList()));
                            return licenseDTO;
                        })
                .collect(Collectors.toList());
    }

    @Override
    public License findById(Long licenseId) {
        return licenseRepository
                .findById(licenseId)
                .orElseThrow(() -> new ResourceNotFoundException("License", "id", licenseId));
    }
}
