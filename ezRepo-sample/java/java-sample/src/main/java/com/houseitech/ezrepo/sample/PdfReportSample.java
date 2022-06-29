package com.houseitech.ezrepo.sample;

import com.houseitech.ezrepo.sample.common.EzRepoApiUtil;

import java.io.File;

public class PdfReportSample {
    public static void main(String[] args) {
        try {
            String apiUrl = "https://ezrepo.houseitech.com/api/pdf/instant/jsonFile";
            //下記ezRepoのAPIを利⽤するためには認証を⾏う必要があります。https://ezrepo.houseitech.com　へユーザー登録してTokenを取得してください。
            //ご不明な点がありましたら、Eメール：ezrepo@houseitech.comまでご連絡ください。
            String token = "";
            String templateFilePath = PdfReportSample.class.getResource("/templates/pdf_template.zip").getPath();
            String jsonFilePath = PdfReportSample.class.getResource("/data/pdf_data.json").getPath();
            String pdfPath = PdfReportSample.class.getResource("/").getPath() + "output/pdf_output.pdf";
            new File(pdfPath).getParentFile().mkdir();
            boolean result = EzRepoApiUtil.invokeRemoteApiWithMultiForm(apiUrl, token,  new File(templateFilePath),new File(jsonFilePath), pdfPath);
            if(result){
                System.out.printf("PDF正常に出力しました。(path=%s)", pdfPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
