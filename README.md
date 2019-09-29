BUILD AND RUN

1. HOW TO BUILD.
Extract the archive. Run a build.bat file. It's actually a one command, so in case you don't use Windows, build the app by typing a command "mvn clean install" while being in project directory.
2. HOW TO RUN.
After building it, run a run.bat file. It's a one liner too, so in case of not using Windows you can type "java -jar target\multiplex-0.0.1-SNAPSHOT.jar" while being in project directory.
3. HOW TO TEST.
After running it, execute curl-test.bat.

Additional assumptions.

1. The system accepts ISO-formatted UTC dates in the "screenings in a given time interval" requests. That is a format which potential JavaScript driven front-end would use, so the system uses it too. The system converts those dates to zone-less timestamps for internal use, but they are actually system's zone timestamps. I assume that this system's zone is Polish. During a response, the system converts back all dates to ISO UTC.
2. The system's reservation response contains expiration date. I assume that the system has to store and process those dates, as otherwise giving them would be pointless or even confusing, because reservations would be of unlimited duration. The system filters out outdated reservation A) during a free seat request. B) during a reservation. The system considers a seat as a free seat in two cases: when it has no reservation or reservation is outdated. So what I also assume is that the system is supposed to store outdated reservations and to replace them with new ones.
3. The system supports English and Polish characters only. This is due to the fact that there is no mention of any other character set in requirements.
4. I assume that rooms can vary in size and also that number of rows can differ from the number of columns.
5. The scripts are written for windows - as .bat files. The command line does not support certain functionality of single quotes - when they wrap json's double quotes elements. So instead of escaping every single quote in command line(which would make them hard to read), I put the data to .json files.