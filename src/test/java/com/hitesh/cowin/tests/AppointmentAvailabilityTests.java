package com.hitesh.cowin.tests;

import com.hitesh.cowin.api.repo.AppointmentAvailability;
import com.hitesh.cowin.email.EmailHelper;
import com.hitesh.cowin.freemarker.helper.EmailGenerator;
import com.hitesh.cowin.freemarker.helper.ResponseToModelTransformer;
import com.hitesh.cowin.freemarker.model.VaccinationCenterModel;
import com.hitesh.cowin.json.JSONObject;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class AppointmentAvailabilityTests {

    @Test(alwaysRun = true)
    public void checkSlotAvailabilityFor18Plus() throws IOException {
        String pincodeToSearchWith = System.getProperty("pincode");
        System.out.println("Pincode : " + pincodeToSearchWith);

        if (!StringUtils.isBlank(pincodeToSearchWith)) {
            AppointmentAvailability appointmentAvailability = new AppointmentAvailability();

            Response response = appointmentAvailability
                    .getVaccinationSessionByPinFor7DaysFromToday(Integer.parseInt(pincodeToSearchWith));

            System.out.println("\n---------------------- ORIGINAL JSON ------------------------");

            try {
                JSONObject jsonObject = new JSONObject(response.asString());
                System.out.println(jsonObject);

                System.out.println("\n---------------------- FILTERED JSON BASED ON AVAILABILITY ------------------------");
                JSONObject filteredJsonObject = appointmentAvailability.filterResponseByAvailability(response);
                System.out.println(filteredJsonObject);

                System.out.println("\n---------------------- AVAILABLE SLOT LIST BY AGE  ------------------------");
                JSONObject filteredObject = appointmentAvailability.filterResponseByAvailabilityAndByAge(response, 18, 44);
                System.out.println(filteredObject.toString());

                if (!filteredObject.getJSONArray("centers").isEmpty()) {
                    List<VaccinationCenterModel> model = ResponseToModelTransformer.convertToVaccinationCenterModel(filteredObject);

                    System.out.println(filteredJsonObject.toString(2));

                    String emailTemplateAsHtml = EmailGenerator.generateEmailTemplate(filteredObject);

                    EmailHelper.emailReport(emailTemplateAsHtml);
                } else {
                    System.out.println("\n\nNo available slots found for age 18-44.");
                }
            } catch (Exception tEx) {
                System.out.println("\n\n************************** RESPONSE IS NOT OF TYPE JSON **************************\n");
                System.out.println("----------------------------- Response Received from API -----------------------------");
                System.out.println(response.asString());
                System.out.println("\n**************************** FIND STACKTRACE BELOW *******************************\n");
                tEx.printStackTrace();
            }
        }
    }
}
