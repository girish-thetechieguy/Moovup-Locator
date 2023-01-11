var libraries;
var markers = [];

function loadData() {
    let allUsersDetails;
    let httpRequest = new XMLHttpRequest();
    httpRequest.open("GET", 'http://localhost:9090/friendslocator/retrive/names');
    httpRequest.send();
    httpRequest.onload = function() {
        libraries = JSON.parse(httpRequest.responseText);
        initMap();
    }

    const Listen = (doc) => {
        return {
            on: (type, selector, callback) => {
                doc.addEventListener(type, (event, markers) => {
                    if (!event.target.matches(selector)) return;
                    callback.call(event.target, event);
                }, false);
            }
        }
    };

    Listen(document).on('click', '.friendBtn', function(e, markers) {
        let httpFriendFinderRequest = new XMLHttpRequest();
        httpFriendFinderRequest.open("GET", 'http://localhost:9090/friendslocator/retrive/' + e.target.id);
        httpFriendFinderRequest.send();
        httpFriendFinderRequest.onload = function(markers) {
            libraries = [];
            libraries.push(JSON.parse(httpFriendFinderRequest.responseText));
            initMap();
            addMarkers();
        }
    });

    // filter by Ancaster
    function filterShowFriends() {
        for (let i = 0; i < libraries.length; i++) {
            var friendname = libraries[i].name.first + "," + libraries[i].name.last;
            var friendPicture = libraries[i].picture;
            var friend_id = libraries[i]._id;
            document.getElementById("friendsList").innerHTML += "<div> <ul><li> <img id='img1' src='" + friendPicture + "' alt='Friend'><button class='friendBtn' id='" + friend_id + "'>" + friendname + "</button>";
        }
    }
    document.getElementById("showFriends").onclick = filterShowFriends;

    //Display all markers again
    function displayAll() {
        addMarkers();
        for (i = 0; i < markers.length; i++) {
            markers[i].setMap(map);
        }
    }
    document.getElementById("displayAll").onclick = displayAll;
}

function initMap() {
    map = new google.maps.Map(document.getElementById("map"), {
        center: {
            lat: 22.31,
            lng: 113.71
        },
        zoom: 10,
    });

    infowindow = new google.maps.InfoWindow();
    //If marker is clicked will run this function and display the name and address
    marker_clicked = function() {
        infowindow.close();
        content = "<h5>" + this.NAME + "</h5><p>" + this.ADDRESS + "</p>";
        infowindow.setContent(content);
        infowindow.open(map, this);
    }
    geocoder = new google.maps.Geocoder();
};

//Add all markers
function addMarkers() {
    for (i = 0; i < libraries.length; i++) {
        new_marker = new google.maps.Marker({
            position: {
                lat: libraries[i].location.latitude,
                lng: libraries[i].location.longitude
            },
            title: libraries[i].name.first + libraries[i].name.last
        });
        new_marker.setMap(map);
        new_marker.NAME = libraries[i].name.first + "," + libraries[i].name.last;
        new_marker.ADDRESS = "ID:"+ libraries[i]._id +",Email:" + libraries[i].email + ",Latitude:" + libraries[i].location.latitude
        +",Longitude:"+ libraries[i].location.longitude;
        new_marker.addListener("click", marker_clicked);
        markers.push(new_marker);
    }
}