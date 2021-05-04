<html>
<head>
  <title>${title}</title>
  <style>
  table, th, td {
    border: 1px solid black;
    border-collapse: collapse;
  }
  th, td {
    padding: 5px;
  }
  </style>
</head>
<body>
  <h1>${title}</h1>

  <table>
    <tr>
        <th>Center Name</th>
        <th>District Name</th>
        <th>Pincode</th>
        <th>Fee Type</th>
        <th>Min Age Limit</th>
        <th>Available Capacity</th>
    </tr>
    <#list vaccinationCenterModelList as vaccinationCenterModel>
        <tr>
            <td>${vaccinationCenterModel.getCenterName()}</td>
            <td>${vaccinationCenterModel.getDistrictName()}</td>
            <td>${vaccinationCenterModel.getZipCode()}</td>
            <td>${vaccinationCenterModel.getFeeType()}</td>
            <td>${vaccinationCenterModel.getMinAgeLimit()}</td>
            <td>
                <table>
                    <tr>
                        <th style="text-align: left;">Date</th>
                        <#list vaccinationCenterModel.getSessionModelList() as sessionModel>
                            <th style="text-align: center;">${sessionModel.getDate()}</th>
                        </#list>
                    </tr>

                    <tr>
                        <th style="text-align: left;">Available Capacity</th>
                        <#list vaccinationCenterModel.getSessionModelList() as sessionModel>
                            <td style="text-align: center;">${sessionModel.getAvailableCapacity()}</td>
                        </#list>
                    </tr>

                    <tr>
                        <th style="text-align: left;">Minimum Age</th>
                        <#list vaccinationCenterModel.getSessionModelList() as sessionModel>
                            <td style="text-align: center;">${sessionModel.getMinAge()}</td>
                        </#list>
                    </tr>
                </table>
            </td>
        </tr>
    </#list>
  </table>

</body>
</html>
