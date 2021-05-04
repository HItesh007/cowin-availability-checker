package com.hitesh.cowin.freemarker.helper;

import com.hitesh.cowin.freemarker.model.VaccinationCenterModel;
import com.hitesh.cowin.json.JSONObject;
import com.hitesh.cowin.utility.SystemUtility;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmailGenerator {

    public static String generateEmailTemplate(JSONObject responseObject) throws IOException {
        if (!responseObject.isEmpty()) {
            Configuration templateConfig = new Configuration(Configuration.getVersion());
            templateConfig.setClassForTemplateLoading(EmailGenerator.class, "/views");
            templateConfig.setDefaultEncoding("UTF-8");

            // Get List of Vaccination model list
            List<VaccinationCenterModel> vaccinationCenterModelList = ResponseToModelTransformer
                    .convertToVaccinationCenterModel(responseObject);

            Map<String, Object> htmlData = new HashMap<>();

            // Add Vaccination Data to main map
            htmlData.put("title", "CoWin Available Slot");
            htmlData.put("vaccinationCenterModelList", vaccinationCenterModelList);

            // 2.2. Get the template
            Template template = templateConfig.getTemplate("email-template.ftl");

            // 2.3. Generate the output

            // String output file path
            String outFilePath = SystemUtility.getDefaultTempFilePath() + "/output.html";

            // For the sake of example, also write output into a file:
            try (Writer fileWriter = new FileWriter(outFilePath)) {
                template.process(htmlData, fileWriter);
            } catch (TemplateException e) {
                e.printStackTrace();
            }

            // Parse HTML
            Document htmlDocument = Jsoup.parse(new File(outFilePath), "UTF-8");
            return htmlDocument.outerHtml();
        }
        return null;
    }
}
