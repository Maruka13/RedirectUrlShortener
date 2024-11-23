package com.maruka.redirectUrlShortener;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.io.InputStream;
import java.util.Map;


public class Main implements RequestHandler<Map<String, Object>, Map<String, String>> {

        private final S3Client s3Client = S3Client.builder().build();

        @Override
        public Map<String, String> handleRequest(Map<String, Object> input, Context context) {
            String pathParameters = (String) input.get("rawPath");
            String shortUrlCode = pathParameters.replace("/", "");

            if (shortUrlCode == null || shortUrlCode.isEmpty()) {
                throw new IllegalArgumentException("Invalid input: 'shortUrlCode' is required.");
            }

            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket("maruka-urlshortener-storage")
                    .key(shortUrlCode + "json")
                    .build();

            InputStream s3objectStream;

            try{
                s3objectStream = s3Client.getObject(getObjectRequest);
            } catch (Exception e) {
                throw new RuntimeException("Error fetching data from s3: " + e.getMessage(), e);
            }

            UrlData urlData;

            return null;
        }

}