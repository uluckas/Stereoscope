#!/bin/sh
./gradlew assemble && scp Stereoscope.js/build/bundle.js rambrand:www/musoft.de/webroot/stereoskope/build/bundle.js
