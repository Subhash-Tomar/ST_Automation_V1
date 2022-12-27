Feature: Validating add placeApI

##Add place verification 

  Scenario Outline: verify addplace api
    Given add payload <lat> <lng> "<accuracy>" "<name>" "<phone_number>" "<address>" "<types_1>" "<types_2>" "<website>" "<language>"
    When user calls "addPlaceAPI" with post method
    Then response is ok
    And  status has scope
    
    Examples: 
      | lat  | lng | accuracy  |name|phone_number|address|types_1|types_2|website|language|
      |-20000.00|20000.00|100|Ghaziabad house|9999795618|Ghaziabad|shoe park|shop|http://google.com|French-IN|
    
    #========================================================================
    #OR  You can verify validate with excel data
    #=========================================================================
      
  Scenario Outline: verify addplace api with exceldata

    Given add payload in request <row>
    When user calls "addPlaceAPI" with post method
    Then response is ok
    And  status has scope
    
Examples:
 
 |row|
 |1|
 |2|

                     
##----------------------------------------------------------------------------------------------------------------