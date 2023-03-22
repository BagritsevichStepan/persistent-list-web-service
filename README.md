# Web Service for Partially Persistent List Management

This is solution of the [3 test task](https://gist.github.com/mmazurkevich/a96393788246f85b2d2adf2cbff2af0e) in [Workspace Model Metadata Storage Team](https://internship.jetbrains.com/projects/1327/).

_Implemented with Kotlin_

## Stages of Development
1. [Implementation using Spring Boot](#stage1)
2. [Implementation using asynchronous REST API](#stage2)
3. [Frontend development using Freemarker](#stage3)
4. [Docker and Deployment](#stage4)

## <a name="stage1"></a>First stage. Spring Boot
Web Service for Partially Persistent List Management Implementation using Spring Boot Framework, ORM Framework Hibernate (included in Spring Boot) and H2 Database (in-memory-database).

[_Open project folder_](https://github.com/BagritsevichStepan/wmms-test-task3/tree/main/wmms-test-task3)

### Request and Response Specification
1. Get the available versions<br />
  Request: GET /lists<br />
  Response: { "versions": [1,2,3,4] }

2. Create new empty list<br />
  Request: Get /lists/add/empty<br />
  Response: { "listVersion": 5 }

3. Get the list elements<br />
  Request: GET /list/{id}<br />
  Response: [2,3,4,6,0]<br />
  Response Codes:<br />
    * 404 - Invalid ID `id`

4. Add a new element to the end of the list<br />
  Request: POST /list/{id}<br />
  Request body: { "newElement": 22 }<br />
  Response: { "listVersion": 6 }<br />
  Response Codes:<br />
    * 404 - Invalid ID `id`

5. Remove an element by value<br />
  Request: DELETE /list/{id}<br />
  Request body: { "oldElement": 10 }<br />
  Response: { "listVersion": 7 }<br />
  Response Codes:<br />
    * 404 - Invalid ID `id`
    * 406 - Element `oldElement` not found
  
6. Remove an element by value with GET request<br />
  DELETE-Request Replacement when DELETE-Request body isn't supported<br />
  Request: GET /list/{id}/delete/{oldElement}<br />
  Response: { "listVersion": 8 }<br />
  Response Codes:<br />
    * 404 - Invalid ID `id`
    * 406 - Element `oldElement` not found

7. Update an element’s value<br />
  Request: PUT /list/{id}<br />
  Request body: { "oldValue": 10, "newValue": 12 }<br />
  Response: { "listVersion": 9 }<br />
  Response Codes:<br />
    * 404 - Invalid ID `id`
    * 406 - Element `oldValue` not found

### Project Structure

## <a name="stage2"></a>Second stage. Jersey Framework
TODO

## <a name="stage3"></a>Third stage. Freemarker
TODO

## <a name="stage4"></a>Fourth stage. Docker
TODO
