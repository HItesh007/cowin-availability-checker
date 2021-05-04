package com.hitesh.cowin.freemarker.model;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class VaccinationCenterModel {

    private final String centerName;
    private final String districtName;
    private final int postalCode;
    private final String feeType;
    private final int minAgeLimit;
    private final List<SessionModel> sessionModelList;

    private VaccinationCenterModel(String name, String district, int pinCode, String feeType, int minAgeLimit, List<SessionModel> sessionList) {
        this.centerName = StringUtils.normalizeSpace(name);
        this.districtName = StringUtils.normalizeSpace(district);
        this.postalCode = pinCode;
        this.feeType = StringUtils.normalizeSpace(feeType);
        this.minAgeLimit = minAgeLimit;
        this.sessionModelList = sessionList;
    }

    public static VaccinationCenterModel with(String name, String district, int pinCode, String feeType, int minAgeLimit, List<SessionModel> sessionList) {
        return new VaccinationCenterModel(name, district, pinCode, feeType, minAgeLimit, sessionList);
    }

    public String getCenterName() {
        return centerName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public int getZipCode() {
        return postalCode;
    }

    public String getFeeType() {
        return feeType;
    }

    public int getMinAgeLimit() {
        return minAgeLimit;
    }

    public List<SessionModel> getSessionModelList() {
        return sessionModelList;
    }
}
