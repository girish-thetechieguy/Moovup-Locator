package com.moovup.friendslocatorservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.moovup.friendslocatorservice.model.FriendDetails;
import com.moovup.friendslocatorservice.model.FriendNamesDVO;

import java.util.List;


public interface FriendsLocationService {

    public List<FriendNamesDVO> getFriendsBasicInfo() throws JsonProcessingException;

    public FriendDetails findFriendDetailsById(String id) throws JsonProcessingException;


}
