// function ezleaderboard() {
// }

// ezleaderboard.prototype.keepAwake = function (successCallback, errorCallback) {
//   cordova.exec(successCallback, errorCallback, "ezleaderboard", "keepAwake", []);
// };

// ezleaderboard.prototype.allowSleepAgain = function (successCallback, errorCallback) {
//   cordova.exec(successCallback, errorCallback, "ezleaderboard", "allowSleepAgain", []);
// };

// ezleaderboard.install = function () {
//   if (!window.plugins) {
//     window.plugins = {};
//   }

//   window.plugins.ezleaderboard = new ezleaderboard();
//   return window.plugins.ezleaderboard;
// };

// cordova.addConstructor(ezleaderboard.install);

class ezleaderboard {
    constructor(){
        this.user;
        this.authorised;
    }

    // -- iOS and Android
    // args = [{ score: 10, leaderboardID: "champions_circle" }]
    submitToLeaderboard(successCallback, errorCallback, args){
        cordova.exec(successCallback, errorCallback, "ezleaderboard", "submitToLeaderboard", args);
    }

    // -- iOS only 
    auth(successCallback, errorCallback){
        cordova.exec(function(user){
                        this.user = user;
                        this.authorised = true;
                        successCallback();
                      }.bind(this),
                      function(){
                        this.authorised = false;
                        errorCallback();
                      },
                      "ezleaderboard", "auth", []);
    }

    //args = [{ leaderboardID: "champions_circle", showAchievements: "true" }]
    showLeaderboard(successCallback, errorCallback, args){
        cordova.exec(successCallback, errorCallback, "ezleaderboard", "showLeaderboard", args);
    }

    getPlayerImage(successCallback, errorCallback){
        cordova.exec(successCallback, errorCallback, "ezleaderboard", "getPlayerImage", []);
    }
}