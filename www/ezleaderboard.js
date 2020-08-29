function ezleaderboard() {
}

ezleaderboard.prototype.keepAwake = function (successCallback, errorCallback) {
  cordova.exec(successCallback, errorCallback, "ezleaderboard", "keepAwake", []);
};

ezleaderboard.prototype.allowSleepAgain = function (successCallback, errorCallback) {
  cordova.exec(successCallback, errorCallback, "ezleaderboard", "allowSleepAgain", []);
};

ezleaderboard.install = function () {
  if (!window.plugins) {
    window.plugins = {};
  }

  window.plugins.ezleaderboard = new ezleaderboard();
  return window.plugins.ezleaderboard;
};

cordova.addConstructor(ezleaderboard.install);