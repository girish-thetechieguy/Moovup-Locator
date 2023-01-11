package com.moovup.friendslocatorservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.moovup.friendslocatorservice.model.FriendDetails;
import com.moovup.friendslocatorservice.model.FriendNamesDVO;
import com.moovup.friendslocatorservice.service.FriendsLocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/friendslocator")
public class FriendsLocationController {

    @Autowired
    private FriendsLocationService friendsLocationService;

    @GetMapping("/retrive/names")
    public ResponseEntity<List<FriendNamesDVO>> retrieveAllFriendsMinInfo() throws JsonProcessingException {
        log.info("Inside FriendsLocationController class - > retrieveAllFriendsMinInfo()");
        List<FriendNamesDVO> friendNamesList = friendsLocationService.getFriendsBasicInfo();
        return ResponseEntity.ok().body(friendNamesList);
    }

    @GetMapping("/retrive/{id}")
    public ResponseEntity<FriendDetails> findDetailsById(@PathVariable("id") String id) throws JsonProcessingException {
        log.info("Inside FriendsLocationController class - > findDetailsById()");
        FriendDetails friendsDetails = friendsLocationService.findFriendDetailsById(id);
        if(friendsDetails != null)
            return ResponseEntity.ok().body(friendsDetails);
        else
            return ResponseEntity.notFound().eTag("Friend details not found!").build();
    }

}
