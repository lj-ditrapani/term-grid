Terminal Grid
=============


Treat the terminal like a grid of colored (foreground and background) text cells.

![Example](termgrid.gif)

Develop
-------

    ./gradlew build
    ./gradlew test -i
    ./gradlew jacocoTestReport


Documentation
-------------

    ./gradlew javadoc


Visual test
-----------

    sh test.sh


Format
------

    ./gradlew goJF


Static analysis
---------------

    ./gradlew check


Example: Game of life in Kotlin
-------------------------------

    kotlinc -cp build/libs/term-grid.jar -script game-of-life.kts


![Example](game-of-life.gif)
