<!-- LTeX: language=fr -->
# TP3 - Design patterns

## Objectifs

Il vous est demandé d’effectuer une ré-ingénierie d’un code existant en mettant en œuvre les patrons de conception vus en cours.

Ceci devrait vous prendre 2 ou 3 séances.

### Déroulement

Ce TP est organisé en deux étapes :

- une ré-ingénierie (refactoring) du code utilisé dans les premiers TP afin de mieux structurer le projet et de le rendre plus modulaire,
- l’extension des fonctionnalités pour réaliser un programme plus complet.

Le plus simple est de partir répertoire `eliza-gpt/` fourni dans votre archive. Si vous le souhaitez (mais ce n'est pas la solution recommandée, à vos risques et périls), vous pouvez aussi repartir d'un répertoire vierge, mais attention à ne rien oublier au passage, et si vous faites cela, vous n'aurez plus la possibilité de récupérer les mises à jour du squelette avec un simple `git pull moy master` !

### Ressources

- [Cours (et pointeurs à la fin du cours) sur les Design patterns](https://perso.liris.cnrs.fr/lionel.medini/enseignement/M1IF01/CM-patterns.pdf)
- [Aide à la mise en place du pattern MVC pour ce TP](./mvc.md)

## Consignes

Il est demandé de travailler en binômes.

Vous devriez avoir déjà rempli le champ URL sur TOMUSS pour indiquer votre dépôt forge et votre binôme,
*Cf*. instructions dans [../projet-note.md](../projet-note.md). Si ce n'est pas encore fait, faites-le maintenant.

### Outils

Il est conseillé d'utiliser un IDE capable de générer des diagrammes UML à partir du code, ou mieux, de faire du __round-trip engineering__ (prendre en compte les modifications du code dans les schémas et inversement). C'est le cas avec IntelliJ, et Eclipse avec un plugin comme [Object Aid](https://www.objectaid.com/) (explications rapides en vidéo [ici](https://www.youtube.com/watch?v=0Zlh56mTS6c)). VS Code n'est pas très bon pour cela, donc si vous utilisez VS Code, le mieux est de passer par un outil externe si vous voulez générer des diagrammes UML.

Il est aussi possible de [générer un diagramme de classe via JavaDoc et PlantUML](https://github.com/talsma-ict/umldoclet), le fichier `pom.xml` fourni tente de faire cela (`mvn javadoc:javadoc` ou `mvn site`). Attention, on trouve beaucoup de documentation sur le web sur un doclet UMLGraphDoc, mais celui-ci est cassé depuis Java 8.

### Qualité du code

Le but de ce cours en général est de vous apprendre à écrire du code propre (irréprochable ?). 
On accordera une attention particulière à la qualité du code à tous les niveaux (style, indentation, architecture...). 
Privilégiez la qualité du code à la quantité de fonctionnalités.

## Partie 1 : Ré-ingénierie

Le code fourni lors de la première séance est ensemble de classes
relativement fouillis (nous en avons déjà parlé dans
[architecture-et-dependances.md](../TP1-java/architecture-et-dependances.md)).
En particulier les couches graphique (la vue) et métier (le modèle) ne sont pas séparées.
Il va vous falloir réorganiser le code en appliquant les patrons de conception adéquats.

### Pattern Modèle-Vue-Contrôleur

Vous allez maintenant redéfinir la structure de base de l’application.
Mettez en place un pattern MVC pour :

- séparer le métier (la logique de l'application) et l’affichage
  des éléments (boutons et champs textes),

- propager les changements du modèle métier (statut des utilisateurs, ...) dans la vue,

- répercuter les entrées utilisateur (clic sur un bouton, validation
  d'un champ texte avec la touche « entrée »...) sous la forme
  adéquate dans le modèle métier.

Pour vous aider, vous pouvez utiliser les slides du cours sur les patterns contrôleur et MVC, ainsi que le document [Mettre en place le pattern MVC](mvc.md). Ce document propose des questions pertinentes à se poser, ainsi que quelques éléments de réponses, mais ce ne sont ni les seules questions ni les seules réponses pertinentes.

**Pour chacun des patterns implémentés à partir de cette partie, vous décrirez (en 1 ou 2 paragraphes) pourquoi vous avez choisi de l'utiliser, et vous fournirez dans votre rapport un/des diagramme(s) UML illustrant comment vous l'avez mis en œuvre.**

#### Flexibilité du modèle MVC

Une des propriétés du MVC est qu'on peut avoir un nombre quelconque de vues, identiques ou non, d'un modèle donné.
On peut vérifier simplement que notre MVC vérifie cette propriété en ajoutant quelque chose comme ceci dans le programme principal :

```java
    public void start(final Stage stage) throws Exception {
        // First view, provided in skeleton
        new JfxView(stage, 600, 600);

        // Second view
        new JfxView(new Stage(), 400, 400);
    }
```

Vous devriez voir une deuxième fenêtre, de taille différente, qui affiche
l'interface graphique. Les deux fenêtres doivent être synchronisées : toutes les
actions importantes faites dans l'une est répercutée immédiatement dans l'autre
(bien sûr, il y a aussi des choses qui restent locales à la vue, par exemple
quand des caractères dans un champ texte, le texte entré n'est propagé
vers le modèle et les autres vues qu'au moment où on valide avec le bouton « correspondant »).

Dans le squelette fourni, les deux vues ne sont pas synchronisées : entrer un
message dans une fenêtre n'affiche pas le message dans l'autre, et par exemple
entrer « Je m'appelle Matthieu » dans une fenêtre ne permet pas à ELIZA de
répondre quand on lui demande « Quel est mon nom ? » dans l'autre fenêtre. Si
vous avez fait correctement le passage en MVC, tout ceci doit être corrigé.

Remarque : si la deuxième fenêtre vous gêne, mettez en commentaire le
morceau de code concerné, mais conservez-le obligatoirement et vérifiez qu'il marche
toujours au moment du rendu.

### Autres patterns

Reprenez les transparents du cours et parcourez la liste des patterns GRASP, des patterns de création, de structure, des principes SOLID, et posez-vous la question de leur applicabilité sur votre projet.
Appliquez ceux qui vous semblent pertinents, et de la même façon que précédemment, documentez cela dans votre rapport (si possible, en suivant la progression du cours : GRASP, création, structure...).

Vous devrez avoir au moins 3 patterns autres que MVC appliqués dans votre projet et décrits dans votre rapport. On considère que le pattern « Observer » fait partie de MVC en ne compte pas dans les 3.

### Anticiper sur le TP 4 "test"

Avant d'aller plus loin sur les extensions, c'est une bonne idée de progresser en tests : nous serons plus en sécurité pour continuer le refactoring et les extensions avec une bonne base de tests. 
Allez jeter un œil au [TP "Test"](../TP4-tests/README.md) (en particulier la section sur le TDD), et revenez pour la suite de ce TP après.
À vous de voir dans quel ordre vous voulez avancer précisément.

## Partie 2 : Extension

Dans toute cette partie, l'ajout de fonctionnalité est un prétexte pour se servir de design patterns. Dans la vraie vie, bien sûr, c'est au client ou à l'expert métier de décider des fonctionnalités pertinentes, mais exceptionnellement dans ce projet un peu artificiel on peut s'autoriser à choisir une fonctionnalité parce qu'elle sera intéressante à implémenter.
Ajoutez chaque fonctionnalité en appliquant les principes et patterns vus en cours, et justifiez-la dans le rapport.

### Rappel des extensions obligatoires du TP1

Pendant le TP1, vous avez du faire ces modifications :

- Un autre type de réponse prédéfinie (Ici, c'est moi qui pose les questions)

- Ajout de règles de conjugaisons de verbes du 3ième groupe (vouloir, pouvoir)

- Suppression d'un message d'ELIZA

Assurez-vous que ces modifications fonctionnent toujours et qu'elles sont codées proprement en suivant le principe MVC.

### Ajout de deux autres réponses pré-définie (Au revoir + au choix)

Pour vérifier que votre base de code est bien évolutive : ajoutez une autre réponse pré-définie pour qu'ELIZA réponde aléatoirement « Oh non, c'est trop triste de se quitter ! » ou « Au revoir » (plus le nom de l'utilisateur s'il est connu) quand l'utilisateur entre le message « Au revoir ».

Ajoutez au moins une autre réponse pré-définie de votre choix.

Ces ajouts ne doivent modifier que le modèle, et autant que possible doivent se
faire sans ajouter de code aux classes existantes (pas d'ajout de `else if` ou
de `case` dans un `if/else if` ou un `switch` existant !). Vous pouvez même vous
amuser à [métaprogrammer](https://github.com/ronmamo/reflections) cela pour
qu'on puisse simplement ajouter des classes dans un paquet pour ajouter des
règles (sans aucune autre modification de la base de code, le mieux qu'on puisse
faire en termes de couplage faible).

### Stratégies de recherche (sous-chaîne, expression régulière)

Au départ, le champ « recherche » ne permet que de faire une recherche via une sous-chaîne.

Modifiez cela pour permettre une recherche avancée. L'utilisateur doit pouvoir
choisir entre plusieurs stratégies de recherche, par exemple comme ceci :

![Recherche avancée](img/eliza-search.png)

Vous devez coder au moins ces stratégies :

- Sous-chaîne, qui sélectionne tous les messages contenant la chaîne, à n'importe quel endroit.

- Expression régulière, qui sélectionne tous les messages contenant une sous-chaîne qui correspond à l'expression régulière. Attention, le comportement de Java par défaut est de faire une correspondance sur l'ensemble de la chaîne, vous devrez donc ajouter `".*"` au début et à la fin du motif entré par l'utilisateur.

Attention bien sûr à maintenir votre code factorisé au maximum : pas
de duplication de code d'une stratégie à l'autre !

Si vous êtes tentés d'écrire du code de la forme

```java
if (strategie == 1) { // NON
    ...
} else {
    ...
}
```

Relisez votre cours sur les design-patterns, il y a plus propre et extensible
... (et non, un `switch` à la place du `if / else if / else` n'est pas une
solution satisfaisante : ajouter une stratégie demanderait de modifier le code
existant).

Il est vivement conseillé d'utiliser la classe `ComboBox` de JavaFX (voir par
exemple [ce tutorial](https://melo-code.com/javafx-combobox/)). Cette classe
permet de créer un menu déroulant à partir d'une liste de `String`, ce qui ne
serait pas une très bonne idée (cela nous obligerait ensuite à analyser les
chaînes de caractères pour choisir la stratégie, donc une modification de
l'interface comme une traduction des chaînes de caractères dans une
autre langue casserait tout). Il est beaucoup plus judicieux de créer le menu à
partir d'une liste de classes, chacune implémentant la méthode
`toString()` correctement. Autrement dit, c'est une mauvaise idée d'utiliser
`ObservableList<String>` ici. Et d'une manière générale, les chaînes de
caractères de l'interface utilisateur ne doivent jamais être utilisées dans la
logique métier.

### Une troisième stratégie de recherche (mot complet)

Ajoutez maintenant une stratégie de recherche « Mot complet », qui ne sélectionne que les mots complets (par exemple le message « Bonjour » ne sera pas sélectionné quand on entre la recherche « jour », mais le message « Quel jour sommes nous » le sera). La [séquence `\b`](https://stackoverflow.com/questions/1324676/what-is-a-word-boundary-in-regex) des expressions régulières Java devrait vous aider. Pour vous simplifier la vie, le motif entré par l'utilisateur sera considéré comme une expression régulière.

Là encore, si vous avez bien travaillé à l'exercice précédent, cela doit se
faire en ajoutant une classe (triviale) et en l'ajoutant à une liste de
stratégies disponibles, mais le code existant ne doit pas être modifié.

### Règles de conjugaison

Jusqu'ici, nous avons pour simplifier utilisé une liste de verbes (du 3eme
groupe) fixe et codée en dur dans le code. C'est acceptable pour un premier
prototype, mais la liste réelle est bien plus longue, on ne veut pas la mélanger
avec le reste de la logique du code. La liste devrait être au minimum dans une
classe séparée, et pourquoi pas dans un fichier de configuration externe (par
exemple
[celui-ci](https://github.com/ianmackinnon/inflect/blob/master/french-verb-conjugation.csv)).
Idéalement on peut vouloir en ajouter dynamiquement sans modifier le code pour
permettre de gérer des barbarismes qui ne sont pas dans le dictionnaire. On peut
même aller plus loin et avoir un éditeur graphique avec une configuration
persistante d'une exécution à l'autre avec un
[DAO](https://en.wikipedia.org/wiki/Data_access_object).

### Modification de l'interface pour les messages (bouton pour supprimer)

La convention utilisée dans le squelette est qu'un clic sur un message supprime
le message. Ce n'est pas du tout intuitif, nous allons modifier cela pour que l'interface ressemble à :

![Bouton supprimer](img/delete-button.png)

Cliquer sur le bouton doit supprimer le message. Cliquer sur le message ne doit
pas le supprimer. Une manière de faire cela est d'utiliser deux `HBox` l'une
dans l'autre : la `HBox` englobante aura la propriété
`setAlignment(Pos.BASELINE_LEFT);` (ou `RIGHT`) pour la position du message. La
boîte interne sera modifiée avec `setStyle(USER_STYLE)` (ou `ELIZA_STYLE`) et
contiendra le texte dans un `Label` ainsi que le `Button`. Pour faire simple, le
bouton est simplement créé avec `new Button("x")` (on aurait pu utiliser une
icône plus propre).

Cette modification change la structure des éléments JavaFX et casserait
certainement le code du squelette (qui s'attend à trouver un `Label` sur lequel
appeler `getText` directement sous le premier `HBox`). Si vous avez codé votre
MVC correctement, le changement doit maintenant se faire sans douleur.

### Autre chose ?

Vous n'en avez pas assez ? Vous voulez ajouter d'autres fonctionnalités ? Un
modèle de langage basé sur un réseau de neurones ? Un backend qui interroge
directement un chatbot via une API web ? À vous de jouer, amusez-vous bien.

## Rendu du TP / projet

Voir les consignes **À RESPECTER IMPÉRATIVEMENT** dans
[../projet-note.md](../projet-note.md).
