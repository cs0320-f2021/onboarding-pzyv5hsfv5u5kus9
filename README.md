# Project 0: Onboarding

##Description
Simple REPL for basic arithmetic and naive knn algorithm.

###Design
####Stars
- a class for Stars objects which parses CSVs, calculates knn given a position or a known star in the stored CSV.

####MathBot
- a class for MathBot objects for basic addition and subtraction
## Run
To build use:
`mvn package`

To run use:
`./run`

This will give you a barebones REPL, where you can enter text and you will be output at most 5 suggestions sorted alphabetically.

To start the server use:
`./run --gui [--port=<port>]`

##Known Errors or Bugs
None

##Tests
- StarTest: a JUnit testing suite for ensuring Stars class works correctly
- multistar_pos.test: test for knn algorithm where k > 1 and origin is given as position
- wrong_star_name.test: test for handling star names which are not in the current database
- overwrite.test: test for ensuring new data overwrites old data



##Contributors
zhuang68