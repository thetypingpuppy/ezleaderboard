function notSupported() {
    console.log('ezleaderboard is not supported on the browser');
}

module.exports = {
    keepAwake: notSupported,
    allowSleepAgain: notSupported
};

require('cordova/exec/proxy').add('ezleaderboard', module.exports);
