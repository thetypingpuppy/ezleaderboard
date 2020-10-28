# ezleaderboard

## Installation

```console
cordova plugin add https://github.com/thetypingpuppy/ezleaderboard.git --variable PLAY_APP_ID=123456789012 --variable PLAY_LEADERBOARD_ID=CgkPpDzMxOAWEAIQAQ
```

Add the paths to the ezleaderboard class. In your index.html (or any other relevant html files), add the following to the `<head>` tag area.
```html
<script type="text/javascript" src="plugins/ezleaderboard/ezleaderboard.js"></script> 
```


## Initialisation

First step is initialisation of the ezleaderboard class. 
```javascript
function onDeviceReady() {
    var ezl = new ezleaderboard();
}
```

### Authorisation - iOS Only
For iOS Game Center needs authorising. Best do this in onDeviceReady.
```javascript
function onDeviceReady() {
    if (/(ipod|iphone|ipad)/i.test(navigator.userAgent)){
        ezl.auth(successCallback(), errorCallback());
    }

    function successCallback(){
        console.log("iOS Game centre authorised.");
    }

    function errorCallback(){
        console.log("Game centre authorisation error.");
    }
}
```

### Submit Score
The argument array is a single entry array composed of one object. The object must contain the key: `score` for both iOS and Android. Specifically for iOS the `leaderboardID` key needs to be given as well.
```javascript
function addScore(){
    var args = [{score: 100, leaderboardID: "my_leaderboard"}];
    ezl.submitToLeaderboard(successCallback, errorCallback, args);
}
```

### Show Leaderboard
On android the leaderboard in Game Play Services app will load automatically, in iOS it needs to be shown manually.
```javascript
function addScore(){
    var args = [{score: 100, leaderboardID: "my_leaderboard"}];
    ezl.submitToLeaderboard(successCallback(), errorCallback(), args);

    function successCallback(){
        // If iOS show leaderboard
        if (/(ipod|iphone|ipad)/i.test(navigator.userAgent)){
            var args = [{leaderboardID: "my_leaderboard", showAchievements: "false"}];
            ezl.showLeaderboard(function(){console.log("iOS Game centre leaderboard loaded.")}, function(){"Game centre leaderboard failed to load."}, args);
        }
    }

    function errorCallback(){
        console.log("Game centre score submission error.");
    }
}
```