# rest-assured-course

Brief starter project for practicing API testing with Rest-Assured (Maven-based).

## Create the project
Directory must be empty before running the archetype command.

Open PowerShell or CMD in the parent folder and run:

```
mvn archetype:generate -DgroupId=com.restassured \
                       -DartifactId=rest-assured-course \
                       -Dpackage=com.restassured \
                       -DarchetypeArtifactId=maven-archetype-quickstart \
                       -DarchetypeVersion=1.4 \
                       -DinteractiveMode=false
```

Then:
```
cd rest-assured-course
```

To open in VS Code:
```
code .
```