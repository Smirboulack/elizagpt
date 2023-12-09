<!-- LTeX: language=fr -->
# MIF01 - TP Remise en route JAVA

**Objectif :**

Il vous est demandé de mettre en place quelques classes pour vous remettre en
tête les grands principes de la programmation orientée objet : messages et
collaboration entre objets, attributs et méthodes, constructeurs, héritage, etc.
Pour cela, vous manipulerez un chatbot, inspiré de ChatGPT (mais en réalité ses
capacités sont plus proches de celle du programme
[ELIZA](https://en.wikipedia.org/wiki/ELIZA) développé dans les années 60).

Votre travail servira de base aux TPs suivants qui feront l'objet d'une note
globale (voir le fichier [projet-note.md](projet-note.md) pour les consignes sur
l'ensemble du projet). Le travail se fait en binômes (malus sur la note pour les
étudiants travaillant seul). Si vous ne trouvez pas de binôme, postez un message
sur l'issue #1 du dépôt enseignant. Pour des raisons logistiques, évitez les
binômes entre un alternant et un étudiant. Il n'y a pas d'autre contrainte pour
cette UE, mais d'autres UE ont des règles différentes.

Ce TP devrait vous prendre environ une séance.

## Environnement

Pour développer en Java durant le TP, il est fortement recommandé d'utiliser, un
environnement de développement intégré (comme VSCode, IntelliJ IDEA, Eclipse,
Netbeans, etc) qui permet de compiler, de générer un projet, de débugger et
d'exécuter. 

Vous pouvez aussi choisir d'utiliser n'importe quel éditeur de texte avec
coloration syntaxique, mais les outils que nous utilisons dans l'UE sont en
général plus agréables à utiliser via l'intégration IDE.

<!-- TODO: est-ce encore vrai pour Windows ? -->
Le TP n'a été testé que sous Linux. Sur les machines de l'université le TP
fonctionne sous Linux, mais les IDE Java n'ont pas l'air installés sous Windows.
Sur vos machines personnelles, vous pouvez utiliser l'OS de votre choix.

### Sur les machines du Nautibus sous Linux

Sur les machines du Nautibus, les TPs ont été testés sous Linux, en
utilisant l'environnement Java par défaut (sans rien configurer).

#### UNIQUEMENT en cas de problème avec l'environnement par défaut

En cas de besoin, il y a aussi une version installée pour vous dans
`/home/tpetu/m1if01/` (à une époque où la version par défaut posait
problème). Pour l'utiliser (en cas de soucis avec la version de base),
avant de démarrer le TP, ajoutez ceci dans votre fichier `~/.bashrc`:

    PATH=/home/tpetu/m1if01/bin:"$PATH"

Puis rechargez le fichier (`exec bash` par exemple). Vérifiez que vous
obtenez bien :

    $ which java
    /home/tpetu/m1if01/bin/java
    $ which javac
    /home/tpetu/m1if01/bin/javac
    $ which mvn
    /home/tpetu/m1if01/bin/mvn

### Sur vos machines personnelles

Sur vos machines personnelles, en cas de problème sous Linux, il peut
être nécessaire d'installer JavaFX explicitement (`sudo apt install
openjfx` sous Ubuntu, ou bien téléchargement depuis
[openjfx.io](https://openjfx.io)). Le TP a été testé avec Java 11, il
ne marchera probablement pas sans adaptation avec des versions plus récentes
(il faudra peut-être version de JavaFX dans `pom.xml`).

Si vous avez installé JavaFX via votre distribution et que Java ne
trouve pas les classes JavaFX, ajoutez explicitement les fichiers JAR
concernés à votre classpath, avec quelque chose comme :

    CLASSPATH="$CLASSPATH":/usr/share/java/openjfx/jre/lib/ext/jfxrt.jar
    CLASSPATH="$CLASSPATH":/usr/share/java/openjfx/jre/lib/jfxswt.jar
    export CLASSPATH

Sur machines personnelles, vérifiez que vous avez bien `git` et `mvn`
installés :

    $ git --version
    git version 2.34.1
    $ mvn --version
    Apache Maven 3.6.3
    [...]

(Vous pouvez bien sûr avoir des versions différentes)

Si ce n'est pas le cas installez-les. Sous Ubuntu, faire :

    apt install git maven

Si quelque chose ne marche pas, regardez si la solution n'est pas documentée dans [FAQ.md](../FAQ.md).

## Création d'un projet sur la forge et récupération du code

Ouvrez dans votre navigateur
[forge.univ-lyon1.fr](http://forge.univ-lyon1.fr). Si vous vous
connectez pour la première fois, le système vous permettra de
vérifier/modifier les informations qui vous concernent, idem pour
votre éventuel binôme.

Nous allons utiliser le dépôt Git du cours comme base pour votre
projet. Ouvrez la page
[https://forge.univ-lyon1.fr/matthieu.moy/mif01](https://forge.univ-lyon1.fr/matthieu.moy/mif01),
et cliquez sur le bouton « fork ». Ce bouton vous permet de récupérer
une copie du projet sur votre espace de la forge.

**IMPORTANT** : pour l'instant, le fork de votre projet est public sur
la forge. Nous vous demandons **impérativement de passer ce projet en
« privé »** pour que vos collègues ne puissent pas recopier votre code.
En cas de copie, nous sanctionnerons sévèrement les étudiants ayant copié **et**
ceux ayant laissé copier leur code. Pour rendre votre projet privé,
rendez-vous dans « settings → general » en bas de la barre latérale de
gauche, puis « Permissions ». Le premier réglage est « Project
visibility ». Dans le menu, choisissez « private », puis cliquez sur
le bouton « save changes ».

Pour vérifier que votre projet est bien privé (indispensable),
retournez à la page d'accueil de votre projet
(`https://forge.univ-lyon1.fr/votre.nom/mif01`) et copiez l'URL.
Ouvrez une fenêtre de navigation privée
(<kbd>Control</kbd>+<kbd>Shift</kbd>+<kbd>P</kbd> sous Firefox,
<kbd>Control</kbd>+<kbd>Shift</kbd>+<kbd>N</kbd> sous Chrom{e,ium}), et collez l'URL de votre projet
dans la barre d'URL. Comme le projet est privé, vous devez avoir le
message « You need to sign in or sign up before continuing. ». Si ce
n'est pas le cas, vous avez raté quelque chose, recommencez la
manipulation.

Bien sûr, il faudra donner les droits à vos enseignants, cf.
[projet-note.md](projet-note.md), et à votre binôme. Tout cela se fait dans
Configuration → Membres.

Pour vos projets futurs, vous pourrez aussi créer des projets à partir
de zéro. Pour cela, vous pourrez faire simplement « new project »
(bouton **+** en haut de l'écran).

Récupérez une copie locale du code avec la commande (l'URL est à
ajuster et vous est donnée par la forge quand vous ouvrez votre projet
avec votre navigateur) :

```
git clone https://forge.univ-lyon1.fr/votrelogin/mif01.git
cd mif01
```

[En cas de problème, voir la FAQ de la forge](https://forge.univ-lyon1.fr/EMMANUEL.COQUERY/forge/wikis/FAQ).

Une fois le clone fait, indiquez l'URL de votre projet (la même pour les deux
membres du binôme) sur TOMUSS. Voir consignes détaillées dans
[projet-note.md](projet-note.md).

Ce fork est votre version du projet, c'est là que vous ferez votre développement.
En cas de mise à jour du dépôt du cours, votre dépôt ne recevra pas
automatiquement les mises à jour. Nous verrons au TP2 comment récupérer facilement les mises à jour depuis le dépôt enseignant.

## Premier contact avec le projet

On vous fournit un squelette [eliza-gpt/](../eliza-gpt/).

Le projet utilise l'outil Maven pour la compilation. Nous en parlerons
plus en détails en CM, pour l'instant vous devez seulement savoir :

- Le projet est décrit dans le fichier `pom.xml`. Vous pouvez regarder
  le contenu de ce fichier, mais vous n'avez pas besoin de le modifier
  pour ce TP.
  
- `mvn compile` compile le projet

- `mvn exec:java` ou `mvn javafx:run` lancent le programme compilé

### Premier contact avec l'application

Lancez l'interface graphique (`mvn exec:java`). Vous devriez voir s'afficher une
fenêtre dans laquelle vous pouvez dialoguer avec une IA minimaliste :

![Interface de ELIZA-GPT](img/eliza-gpt.png)

Vous pouvez entrer du texte dans le champ en bas de la fenêtre, valider avec la
touche <kbd>Entrée</kbd> ou le bouton « search ». Vous pouvez effacer vos
messages en cliquant sur le message à supprimer.

Un champ recherche est également disponible en haut de l'écran pour filtrer les
messages contenant une chaîne de caractères. Dans la version actuelle, les
messages ne contenant pas la chaîne sont non-seulement cachés, mais
définitivement oubliés, donc on ne peut pas annuler une recherche (le bouton «
Undo search » lance une exception). Ce sera à vous d'améliorer cela (mais
l'implémentation est tellement affreuse qu'il faudra avant tout coder ça
proprement et l'annulation sera triviale à coder dans une base de code propre).

Les chatbots de ce genre sont apparus très tôt dans l'histoire de
l'informatique. Le nom de notre projet est tiré du célèbre programme
[ELIZA](https://en.wikipedia.org/wiki/ELIZA) écrit entre 1964 et 1966, et plus
récemment de [ChatGPT](https://openai.com/blog/chatgpt).

### Le code source du squelette fourni

Le squelette contient ces classes :

-   `view.JfxView` : une classe gérant l'interface graphique. Vous pouvez jeter
    un œil au code source de cette classe. Si vous trouvez le code propre, ce
    cours est là pour vous montrer que ce n'est pas le cas, il y a beaucoup de
    choses dans ce squelette qu'un bon programmeur ne fait *jamais*. Bien sûr,
    toutes ces horreurs devront avoir disparues de la version finale que vous
    rendrez.

-   Un embryon de package `model.*` : pour gérer la logique de l'application,
    ici le traitement de langue naturelle.

-   `App` : le point d'entrée de l'application, qui gère la création de
    l'application.

Le squelette de code fait une première séparation entre l'interface
graphique (package `view`) et la logique métier (package `model`).
Nous verrons plus tard que cette séparation est encore plus
qu'imparfaite et vous demanderons de refactorer le code.

### Documentation de Java et JavaFX

Consultez la documentation de [Java
11](https://docs.oracle.com/en/java/javase/11/docs/api/index.html) et de
[JavaFX Graphics](https://openjfx.io/javadoc/11/)
(la bibliothèque graphique utilisée).

### Chargement du projet dans un IDE (VS Code, Eclipse, IntelliJ, Netbeans ..)

Si vous souhaitez utiliser un IDE, votre IDE favori propose
probablement une prise en charge de Maven, et configurera donc le
projet automatiquement depuis le `pom.xml` :

- VS Code : installer le plugin [Java Extension Pack](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack) qui apporte le support du langage Java, de Maven, ... Faire menu File → Open Folder (<kbd>Controle</kbd>+<kbd>k</kbd> <kbd>Controle</kbd>+<kbd>o</kbd>) puis choisir le répertoire. Une section « Maven Projects » doit s'ajouter à la barre latérale de gauche, et vous pourrez sélectionner les actions à effectuer (`exec:java` dans la section `exec` pour lancer l'application). Si vous n'avez pas la complétion intelligente et la navigation dans le code, vous devrez sans doute positionner votre variable de configuration `java.home` : faire avec <kbd>Control</kbd> + <kbd>,</kbd>, puis chercher `java.home` et « edit in settings.json ». Au Nautibus, la configuration est :
<pre>
"java.home" : "/home/tpetu/m1if01/jdk-11.0.4/"
</pre>
<!-- TODO ça c'est la version installé à la main -->

- Eclipse : installer le plugin [m2e](http://www.eclipse.org/m2e/), puis importer le projet en
  temps que projet Maven (File → Import... → Maven → Existing Maven Projects). Au Nautibus, Eclipse
  est installé dans `/home/tpetu/m1if01/bin/eclipse` avec m2e installé. Pour lancer l'application,
  on peut au choix utiliser « Run as ... » → « Java application » (mais cf. ci-dessous pour une
  erreur fréquente), ou « Run as ... » → « Maven Build... » puis choisir le goal « exec:java ».

- IntelliJ et Netbeans : le support de Maven est inclus de base dans l'outil. Il
  suffit d'ouvrir le répertoire contenant le `pom.xml`.

Il n'est pas rare qu'il y ait de mauvaises interactions entre un IDE et JavaFX, nous avons essayé de documenter les problèmes et solutions dans [FAQ.md](../FAQ.md).

## Travail demandé

Dans un premier temps, conservez l'architecture (répartition en
classes et packages) fournie. Nous verrons bientôt comment réorganiser
le tout.

### Un autre type de réponse prédéfinie (Ici, c'est moi qui pose les questions)

Contrairement aux modèles de langages modernes, notre programme n'est pas
entraîné sur une base de textes, mais toutes les réponses possibles sont codées
en dur dans l'outil. Par exemple, quand vous commencez une phrase par « Je pense
que ... » l'outil peut vous répondre « Pourquoi pensez-vous que ... ».

Ajoutez une règle qui détecte les questions (c'est-à-dire les phrases terminant
par un point d'interrogation), et faites en sorte que le programme réponde
aléatoirement « Je vous renvoie la question. » ou « Ici, c'est moi qui pose les
questions. ».

Si vous ne trouvez pas les endroits où faire vos modifications dans la base de
code, une astuce : cherchez les chaînes de caractères qui vous intéressent, par
exemple `git grep -n "Pourquoi pensez-vous que"` vous indiquera comment est
faite la réponse automatique aux phrases commençant par `Je`.

Le code existant utilise les expressions régulières pour reconnaître des motifs
dans une phrase. Si vous n'êtes pas familier avec les expressions régulières de
Java, vous pouvez jeter un œil à [ce
tutorial](https://www.w3schools.com/java/java_regex.asp).

### Ajout de règles de conjugaisons de verbes du 3ième groupe (vouloir, pouvoir)

Pour transformer les phrases comme « Je pense que ... » en « Qu'est-ce qui vous
fait dire que vous pensez ... », notre programme doit savoir conjuguer un verbe,
pour passer de « pense » à « pensez ». Pour les verbes du 3ième groupe, il n'y a
malheureusement pas de règle générale, donc notre programme connaît un certain
nombre de verbes (par exemple « suis » -> « êtes », « vais » -> « allez »).
Ajoutez les correspondances « peux » -> « pouvez » et « dois » -> « devez ».

On peut remarquer au passage que :

* Ajouter un verbe ne prend qu'une ligne de code, ce qui est une bonne chose.

* La logique permettant de passer de la 1ère personne à la 2ème est séparée du
  code de l'interface graphique, ce qui est également une bonne chose.

* La liste des verbes est codée en dur dans le code Java. Impossible d'ajouter
  des verbes sans modifier le code (par exemple, on aurait pu souhaiter qu'un
  plugin puisse ajouter des verbes). On dit que ce code n'est pas extensible.

### Suppression d'un message d'ELIZA

Cliquer sur un de vos messages permet de le supprimer, mais l'auteur a
« oublié » de faire la même chose pour les messages d'ELIZA. Faites en sorte
qu'un clic sur un message d'ELIZA le supprime également. Profitez-en pour pester
contre l'auteur de ce squelette qui n'a pas correctement factorisé le code : il
y a des fonctions séparées très similaires pour poster les messages d'ELIZA et
les vôtres. Ce code viole le principe simple « DRY » (Don't Repeat Yourself).
Notez dans un coin de votre tête qu'il faudra résoudre tout cela dans la version
finale bien sûr.

### Stratégie de recherche

Le champ recherche utilise pour l'instant une simple recherche de sous-chaîne.
Proposez une autre stratégie de recherche où l'utilisateur puisse faire une
recherche par expression régulière (par exemple entrer « Qu'est.*dire » dans le
champ de recherche trouvera le message « Qu'est-ce qui vous fait dire cela ? »).

### Problèmes avec la base de code fournie

Réfléchissez maintenant à l'architecture du code. Posez-vous les
questions (rhétoriques ?) suivantes :

- Est-ce facile de modifier l'interface graphique sans changer la
  fonctionnalité ?
  
- À l'inverse, des fonctionnalités comme les réponses pré-définies devraient
  être indépendantes, de l'interface graphique utilisée. Quand vous avez ajouté
  une réponse « Ici, c'est moi qui pose les questions », l'avez-vous fait sans
  modifier la vue (le package `view`) ?
  
- Si vous deviez avoir plusieurs interfaces possibles (par exemple un
  mode « expert » et un mode « débutant » avec moins de boutons, ou
  une interface web et une interface graphique JavaFX), pourriez-vous
  le faire facilement sans dupliquer le code de la logique métier ?
  
- Quelles sont les responsabilités de la classe `JfxView` ? Est-ce
  compatible avec le [Principe de Responsabilité
  Unique](https://en.wikipedia.org/wiki/Single_responsibility_principle) ?

Quelques éléments de réponses sont disponibles dans le fichier
[architecture-et-dependances.md](architecture-et-dependances.md). Ne
les lisez pas avant d'y avoir réfléchi vous-mêmes.

## Si vous avez fini ...

Passez au [TP2](../TP2-outils) !
