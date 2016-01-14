## TestFX-Playground

This repository contains sample projects for [TestFX](https://github.com/TestFX/TestFX).


### Structure

- `samples/basics`: Getting started with a desktop pane, login and counter application.
- `samples/issues`: SSCCE examples for issues from GitHub and the TestFX mailing list.


### Contribute

The playground uses the Gradle build system. You can contribute new samples doing the following:

- Include your sample as project in `settings.gradle`, e.g. `include "samples/my-samples"`.
- Run the Gradle task `initSourceDirs` to initialize the main and test source sets.
- Add a build file with your library dependencies for your sample, e.g. `samples/my-samples/my-samples.gradle`.
