package com.houseitech.ezrepo.sample.common;

import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.Arrays;

@Component
public class EzRepoApiUtil {
	private static  final RestTemplate restTemplate = new RestTemplate();
	static {
		MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
		mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
		restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
	}

	public static boolean invokeRemoteApiWithMultiForm(String apiUrl, String token, File templateFile,File jsonFile, String outputFile) throws IOException {
		// リクエストヘッダを設定する
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		headers.add("token", token);

		try(InputStream inputTemplateStream = new FileInputStream(templateFile);
			InputStream inputJsonStream = new FileInputStream(jsonFile)){
			LinkedMultiValueMap<String,Object>  requestParams = new LinkedMultiValueMap<>();
			MyInputStreamResource templateStreamResource = new MyInputStreamResource(inputTemplateStream, templateFile.length(), templateFile.getName());
			requestParams.add("template", templateStreamResource);

			MyInputStreamResource jsonStreamResource = new MyInputStreamResource(inputJsonStream, jsonFile.length(), jsonFile.getName());
			requestParams.add("entity", jsonStreamResource);

			HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(requestParams, headers);

			// ezRepoのapiを呼出す
			ResponseEntity<byte[]> result = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, byte[].class);
			// 出力レポートをファイルに保存する
			byte[] body = result.getBody();
			try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(outputFile)))) {
				bos.write(body);
				bos.flush();
			}
			return true;
		}catch (HttpClientErrorException ex) {
			ex.printStackTrace();
			return false;
		}
	}

}
