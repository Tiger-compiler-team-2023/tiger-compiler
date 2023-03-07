# arm64
### Ce dépôt Git contient des macros et des fonctions de base en arm64 pour Linux.

---
## Contexte
Ces implémentations s'inscrivent dans la phase de génération de code du projet de compilation de deuxième année à [TELECOM Nancy](https://telecomnancy.univ-lorraine.fr) : il s'agit de créer un compilateur du langage Tiger introduit par [Andrew W. Appel](https://www.cs.princeton.edu/~appel/) dans son livre *Modern Compiler Implementation*.

---
## Sommaire
1. [Contenu des fichiers](#P1)
- `base_macros.s`
- `base_functions.s`
- `arithmetic_functions.s`
2. [Conventions](#P2)
- Utilisation des registres
- Appels de fonction
- Statuts de retour
3. [Utiliser les modules](#P3)
4. [_Compiler_ le code assembleur](#P4)
5. [Déboguer votre code arm64](#P5)
- Installation des outils
- Utilisation des outils
6. [Tests](#P6)

---
## Contenu des fichiers <a name="P1"></a>
### `base_macros.s`
Contient des macros.
- `exit code` (où code a une valeur entre 0 et 255) quitte l'exécution du processus avec le code `code`.
- `push Xn` empile le registre `Xn`.
- `pop Xn` désempile en écrivant la valeur dans le registre `Xn`.
- `push915` empile tous les registres de `x9` à `x15`.
- `pop915` désempile dans les registre de `x15` à `x9`.
- `push1927` empile tous les registres de `x19` à `x27`.
- `pop1927` désempile dans les registre de `x27` à `x19`.
- `mod Xa, Xb, Xc` écrit dans `Xa` le reste de la division euclidienne de `Xb` par `Xc`.
- `print` écrit sur la sortie standard le contenue de la chaîne de caractères à l'adresse `x1` de longueur `x2`
- `input` lit sur la sortie standard et écrit dans le buffer d'adresse `x1` et de taille `x2` ; `x0` prend la valeur du nombre de caractères lus.
- `err im` gère les erreurs et exit avec `im` un code entre 0 et 255.
### `base_functions.s`
Contient des fonctions et des données (les buffers).
- `print_int16` (1 arg -> 0 res) écrit l'entier `arg1/1` sur la sortie standard en base 16 (e.g. 54000 -> `0x 0000 0000 0000 d2f0`).
- `print_int10` (1 arg -> 0 res) écrit l'entier `arg1/1` sur la sortie standard en base 10 (e.g. 54000 -> `54 000`).
- `input_int10` (0 arg -> 1 res) lit jusqu'à 31 chiffres (ou '-' et 30 chiffres) sur l'entrée standard et écrit la valeur de l'entier en base 10 lu au sommet de la pile.
- `malloc` (1 arg -> 1 res) réserve `arg1/1` octets dans le tas et écrit l'adresse de l'espace alloué au sommet de la pile (exit avec un statut 32 en cas d'erreur).
- `error` (1 arg -> 0 res) gère les erreurs et exit avec le code `arg1/1` compris entre 0 et 255.
### `data_functions.s`
Contient des fonctions.
- `array_assign` (2 arg -> 1 res) écrit l'adresse du tableau de taille arg1/2 dont tous les éléments sont arg2/2 au sommet de la pile (tests semantiques : taille >= 0 et bon fonctionnement de l'allocation mémoire).
- `array_access` (2 arg -> 1 res) écrit l'adresse de la case du tableau d'adresse arg1/2 et d'indice arg2/2 au sommet de la pile (tests semantiques : indice >= 0 et indice < size). On utilise `str` et `ldr` pour modifier les valeurs des cellules du tableau, cette fonctions sert principalement aux tests sémantiques et à calculer le déplacement.
### `arithmetic_functions.s`
Contient des fonctions.
- `ari_int_neg` (1 arg -> 1 res) écrit l'opposé de `arg1/1` au sommet de la pile où `arg1/1` est un entier.
- `ari_int_add` (2 arg -> 1 res) écrit `arg1/2` + `arg2/2` au sommet de la pile où `arg1/2` et `arg2/2` sont des entiers.
- `ari_int_sub` (2 arg -> 1 res) écrit `arg1/2` - `arg2/2` au sommet de la pile où `arg1/2` et `arg2/2` sont des entiers.
- `ari_int_mul` (2 arg -> 1 res) écrit `arg1/2` * `arg2/2` au sommet de la pile où `arg1/2` et `arg2/2` sont des entiers.
- `ari_int_div` (2 arg -> 1 res) écrit `arg1/2` / `arg2/2` au sommet de la pile où `arg1/2` et `arg2/2` sont des entiers. Gère le cas de la division par 0 avec un message d'erreur et un exit.
- `ari_log_and` (2 arg -> 1 res) écrit `arg1/2` & `arg2/2` au sommet de la pile où `arg1/2` et `arg2/2` sont des booléens (entiers tels que 0 vaut faux et tout autre nombre vaut vrai).
- `ari_log_or` (2 arg -> 1 res) écrit `arg1/2` | `arg2/2` au sommet de la pile où `arg1/2` et `arg2/2` sont des booléens (entiers tels que 0 vaut faux et tout autre nombre vaut vrai).
- `ari_int_EQ` (2 arg -> 1 res) écrit `arg1/2` = `arg2/2` au sommet de la pile où `arg1/2` et `arg2/2` sont des entiers.
- `ari_int_NE` (2 arg -> 1 res) écrit `arg1/2` <> `arg2/2` au sommet de la pile où `arg1/2` et `arg2/2` sont des entiers.
- `ari_int_GT` (2 arg -> 1 res) écrit `arg1/2` > `arg2/2` au sommet de la pile où `arg1/2` et `arg2/2` sont des entiers.
- `ari_int_LT` (2 arg -> 1 res) écrit `arg1/2` < `arg2/2` au sommet de la pile où `arg1/2` et `arg2/2` sont des entiers.
- `ari_int_GE` (2 arg -> 1 res) écrit `arg1/2` >= `arg2/2` au sommet de la pile où `arg1/2` et `arg2/2` sont des entiers.
- `ari_int_LE` (2 arg -> 1 res) écrit `arg1/2` <= `arg2/2` au sommet de la pile où `arg1/2` et `arg2/2` sont des entiers.
- `ari_str_EQ` (2 arg -> 1 res) écrit `arg1/2` = `arg2/2` au sommet de la pile où `arg1/2` et `arg2/2` sont des chaînes de caractères.
- `ari_str_NE` (2 arg -> 1 res) écrit `arg1/2` <> `arg2/2` au sommet de la pile où `arg1/2` et `arg2/2` sont des chaînes de caractères.
- `ari_str_GT` (2 arg -> 1 res) écrit `arg1/2` > `arg2/2` au sommet de la pile où `arg1/2` et `arg2/2` sont des chaînes de caractères.
- `ari_str_LT` (2 arg -> 1 res) écrit `arg1/2` < `arg2/2` au sommet de la pile où `arg1/2` et `arg2/2` sont des chaînes de caractères.
- `ari_str_GE` (2 arg -> 1 res) écrit `arg1/2` >= `arg2/2` au sommet de la pile où `arg1/2` et `arg2/2` sont des chaînes de caractères.
- `ari_str_LE` (2 arg -> 1 res) écrit `arg1/2` <= `arg2/2` au sommet de la pile où `arg1/2` et `arg2/2` sont des chaînes de caractères.

---
## Conventions <a name="P2"></a>
### Utilisation des registres
|Registre(s)  |Utilisation  |
|---  |---  |
|x0 - x7  | Paramètres et retours de fonctions  |
|x8 (XR) | Appels systèmes |
|x9 - x15 | Registres de travail qui doivent être empilés par l'appelant  |
|x19 - x27 | Registres de travail qui doivent être empilés par l'appelé  |
|x28 | Chaînage statique |
|x29 (FP) | Frame pointer |
|x30 (LR) | Link register |
### Appels des fonctions
WIP
### Statuts de retour
|Statut  |Signification  |
|---  |---  |
|0  | Aucune erreur  |
|32  | Erreur d'allocation de mémoire (`malloc`)  |
|33  | Division par 0  |
|34  | Overflow lors d'un calcul arithmétique (n'est pas implémenté)  |
|35  | Index out of range  |
|36  | Taille ou indice négatif  |
|Autre  | Erreur inconnue  |


---
## Utiliser les modules <a name="P3"></a>
Le mot-clé `include` permet de d'intégrer le contenu d'autres fichiers dans votre code et donc de découper votre code en modules. Un exemple de code important les trois modules est donnée ci-dessous.
```arm
.include    "base_macros.s"
// MACROS


// fin MACROS
.section    .text
.global     _start
_start:
// EXECUTION


// fin EXECUTION
	exit    #0
// FUNCTIONS


// fin FUNCTIONS
.include    "arithmetic_functions.s"
.include    "data_functions.s"
.include    "base_functions.s"
// DATA


// fin DATA
```

---
## _Compiler_ le code assembleur <a name="P4"></a>
La compilation du code assembleur se fait en deux étapes : du code source au code objet puis du code objet à l'exécutable. Exemple avec le script `exemple.s`.
```bash
$	as -o exemple.o exemple.s -g    # source -> objet
$	ld -o exemple exemple.o         # object -> exécutable
```
NB : l'option `-g` permet d'utiliser l'outil de débogage présenté dans la partie suivante, elle n'est pas nécessaire.

Je vous conseille d'utiliser un [Makefile](https://www.gnu.org/software/make/manual/make.html).

---
## Déboguer votre code arm64 <a name="P5"></a>
Pour débogger, nous utilisons [gdb (GNU DeBugger)](https://doc.ubuntu-fr.org/gdb) et [gef](https://github.com/hugsy/gef).\
Cette installation requiert `curl` et `python` (3.6+).
### Installation des outils
```bash
      # gdb
$  sudo apt-get install build-essential gdb
      # gef
$  bash -c "$(curl -fsSL https://gef.blah.cat/sh)"
```
NB : l'installation de `gef` est à effectuer sur chaque session.
### Utilisation des outils
N'oubliez pas de compiler avec l'option `-g`.\
Lancer `gdb` et `gef` :
```bash
$  gdb exemple                     # exemple est l'exécutable
```
Ajouter un point d'arrêt en donnant un label :
```bash
gef➤  break _start                 # breakpoint au label _start
Breakpoint 1 at 0x4000b0: file exemple.s, line 11.
```
Lancer l'exécution :
```bash
gef➤  run
```
Vous verrez l'état des registres, l'état de la stack, la prochaine ligne à être exécutée et les données derrières les adresses dans les registres et dans la stack.\
Vous pouvez passer à la ligne suivante avec `step`.
```bash
gef➤  step
```
Vous pouvez quitter directement avec `Ctrl+D`.

---
## Tests <a name="P6"></a>
Des tests des fonctions arithmétiques (`arithmetic_functions.s`) sont implémenté et peuvent être lancés de la manière suivante :
```bash
$  cd tests
$  bash test_arithmetic_functions.sh 
```
