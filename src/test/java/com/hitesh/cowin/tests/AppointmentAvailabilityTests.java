package com.hitesh.cowin.tests;

import com.hitesh.cowin.api.repo.AppointmentAvailability;
import com.hitesh.cowin.email.EmailHelper;
import com.hitesh.cowin.freemarker.helper.EmailGenerator;
import com.hitesh.cowin.freemarker.helper.ResponseToModelTransformer;
import com.hitesh.cowin.freemarker.model.VaccinationCenterModel;
import com.hitesh.cowin.json.JSONObject;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;

public class AppointmentAvailabilityTests {


    public static void main(String[] args) throws IOException {
        String pincodeToSearchWith = System.getProperty("postalcode");
        System.out.println("Pincode : " + pincodeToSearchWith);

        if (!StringUtils.isBlank(pincodeToSearchWith)) {
            AppointmentAvailability appointmentAvailability = new AppointmentAvailability();

            Response response = appointmentAvailability
                    .getVaccinationSessionByPinFor7DaysFromToday(Integer.parseInt(pincodeToSearchWith));

            System.out.println("\n---------------------- ORIGINAL JSON ------------------------");
            System.out.println(response.asString());

            System.out.println("\n---------------------- FILTERED JSON BASED ON AVAILABILITY ------------------------");
            JSONObject filteredJsonObject = appointmentAvailability.filterResponseByAvailability(response);
            System.out.println(filteredJsonObject);

            System.out.println("\n---------------------- AVAILABLE SLOT LIST BY AGE  ------------------------");
            JSONObject filteredObject = appointmentAvailability.filterResponseByAvailabilityAndByAge(response, 18, 44);
            System.out.println(filteredObject.toString());

            if (!filteredObject.getJSONArray("centers").isEmpty()) {
                List<VaccinationCenterModel> model = ResponseToModelTransformer.convertToVaccinationCenterModel(filteredObject);

                System.out.println(model);

                String emailTemplateAsHtml = EmailGenerator.generateEmailTemplate(filteredObject);

                EmailHelper.emailReport(emailTemplateAsHtml);
            } else {
                System.out.println("\n\nNo available slots found for age 18-44.");
            }
        }
    }
}
