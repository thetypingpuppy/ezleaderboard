class ezleaderboard {
    constructor(){
        this.user;
        this.authorised;
    }

    // -- iOS and Android
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

    showLeaderboard(successCallback, errorCallback, args){
        cordova.exec(successCallback, errorCallback, "ezleaderboard", "showLeaderboard", args);
    }

    getPlayerImage(successCallback, errorCallback){
        cordova.exec(successCallback, errorCallback, "ezleaderboard", "getPlayerImage", []);
    }
}