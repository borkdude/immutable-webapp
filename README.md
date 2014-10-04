# Immutable Webapp!

## Requirements
This workshop requires the following software:

* JDK 7
* git
* a modern webbrowser such as the latest Chrome, Safari or Firefox


## Getting started

### Install leiningen
Leiningen is the de facto build tool for Clojure. In this workshop we use it to download
dependencies for front- and backend.

Install [leiningen](http://leiningen.org) by following the instructions on this
[page](http://leiningen.org).

### Install IntelliJ
To edit Clojure code we will use IntelliJ and the Cursive plugin. If you like, you can skip
this step and use your favorite editor with Clojure support. Code changes are auto-compiled,
so you don't need IntelliJ for Clojure compilation.

Download and install IntelliJ via [this link](http://www.jetbrains.com/idea/download/).
The community edition of IntelliJ is free.
Version 13.1 is preferred, but version 12 and 13 are also supported by the Cursive plugin,
which we will install in the next step.

### Install the Cursive plugin for IntelliJ.
Open IntelliJ and open Preferences.
Proceed with the instructions on [this page](https://cursiveclojure.com/userguide/)
to install the Cursive plugin.
You might need to restart IntelliJ after the plugin has been installed.
To open a leiningen project, just open the `project.clj` file with IntelliJ.

### Download project and dependencies

    cd ~/projects # or any other directory you prefer
    git clone https://github.com/borkdude/immutable-webapp
    cd immutable-webapp
    lein deps # forces leiningen to download dependencies
              # not needed as a separate step,
              # but a good test to see if
              # leiningen works

### Running the project

To compile Clojurescript open a terminal window and cd into immutable-webapp's directory.
Then run `lein cljsbuild auto`. Changes in the Clojurescript code of this project are
automatically picked up.

In another terminal window again cd into immutable-webapp's directory and run `lein ring server`.
Browser to `http://localhost:8090/index.html` and you should see "Hello world!"


## Slides

The final slides for this workshop can be found [here](https://rawgit.com/borkdude/immutable-webapp/master/sheets/presentation.html).

## License

Copyright © 2014

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
