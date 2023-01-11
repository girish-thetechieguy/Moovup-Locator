package com.moovup.friendslocatorservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moovup.friendslocatorservice.model.FriendDetails;
import com.moovup.friendslocatorservice.model.FriendNamesDVO;
import com.moovup.friendslocatorservice.service.FriendsLocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FriendsLocationServiceImpl implements FriendsLocationService {

    @Value("${com.moovup.endpoint}")
    protected String endpoint;

    @Value("${com.moovup.token}")
    protected String token;

    @Override
    public List<FriendNamesDVO> getFriendsBasicInfo() throws JsonProcessingException {
        log.info("Inside FriendsLocationServiceImpl class - > getFriendsBasicInfo()");
        FriendDetails[] friendsDetailsList = getDetailsFromEndpoint();
        List<FriendNamesDVO> friendNamesDVO = new ArrayList<FriendNamesDVO>();
        for(FriendDetails friendDetails : friendsDetailsList){
            FriendNamesDVO friendNames =  new FriendNamesDVO();
            friendNames.set_id(friendDetails.get_id());
            friendNames.setPicture(friendDetails.getPicture());
            friendNames.setName(friendDetails.getName());
            friendNames.setEmail(friendDetails.getEmail());
            friendNames.setLocation(friendDetails.getLocation());
            friendNamesDVO.add(friendNames);
        }
        return friendNamesDVO;
    }

    @Override
    public FriendDetails findFriendDetailsById(String id) throws JsonProcessingException {
        log.info("Inside FriendsLocationServiceImpl class - > findFriendDetailsById()");
        FriendDetails[] friendsDetailsList = getDetailsFromEndpoint();
        for(FriendDetails friendDetails : friendsDetailsList){
            if(friendDetails.get_id().equals(id))
                return friendDetails;
        }
        return null;
    }

    private FriendDetails[] getDetailsFromEndpoint() throws JsonProcessingException {
        log.info("Inside FriendsLocationServiceImpl class - > getDetailsFromEndpoint()");
        String url = endpoint;
        WebClient.Builder webClientBuilder = WebClient.builder();
        String result = webClientBuilder.build()
                .get()
                .uri(url)
                .headers(h -> h.setBearerAuth(token))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        FriendDetails[] friendsDetailsList = new ObjectMapper().readValue(result, FriendDetails[].class);
        return friendsDetailsList;
    }
}
