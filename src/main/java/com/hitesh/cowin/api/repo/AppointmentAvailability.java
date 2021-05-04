package com.hitesh.cowin.api.repo;

import com.github.underscore.lodash.U;
import com.hitesh.cowin.api.constants.APIConstants;
import com.hitesh.cowin.json.JSONArray;
import com.hitesh.cowin.json.JSONObject;
import com.hitesh.cowin.utility.DateUtility;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.given;

public class AppointmentAvailability {
    private static final Logger logger = LogManager.getLogger(AppointmentAvailability.class.getName());

    public AppointmentAvailability() {
        RestAssured.baseURI = APIConstants.API_BASE_URL;
    }

    public Response getVaccinationSessionByPinFor7DaysFromToday(int postalCode) {
        String currentDate = DateUtility.getCurrentTimeStampWithFormatAs("dd-MM-yyyy");

        return given()
                .queryParam("pincode", postalCode)
                .queryParam("date", currentDate)
                .contentType(ContentType.JSON)
                .when()
                .get("appointment/sessions/public/calendarByPin")
                .then()
                .extract()
                .response();
    }

    public Response getVaccinationSessionByPinFor7DaysFromToday(int postalCode, String dateFrom) {
        return given()
                .queryParam("pincode", postalCode)
                .queryParam("date", dateFrom)
                .contentType(ContentType.JSON)
                .when()
                .get("appointment/sessions/public/calendarByPin")
                .then()
                .extract()
                .response();
    }

    public JSONObject filterResponseByAvailability(Response apiResponse) {
        JSONObject responseAsJson = new JSONObject(apiResponse.asString());

        JSONArray filteredJsonArray = new JSONArray();

        responseAsJson.getJSONArray("centers").forEach(centerObject -> {
            JSONObject eachCenterJsonObj = (JSONObject) centerObject;

            // Store value of current center into JSON
            JSONObject availableJsonObject = new JSONObject();
            availableJsonObject.put("center_id", eachCenterJsonObj.get("center_id"));
            availableJsonObject.put("name", eachCenterJsonObj.get("name"));
            availableJsonObject.put("district_name", eachCenterJsonObj.get("district_name"));
            availableJsonObject.put("pincode", eachCenterJsonObj.get("pincode"));
            availableJsonObject.put("fee_type", eachCenterJsonObj.get("fee_type"));

            if (eachCenterJsonObj.has("sessions")) {
                int totalAvailableSessions = eachCenterJsonObj.getJSONArray("sessions").length();
                availableJsonObject.put("total_sessions", totalAvailableSessions);

                // For each sessions. check if the availability > 0
                JSONArray filteredSessionArray = new JSONArray();
                List<Integer> minimumAgeList = new ArrayList<>();

                eachCenterJsonObj.getJSONArray("sessions").forEach(sessionObj -> {
                    JSONObject sessionJsonObj = (JSONObject) sessionObj;
                    if (Integer.parseInt(sessionJsonObj.get("available_capacity").toString()) > 0) {
                        JSONObject sessionJsonObject = new JSONObject();
                        sessionJsonObject.put("date", sessionJsonObj.get("date"));
                        sessionJsonObject.put("available_capacity", sessionJsonObj.get("available_capacity"));
                        sessionJsonObject.put("min_age_limit", sessionJsonObj.get("min_age_limit"));

                        // Add current details into jsonArray
                        filteredSessionArray.put(sessionJsonObject);

                        // Add minimum age limit to array
                        minimumAgeList.add(Integer.parseInt(sessionJsonObj.get("min_age_limit").toString()));
                    }
                });

                if (filteredSessionArray.length() > 0) {
                    Optional<Integer> minValue = minimumAgeList.stream().min(Integer::compare);
                    availableJsonObject.put("min_age_limit", minValue.orElse(-1));
                    availableJsonObject.put("sessions", filteredSessionArray);
                }
            }

            if (availableJsonObject.has("sessions")) {
                // Add Filtered details into array
                filteredJsonArray.put(availableJsonObject);
            }
        });

        return new JSONObject().put("centers", filteredJsonArray);
    }

    public JSONObject filterResponseByAvailabilityAndByAge(Response apiResponse, int minAge, int maxAge) {
        U.Chain<String> test = U.of(filterResponseByAvailability(apiResponse).getJSONArray("centers"))
                .filter(eachJsonObject -> Integer.parseInt(((JSONObject) eachJsonObject).get("min_age_limit").toString()) >= minAge)
                .filter(eachJsonObject -> Integer.parseInt(((JSONObject) eachJsonObject).get("min_age_limit").toString()) <= maxAge)
                .toJson();

        return new JSONObject().put("centers", new JSONArray(test.item()));
    }
}
