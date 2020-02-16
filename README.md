# Try Allure! My first version.
**Hi all! This is my first attempt to attach allure screenshots.**

* Here is setup: 
  * maven 
  * java 1.8
  * allure2 
  * junit4 
  * selenide 3.11
  
* Project contains same two tests: positive and negative (will fail for taking screenshot).

* For each of two webDrivers: headlessChrome and htmlUnit 

  1. allure.takescreenshot.onfail.ChromeTest - .png + .html screenshots should be taken.
 
  2. allure.takescreenshot.onfail.HtmlUnitTest - only .html screenshots should be taken.

     * htmlUnit has examples with js on and js off.
