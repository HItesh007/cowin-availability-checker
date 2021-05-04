package com.hitesh.cowin.freemarker.helper;

import com.hitesh.cowin.freemarker.model.SessionModel;
import com.hitesh.cowin.freemarker.model.VaccinationCenterModel;
import com.hitesh.cowin.json.JSONObject;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.List;

public class ResponseToModelTransformer {


    public static List<VaccinationCenterModel> convertToVaccinationCenterModel(JSONObject filteredObject) {

        List<VaccinationCenterModel> vaccinationCenterModelList = new ArrayList<>();

        filteredObject.getJSONArray("centers").forEach(object -> {
            JSONObject centerJObject = (JSONObject) object;

            // Get Data Of Centers
            Object centerName = centerJObject.get("name");
            Object district = centerJObject.get("district_name");
            Object pinCode = centerJObject.get("pincode");
            Object feeType = centerJObject.get("fee_type");
            Object minAgeLimit = centerJObject.get("min_age_limit");
            List<SessionModel> sessionModelList = new ArrayList<>();
            if (centerJObject.has("sessions")) {

                centerJObject.getJSONArray("sessions").forEach(sessionObject -> {
                    JSONObject sessionJObject = (JSONObject) sessionObject;

                    // Get Data of json
                    Object date = sessionJObject.get("date");
                    Object availableCapacity = sessionJObject.get("available_capacity");
                    Object minAge = sessionJObject.get("min_age_limit");

                    sessionModelList.add(SessionModel.with(
                            String.valueOf(date),
                            NumberUtils.toInt(availableCapacity.toString()),
                            NumberUtils.toInt(minAge.toString())));
                });
            }

            vaccinationCenterModelList.add(VaccinationCenterModel.with(
                    String.valueOf(centerName),
                    String.valueOf(district),
                    NumberUtils.toInt(pinCode.toString()),
                    String.valueOf(feeType),
                    NumberUtils.toInt(minAgeLimit.toString()),
                    sessionModelList
            ));
        });

        return vaccinationCenterModelList;
    }
}
