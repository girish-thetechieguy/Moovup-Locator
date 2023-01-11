package com.moovup.friendslocatorservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FriendDetails {
    private String _id;
    private Name name;
    private String email;
    private String picture;
    private Location location;
}
