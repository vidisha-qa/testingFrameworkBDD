Feature: User flow
@TC1 @SmokeTest @RegressionTest
Scenario Outline: User Flow for downloading voter roll
Given User is on ELECTORAL ROLLS page 
When Select <District_Name> and <Assembly_Constituency>
And Click on Get Polling Stations button
Then Click on view and dowload the vote poll

Examples:
|District_Name|Assembly_Constituency|
|8-Peddapalli|24-Manthani|