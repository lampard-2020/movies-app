# **Android Movie App**
Android mobile app that allows the user to search for any movie title, view the list of search results. The
app shall fetch the movies data from The Open Movie Database (OMDb) REST API.

**TECHSTACKS**

- Language: Kotlin
- Architecture: MVVM
- Libraries & Dependences: 
    - UI: Navigation, RecycleView, ViewBinding, Paging 3
    - Coroutine, Kotlin Flow: Executes asynchronously
    - Interact Restful API: Retrofit, OkHttp, Gson
    - Dependency Injection: Dagger-Hilt, 
    - Support MVVM Architecture: lifecycle ViewModel, Kotlin Flow
    - Testing: JUnit Test, AndroidX Test Core, Mockito

**PROJECT STRUCTURE**

- Project structure follows MVVM architecture: 

    - Data, Service, Repository: help interact Restful API
    - DI: support dependency injection using dagger-Hilt
    - Data: come from service
    - Entity: come from database
    - Domain: For update UI

# Screenshot

<img src="https://user-images.githubusercontent.com/71933873/218329601-4fcc1d5b-a704-4c51-a8d4-3e29e884bbd4.png" width=640 />

# Recording

https://user-images.githubusercontent.com/71933873/218329568-8e226d3a-2d45-41b8-96ee-a39d34baa8aa.mp4

