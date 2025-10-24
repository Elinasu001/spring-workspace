package com.kh.spring.api.model.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ApiService {
	
	public String requestBeef() {
		
		final String API_KEY = "e7890a0a5df5768248d2ec1be79503b46e6170116a0c6030636eda56d2566ccd";
		
		//우리 Service단에서
		// api서버로 요청을 보내고 응답을 받아서 다시 앞단으로 반환
		StringBuilder sb = new StringBuilder();
		sb.append("http://211.237.50.150:7080/openapi/");
		sb.append(API_KEY);
		sb.append("/json/Grid_20200713000000000605_1/1/5");
		
		try {
			URI uri = new URI(sb.toString());
			RestTemplate restTamplate = new RestTemplate();
			String response = restTamplate.getForObject(uri, String.class); // stream
			return response;
			
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		return "";
		
	}
	
	
	public String requestBlog(String query) {
		String clientId = "6SgiAAYZkuHSnSwQARwu"; //애플리케이션 클라이언트 아이디
        String clientSecret = "mFoJw9rUbA"; //애플리케이션 클라이언트 시크릿


        String text = null;
        try {
        	 text = URLEncoder.encode(query, "UTF-8");  // 사용자가 입력한 쿼리로, 개발자가 정의한
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패",e);
        }

        //String apiURL = "https://openapi.naver.com/v1/search/blog?query=" + text + "&start=2&sort=date";    // JSON 결과
        String apiURL = "https://openapi.naver.com/v1/search/shop?query=" + text + "&display=10&sort=date";    // JSON 결과
        
       
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBody = get(apiURL,requestHeaders); // get 은 ? 아래 코드 


        System.out.println(responseBody);
        return responseBody;
	}
	
	 private String get(String apiUrl, Map<String, String> requestHeaders){
	        HttpURLConnection con = connect(apiUrl);
	        try {
	            con.setRequestMethod("GET");
	            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
	                con.setRequestProperty(header.getKey(), header.getValue());
	            }


	            int responseCode = con.getResponseCode();
	            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
	                return readBody(con.getInputStream());
	            } else { // 오류 발생
	                return readBody(con.getErrorStream());
	            }
	        } catch (IOException e) {
	            throw new RuntimeException("API 요청과 응답 실패", e);
	        } finally {
	            con.disconnect();
	        }
	    }


	    private HttpURLConnection connect(String apiUrl){
	        try {
	            URL url = new URL(apiUrl);
	            return (HttpURLConnection)url.openConnection();
	        } catch (MalformedURLException e) {
	            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
	        } catch (IOException e) {
	            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
	        }
	    }


	    private String readBody(InputStream body){
	    	
	    	// 인코딩 에러
	    	//InputStreamReader streamReader = new InputStreamReader(body);
	        InputStreamReader streamReader = null;
	       
	        try {
				streamReader = new InputStreamReader(body, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}


	        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
	            StringBuilder responseBody = new StringBuilder();


	            String line;
	            while ((line = lineReader.readLine()) != null) {
	                responseBody.append(line);
	            }


	            return responseBody.toString();
	            
	        } catch (IOException e) {
	            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
	        }
	    }
	    
	    
	    public String requestBusan(int pageNo) {
	    	
	    	final String SERIVCE_KEY="?serviceKey=bafa38a635253c840faba210012e6455cd784e0696c222a50f143cbe54696f39";
	    	
	    	StringBuilder sb = new StringBuilder();
	    	//https://apis.data.go.kr/6260000/FoodService/getFoodKr?serviceKey=bafa38a635253c840faba210012e6455cd784e0696c222a50f143cbe54696f39&pageNo=1&numOfRows=10&resultType=json
	    	// 값이 바뀔 것을 생각해서 구분함.
	    	sb.append("https://apis.data.go.kr/6260000/FoodService/getFoodKr");
	    	sb.append(SERIVCE_KEY);
	    	sb.append("&pageNo=" + pageNo);
	    	sb.append("&numOfRows=6");
	    	sb.append("&resultType=json");
	    	
	    	URI uri = null;
	    	try {
				uri = new URI(sb.toString());
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
	    	
	    	String apiResponse = new RestTemplate().getForObject(uri, String.class);
	    	
	    	return apiResponse;
	    	
	    }
	    
	    
	    public String requestBusanDetail(int num) {
	    	final String SERIVCE_KEY="?serviceKey=bafa38a635253c840faba210012e6455cd784e0696c222a50f143cbe54696f39";
	    	
	    	StringBuilder sb = new StringBuilder();
	    	//https://apis.data.go.kr/6260000/FoodService/getFoodKr?serviceKey=bafa38a635253c840faba210012e6455cd784e0696c222a50f143cbe54696f39&pageNo=1&numOfRows=10&resultType=json
	    	// 값이 바뀔 것을 생각해서 구분함.
	    	sb.append("https://apis.data.go.kr/6260000/FoodService/getFoodKr");
	    	sb.append(SERIVCE_KEY);
	    	sb.append("&pageNo=1");
	    	sb.append("&numOfRows=1");
	    	sb.append("&resultType=json");
	    	sb.append("&UC_SEQ="+num);
	    	URI uri = null;
	    	try {
				uri = new URI(sb.toString());
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
	    	
	    	String apiResponse = new RestTemplate().getForObject(uri, String.class);
	    	
	    	return apiResponse;
	    }
	
	
}
