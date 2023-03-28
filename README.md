# Web Service for Partially Persistent List Management

The repository contains a solution of the [3 test task](https://gist.github.com/mmazurkevich/a96393788246f85b2d2adf2cbff2af0e) in [Workspace Model Metadata Storage Team](https://internship.jetbrains.com/projects/1327/).

_Implemented with Kotlin_

## Links
* [Implementation using Spring Boot](#stage1)
  1. [Request and Response Specification](#specification1)
  2. [Project Structure](#project1)
      1. [Controller Layer](#controller-layer)
      2. [Service Layer](#service-layer)
      3. [Repository Layer](#repository-layer)
      4. [Database](#database)
      5. [Testing](#testing)
* [Implementation using JAX-RS. Asynchronous REST API](#stage2)
  1. [Request and Response Specification](#specification2)
  2. [Project Structure](#project2)
      1. [Util Classes](#util-classes)
      2. [Package Async](#package-async)

## <a name="stage1"></a>First stage. Spring Boot
Web Service Implementation using Spring Boot Framework, ORM Framework Hibernate (included in Spring Boot) and H2 Database (in-memory-database).

[_Open project folder_](https://github.com/BagritsevichStepan/wmms-test-task3/tree/main/wmms-test-task3)

### <a name="specification1"></a>Request and Response Specification
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

### <a name="project1"></a>Project Structure

#### Controller Layer
![controller layer structure](https://github.com/BagritsevichStepan/wmms-test-task3/blob/main/images/spring/2.png)

`PersistentListVersionsController` - Controller that mapps requests with path /lists...

`PersistentListUpdateController` - Controller that mapps requests with path /list/{id}...

`PersistentListController` - Common controller

#### Service Layer
![controller layer structure](https://github.com/BagritsevichStepan/wmms-test-task3/blob/main/images/spring/3.png)

`ListService` - Generic Interface which is responsible for the list service and has methods for updating the list

`PersistentService` - Generic Interface which is responsible for the service of persistent data structure and has method `getVersions`

`PersistentListService` - Interface which is responsible for the persistent list service, inherits `ListService` and `PersistentService`

#### Repository Layer
In the repository layer we only need one interface `PersistentListRepository` which inherits `JpaRepository<Version, Long>`:
```
interface PersistentListRepository: JpaRepository<Version, Long> {
    @Query(value = "select p.id from Version p")
    fun getAllIds(): List<Long>
}
```
Method `getAllIds` is used to respond to the request to give all available versions

#### Database
Using the Hibernate Framework we need to create a table `Version` with annotations `@Entity` and `@Table` that stores the versions of the lists:
```
@Entity
@Table
data class Version(
    @Id
    @GeneratedValue
    var id: Long? = null,

    @NotNull
    @ElementCollection
    @Column
    var persistentListVersion: List<Int>? = null
)
```

#### Testing
Technologies that were used for testing: SpringBootTesting, JUnit and Mockito

## <a name="stage2"></a>Second stage. Asynchronous REST API

Of course Spring Boot is too big tool for this web service. So I decided to use JAX-RS and JPA for this task. I also decided to add asynchronous requests to my solution.

Technologies that were used: Jersey Framework, Hibernate, Java Multithreading, D2 Database.

[_Open project folder_](https://github.com/BagritsevichStepan/wmms-test-task3/tree/main/test-task3-async)

### <a name="specification2"></a>Request and Response Specification

The format of the requests is the same. But for asynchronous request you must add `/async` at the beginning of each path, for e. g. `GET /async/list/1` to get the elements of the list with id=1  asynchronous.

### <a name="project2"></a>Project Structure

The structure of the project remains almost the same. Except for the addition of several classes that "link" the components of the web service. Before that, the spring boot did it instead of us.

#### Util Classes

`PersistentListApplication` is the class that specifies where our application is located. It inherits the class `Application` and has no methods:

```
@ApplicationPath("")
class PersistentListApplication : Application()
```

`EntityManagerUtil` is the util class that stores `EntityManagerFactory`, and when the method `getEntityManager` is called, it creates and returns a new `EntityManager`:

```
class EntityManagerUtil {
    companion object {
        var entityManagerFactory: EntityManagerFactory =
            Persistence.createEntityManagerFactory("list");

        fun getEntityManager(): EntityManager {
            return entityManagerFactory.createEntityManager()
        }

        fun close() {
            entityManagerFactory.close()
        }
    }
}
```

#### Package Async

In addition, the package `async` was added, which contains classes that implement asynchronous response to requests. When requested, they send the task to the common pool of threads (class `ConcurrencyUtil`), and the unique EntityManager is created for each thread. Each thread obtains its instance, work with it, and close it at the end. Using an instance of the class `AsyncResponse`, threads return the result to the user.

The `ConcurrencyUtil` class uses a pool of threads with a fixed size (size equal to the number of processors available for Java).

```
class ConcurrencyUtil {
    companion object {
        private val executorService: ExecutorService = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
        )

        fun submit(task: Runnable) {
            executorService.submit(task)
        }

        fun shutdown(): MutableList<Runnable> = executorService.shutdownNow()
    }
}
```















