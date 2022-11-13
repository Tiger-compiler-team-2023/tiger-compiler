Antlr sur windows :
```shell
java -jar .\lib\antlr-4.11.1-complete.jar .\src\main\java\parser\expr.g4 -no-listener -no-visitor -o .\
```
Sur Linux :
```shell
java -jar ./lib/antlr-4.11.1-complete.jar ./src/main/java/parser/expr.g4 -no-listener -no-visitor -o ./
```

```shell
./src/test/ressources/correct/01_test
```
---
Gestion des erreurs :
https://stackoverflow.com/questions/18132078/handling-errors-in-antlr4

---
## Tests
- Installation Junit sur IntelliJ : https://stackoverflow.com/questions/19330832/setting-up-junit-with-intellij-idea
- Voir doc Junit : https://junit.org/junit5/docs/current/user-guide/

java -cp C:\Users\nicol\OneDrive\1-TELECOM_2A\PCL_1\projet_compil_1\java-project/lib/junit-4.13.2.jar org.junit.runner.JUnitCore src/test/java/IncorrectTests

javac -d C:\Users\nicol\OneDrive\1-TELECOM_2A\PCL_1\projet_compil_1\java-project\out\production\java-project -cp C:\Users\nicol\OneDrive\1-TELECOM_2A\PCL_1\projet_compil_1\java-project\lib\antlr-4.11.1-complete.jar C:\Users\nicol\OneDrive\1-TELECOM_2A\PCL_1\projet_compil_1\java-project\src\test\java\IncorrectTests.java