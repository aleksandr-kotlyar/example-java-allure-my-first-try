# Try Allure! My version of pet project.
**Hi all! I'm trying to use allure2 in project.**

**But i have some questions/problems on the start.**

_**Here are the points which disappoints**(me)_.

**1.** I could not attach .html screenshots. Now i can, but look on the code...

**2.** I wanted to take .html screenshots always on fail. And + .png screenshots only on fails with gui webDrivers. Now i can, but look on the code...

**3.** I wanted to create custom name to each screenshot, but look on the code, i could't do it.

**4.** I thought there is the easy way to take pageSource into file, but i just copy/pasted from selenide.

**5.** I thought it would be work with selenium! But, look on the code! If i close webdriver in @After tearDown, screenshots will not be taken anymore!

##I'm looking forward that someone skilled in allure will tell me what i should fix and how, to use allure2 rightly.
##I will accept any help.


* Here is setup: 
  * maven 
  * java 
  * allure2 
  * junit4 
  * selenide 3.11
  
* Project contains same two tests: positive and negative (will fail for taking screenshot).

* For each of two webDrivers: headlessChrome and htmlUnit 

  1. SimpleChromeHeadlessTest - .png + .html screenshots should be taken.
 
  2. SimpleHtmlUnitTest - only .html screenshots should be taken.

     * htmlUnit has examples with js on and js off.