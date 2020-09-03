# ezleaderboard

Install with the following:

```console
cordova plugin add https://github.com/thetypingpuppy/ezleaderboard.git --variable PLAY_APP_ID=123456789012 --variable PLAY_LEADERBOARD_ID=CgkPpDzMxOAWEAIQAQ
```

Submit a score of 1401:

```javascript
        cordova.exec(
            function(winParam) {},
            function(error) {},
            "ezleaderboard",
            "submitToLeaderboard",
            [1401]);
```
