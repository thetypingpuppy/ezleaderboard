#import "Foundation/Foundation.h"
#import "Cordova/CDV.h"
#import "CDVezleaderboard.h"
#import "GameKit/GameKit.h"

@interface CDVezleaderboard : CDVPlugin <GKGameCenterControllerDelegate>

- (void) auth:(CDVInvokedUrlCommand*)command;
- (void) getPlayerImage:(CDVInvokedUrlCommand*)command;
- (void) submitToLeaderboard:(CDVInvokedUrlCommand*)command;
- (void) showLeaderboard:(CDVInvokedUrlCommand*)command;
// - (void) reportAchievement:(CDVInvokedUrlCommand*)command;
// - (void) resetAchievements:(CDVInvokedUrlCommand*)command;
// - (void) getAchievements:(CDVInvokedUrlCommand*)command;

@end