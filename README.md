# LiveTracking

This Application mainly use for tracking the user running workout and keeping all record within database.


The app follows the MVVM architecture with the repository pattern, alongside Dagger Hilt for DI.
Because of small project we kept classes in simple way.

### Folder structure

There are 4 main folders: di, models, network, ui, utils.
* db: For database related file
* di: Contain Dagger Hilt related files
* helper: This folder contain helper classes
* repository: This folder holding all repository classes uses in this application.
* ui: All views related classes present in this folder. The app follows MVVM architecture coding
pattern for this project.
** fragments: We are trying something defferent, because of small project we kept all fragment together
 in this folder
** viewmodel: All viewmodel classes taking placed in this folder.


### Design, libraries and other stuff applied

* Room database
* ViewModel and LiveData
* Kotlin CoRoutine
* Dagger Hilt for dependency injection
* MVVM architecture + Repository pattern
* Navigation Architecture Component
* ConstraintLayout
* Google Material Components

Whole project written in kotlin language.


### Permission!

1. Location permission (ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION, ACCESS_BACKGROUND_LOCATION)
2. Forground Service
