package org.kps.currency.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Collections;
import java.util.List;

@Service
public class GoogleAuthorizationService {
    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    /**
     * Directory to store authorization tokens for this application.
     */
    @Value("${google.token_directory_path}")
    private String TOKENS_DIRECTORY_PATH;

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES =
            Collections.singletonList("https://www.googleapis.com/auth/drive");

    @Value("${google.credentials_file_path}")
    private String CREDENTIALS_FILE_PATH;

    public Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
            throws IOException {
            //authorize user example
//        // Load client secrets.
//        InputStream in = GoogleAuthorizationService.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
//        if (in == null) {
//            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
//        }
//        GoogleClientSecrets clientSecrets =
//                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
//
//        // Build flow and trigger user authorization request.
//        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
//                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
//                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
//                .setAccessType("offline")
//                .build();
//        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
//        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
//        //returns an authorized Credential object.
//        return credential;
//
//        FileInputStream credentialsStream = new FileInputStream(CREDENTIALS_FILE_PATH);
//        GoogleCredential credential = GoogleCredential.fromStream(credentialsStream, new NetHttpTransport(), null)
//                .createScoped(Collections.singletonList("https://www.googleapis.com/auth/drive"));
//        return credential;

        //authorize with key
        InputStream credentialsStream = getClass().getResourceAsStream("/service_key.json");
        if (credentialsStream == null) {
            throw new FileNotFoundException("Resource not found: credentials.json");
        }
        GoogleCredential credential = GoogleCredential.fromStream(credentialsStream, HTTP_TRANSPORT, JSON_FACTORY)
                .createScoped(Collections.singletonList("https://www.googleapis.com/auth/drive"));
        return credential;

    }
}
