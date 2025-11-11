package com.training.articles.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.training.articles.dto.user.GetUsersResponseDto;
import com.training.articles.dto.user.KeycloackUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;


@Service
public class KeycloakCallApiService {
private static final String KEYCLOAK_BASE_URL = "http://localhost:8080/admin/realms";
private static final String KEYCLOAK_GET_USERS_URL = KEYCLOAK_BASE_URL + "/%s/users";
private static final String KEYCLOAK_DELETE_USERS_URL = KEYCLOAK_BASE_URL + "/%s/users/%s";

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    private final RestTemplate restTemplate = new RestTemplate();

public List<GetUsersResponseDto> getListUser(String realmCode) {
    var uri = String.format(KEYCLOAK_GET_USERS_URL, realmCode);
    var adminAccessToken = getAdminAccessToken(realmCode);

    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(adminAccessToken);


    HttpEntity<Void> request = new HttpEntity<>(headers);

    ResponseEntity<List<GetUsersResponseDto>> response = restTemplate.exchange(
            uri,
            HttpMethod.GET,
            request,
            new ParameterizedTypeReference<List<GetUsersResponseDto>>() {}
    );

    return response.getBody();
}

public void updateUser(String realmCode, String userId, String userName, String email) {
        var uri = String.format(KEYCLOAK_DELETE_USERS_URL, realmCode, userId);
        var adminAccessToken = getAdminAccessToken(realmCode);

        KeycloackUser user = KeycloackUser.builder()
                            .email(email)
                            .username(userName)
                            .build();
        var body = createRequestBody(user);

        sendRequestToKeycloakWithBody(uri, adminAccessToken, body, HttpMethod.PUT);
}

public void createUser(String realmCode,  String userName, String email) {
    var uri = String.format(KEYCLOAK_GET_USERS_URL, realmCode);
    var adminAccessToken = getAdminAccessToken(realmCode);

    KeycloackUser user = KeycloackUser.builder()
            .email(email)
            .username(userName)
            .build();
    var body = createRequestBody(user);

    sendRequestToKeycloakWithBody(uri, adminAccessToken, body, HttpMethod.POST);
}

public void deleteUser(String realmCode, String userId) {
    var uri = String.format(KEYCLOAK_DELETE_USERS_URL, realmCode, userId);
    var adminAccessToken = getAdminAccessToken(realmCode);

    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(adminAccessToken);

    HttpEntity<Void> request = new HttpEntity<>(headers);

    restTemplate.exchange(uri, HttpMethod.DELETE, request, Void.class);
}


public String getAdminAccessToken(String realmCode) {
        String tokenUrl = KEYCLOAK_BASE_URL + "/realms/" + realmCode + "/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);
        return (String) response.getBody().get("access_token");
}

private void sendRequestToKeycloakWithBody(String uri, String adminAccessToken, JsonNode body, HttpMethod method){
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(adminAccessToken);

    HttpEntity<JsonNode> request = new HttpEntity<>(body, headers);

    restTemplate.exchange(uri, method, request, Void.class);
}

private JsonNode createRequestBody(KeycloackUser user){
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode node = mapper.createObjectNode();
    node.put("user_name", user.username());
    node.put("email", user.email());
    return  node;
}

}
