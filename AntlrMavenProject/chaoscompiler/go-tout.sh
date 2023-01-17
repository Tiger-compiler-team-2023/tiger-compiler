mvn clean -f "/home/theog/Documents/PCL1/pcl-goureau1u/AntlrMavenProject/chaoscompiler/pom.xml"
mvn compile -f "/home/theog/Documents/PCL1/pcl-goureau1u/AntlrMavenProject/chaoscompiler/pom.xml"
mvn exec:java -Dexec.mainClass=eu.tn.chaoscompiler.main.Main -Dexec.args="./src/test/ressources/erreursSemantiques/tout.tig"