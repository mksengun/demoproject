# demoproject

From a high level point of view the demo consists of a list of posts, where each post has its own detail.

## Libraries 

### Realm
Realm is a mobile database and a replacement for SQLite. In this project we used in local repository classes.

### Retrofit
Retrofit is a REST Client for Android and Java by Square. It makes it relatively easy to retrieve and upload JSON (or other structured data) via a REST based webservice. 

In this project we got our data from http://jsonplaceholder.typicode.com 

* http://jsonplaceholder.typicode.com/users
* http://jsonplaceholder.typicode.com/comments
* http://jsonplaceholder.typicode.com/posts

## Architecture 

This project has Model-View-Controller architecture. The MVP pattern allows separate the presentation layer from the logic, so that everything about how the interface works is separated from how we represent it on screen. 

### Visiulation
![alt text](http://mksengun.com/mvp.png)

### Presenter
The presenter is responsible to act as the middle man between view and model. 

### View
The view, implemented by an Fragment and will contain a reference to the presenter.

### Model
The model has business logic in this application. 

## Data Flow

### List
In list view firstly we ask for a data from view to presenter. Presenter requests data from repository. Repository is simply responsible for geting data. Bussiness logic starts to work from here. Repository checks for remote data, if successfully gets data from remote api, remote repository passes data to presenter and saves data to local database to use it later. If connection failes with remote server, repository goes for local source which is realm database and gets data from there. If there is no data in the database then list will be empty for the view.

### Detail
In detail view post includes user and comment data. We already got post data from previus screen. When we go to detail screen, body and title comes with fragment transition from list. From now on we have to get other user and comment data from repository. We ask for comment data firstly, if success then we ask for user data, if success we will update the view. If one of those requests to remote server fails we dont update view with real data. 

### _sources_
* http://realm.io
* http://square.github.io/retrofit/
* https://antonioleiva.com/mvp-android/
* https://github.com/googlesamples/android-architecture/tree/todo-mvp/
* http://www.androidhive.info/2016/05/android-working-with-realm-database-replacing-sqlite-core-data/
* http://www.androidhive.info/2016/05/android-working-with-retrofit-http-library/
* http://www.vogella.com/tutorials/Retrofit/article.html
* http://www.androidhive.info/2016/01/android-working-with-recycler-view/


