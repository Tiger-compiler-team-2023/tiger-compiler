# pcl-goureau1u

Projet de Compilation 2022-2023 (PCL1 & PCL2).
Membres du groupe :
- Nicolas Frache (gpe. 41, IL)
- Théo Goureau (gpe. 41, IL)
- Cyrielle Lacrampe--Diter (gpe. 42, IL)
- Rida Moussaoui (gpe. 42, ISS)

---
# Utilisation de Maven
### Installer Maven
_Ubuntu_ : ```sudo apt install Maven```

_Windows_ : https://maven.apache.org/install.html#windows-tips
    
    ▶️ Ou utiliser les packages et extension MAVEN d'un IDE (beaucoup plus pratiques)

---
### Se déplacer dans le bon projet
```
cd AntlrMavenProject/chaoscompiler/
```

### Utiliser les goals MAVEN

- Nettoyer le projet

```
nvm clean
```

---
- Générer le lexer et le parser puis compiler

```
nvm compile
```

Pour génerer le lexer et le parser **sans** compiler le projet, on peut aussi faire:

```
mvn antlr4:antlr4
```

---

- lance les tests + tâches précèdentes si nécessaires
```
mvn test
```
---

- Executer le main manuellement avec gradle

```
nvm install
```
```
mvn exec:java -Dexec.mainClass=eu.tn.chaoscompiler.main.Main
```
---
- L'application peut également générer un jar totalement indépendant
```
mvn package
```
```
java -jar .\chaoscompiler-1.0-SNAPSHOT-jar-with-dependencies.jar
```

Qu'on peut adapter si besoin en fonction des versions de java, par exemple ;
```
C:\Users\USER\.jdks\openjdk-19\bin\java.exe -jar .\chaoscompiler-1.0-SNAPSHOT-jar-with-dependencies.jar
```










