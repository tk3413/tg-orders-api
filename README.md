# Order DB API

### Diagram:


### Routes:
#### /orderInfo 
* Required query params: objectId
* Optional query params: timestamp (if not provided, will fetch most recent order if available)
###### Request examples: 
* /order?objectId=1 
* /order?objectId=2&timestamp=1484731400

###### Response examples: (format: json)
{"customerName":"Jack","customerAddress":"Trade St.","status":"UNPAID","timestamp":1484730554}

###### Response codes:
| Response Code | Response Status |
|---------------|-----------------|
| 200           | Success         |
| 404           | Not Found       |
| 500           | Bad Request     |

###### Response format: 


#### /health
* Required query params: none
* Optional query params: none

###### Request examples: 
* /health

###### Response examples:
(Response code: 200)



### Developer's Corner:
```bash
git clone https://github.com/tk3413/tradegecko.git
cd tradegecko
Use your favorite editor to update DB password in: src/main/resources/application.properties
mvn clean install
mvn spring-boot:run
```