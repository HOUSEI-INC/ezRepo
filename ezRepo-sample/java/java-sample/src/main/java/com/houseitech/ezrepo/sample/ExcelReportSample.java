package com.houseitech.ezrepo.sample;

import com.houseitech.ezrepo.sample.common.EzRepoApiUtil;

import java.io.File;

public class ExcelReportSample {
    public static void main(String[] args) {
        try {
            String apiUrl = "https://ezrepo.jp/api/v2/excel/render/instant";
            //下記ezRepoのAPIを利⽤するためには認証を⾏う必要があります。https://ezrepo.houseitech.com　へユーザー登録してTokenを取得してください。
            //ご不明な点がありましたら、Eメール：ezrepo@houseitech.comまでご連絡ください。
            String token = "";
            String templateFilePath = ExcelReportSample.class.getResource("/templates/excel_template.xlsx").getPath();
            String jsonFilePath = ExcelReportSample.class.getResource("/data/excel_data.json").getPath();
            String pdfPath = ExcelReportSample.class.getResource("/").getPath() + "output/excel_output.xlsx";
            new File(pdfPath).getParentFile().mkdir();
            boolean result = EzRepoApiUtil.invokeRemoteApiWithMultiForm(apiUrl, token,  new File(templateFilePath),new File(jsonFilePath), pdfPath);
            if(result){
                System.out.printf("Excel正常に出力しました。(path=%s)", pdfPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
