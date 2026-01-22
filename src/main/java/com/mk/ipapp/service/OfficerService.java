package com.mk.ipapp.service;

import com.mk.ipapp.dto.officer.OfficerCreateRequest;
import com.mk.ipapp.dto.officer.OfficerUpdateRequest;
import com.mk.ipapp.dto.UserSummary;

import java.util.List;

public interface OfficerService {

    UserSummary createOfficer(OfficerCreateRequest request);

    UserSummary updateOfficer(Long id, OfficerUpdateRequest request);

    void activateOfficer(Long id);
    void deactivateOfficer(Long officerId);

    List<UserSummary> listOfficers(Long regionId); // nullable regionId: all officers
}
