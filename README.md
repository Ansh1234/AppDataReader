# AppDataReader

A library for reading Shared Preferences and Database values within the application. 


## Description

Similar to this library, there are existing libraries like Facebook's Stetho, which does the exact thing of reading the application's database and Shared Preferences. But the values are shown in the browser. With the help of this library, the  developer does not need to connect the application to the browser. Within the phone itself, the developer can see the values. 

## Advantages of using this library
* No Java Code. Only gradle dependency.
* Read all the app data within the phone without the need of a browser.
* See all the Shared Preferences at once or file by file.
* Copy the value of an individual column of Shared Preference and Database tables to Clipboard.
* Shows the count of the Shared Preferences in a file and  rows in a table.
* With the help of some additional gradle code, this library will pick up the compileSdk Version, targetSdk Version and  Build Tools Version from the main project.

## Install the library
```
compile 'com.awesomedroidapps:inappstoragereader:1.0.0-beta'
```
Or if the library is to be used only for debug builds and not release builds, then 
```
debugCompile 'com.awesomedroidapps:inappstoragereader:1.0.0-beta'
```

The library is using two dependencies for building the UI i.e. `RecyclerView v23.2.0` and the `Support Library v23.2.0`. In case, your project is also using these dependencies, but with a different version, then declare the following variables in the `build.gradle` file of the root project.

```
project.ext {
    rvv = '23.1.0' //RecyclerView  version
    slv = '23.1.0' // Support AppCompat v7 library version.
    csv = 23 // compile Sdk version
    tsv = 23 //Target Sdk version
    btv = '23.0.2' // Build tools version.
 }
```


The library declares an activity `AppDataListActivity` with action `android.intent.action.MAIN` in the `AndroidManifest.xml`. So as soon as your application is installed, this activity will also be placed in the launcher of the phone. Search for **App Data** on the launcher.  

## Demo 

&nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; ![](appstoragereader.gif)

***

## Tentative features for the next release

* Query the database and change the values within the application.
* Copy an entire row to Clipboard.
* Export an entire table in csv format.


=====

Checkout my other projects

[RxDownloader](https://github.com/Ansh1234/RxDownloader)- Demo of Downloading Songs/Images through Android Download Manager using RxJava2
[RxFbLiveVideoEmoticons](https://github.com/Ansh1234/RxFbLiveVideoEmoticons) - Demo of Fb Live Video Reactions using RxJava2


