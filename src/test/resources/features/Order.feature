@driver:chrome
Feature: order on Tiki

  @test
  Scenario Outline: User order on Tiki
    Given Access Tiki website
    When Login the website "<phonenumber>" "<password>"
    And Search by "<keysearch>"
    Then Order product

    Examples:
    |phonenumber|password|keysearch|
    |0967590975|huyen1571998|Sách    |