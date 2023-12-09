<!-- LTEX: language=fr -->
Ce fichier contiendra les nouvelles du cours. Un mail sera envoyé quand le fichier est mis à jour.

# 19/09/2023 : petit bug du squelette

Dans le squelette fourni, on utilise `USER_STYLE` comme style pour les messages
d'ELIZA, et `ELIZA_STYLE` pour les messages de l'utilisateur. Il aurait bien
entendu fallu faire l'inverse.

Pour ne pas risquer de vous embêter avec des conflits, un correctif est proposé,
mais seulement dans la branche `low-priority` du dépôt enseignants, que vous
pouvez récupérer avec :

```
git pull --no-rebase moy low-priority
```

# 18/09/2023 : mise à jour du squelette, logistique cette semaine

Il y avait une petite erreur dans votre squelette, le fichier setup-mvn-proxy.sh
aurait dû être exécutable, mais il ne l'était pas. C'est corrigé dans le
squelette enseignant, donc vous pouvez récupérer la mise en suivant les
instructions disponibles ici :

  https://forge.univ-lyon1.fr/matthieu.moy/mif01/-/blob/main/TP2-outils/README.md#travail-avec-plusieurs-d%C3%A9p%C3%B4ts-distants

En résumé :

```
git remote add moy https://forge.univ-lyon1.fr/matthieu.moy/mif01.git
git pull --no-rebase moy main
```

Nous nous retrouvons mercredi pour la deuxième journée de cours. Quelques
informations :

* L'eau du Nautibus a été re-testée et est maintenant officiellement potable.

* Pour les TD, j'ai fait une pré-affectation de salle. Vous pouvez voir la salle
  dans laquelle vous êtes affecté dans la case [Salle_TD1_20_septembre] sur
  TOMUSS.

# 11/09/2023 : début du cours cette semaine

Le cours MIF01, Gestion de Projet et Génie Logiciel, démarre cette semaine
(mercredi). La page web du cours est ici :

  https://forge.univ-lyon1.fr/matthieu.moy/mif01

Nous commençons par un CM à 8h amphi Thémis 7, puis nous enchaînerons avec un TP
bâtiment Nautibus (salles sur la page du cours, répartissez-vous comme vous le
souhaitez dans les salles).

Le bâtiment Nautibus est toujours en travaux, et j'avais demandé des salles dans
un autre bâtiment, mais la scolarité a ré-affecté mes réservations au Nautibus.
On me demande d'être vigilant pour la sécurité des étudiants, et de bien les en
informer. Attention ! les sols ne sont pas finalisés et restent un peu fragiles.
Nous vous demandons d’être attentifs lors de vos passages pour ne pas dégrader
la couche de ragréage (en évitant les rollers, les skates, les vélos et les
chaussures à talons ou à ferrures).

Vous aurez un mini-projet à réaliser en binômes au cours du semestre. Vous
pouvez dès maintenant discuter avec vos collègues pour commencer à former vos
équipes. Si vous n'avez pas trouvé de co-équipier avant le premier TP, je vous
invite chercher un binôme directement dans les salles et/ou à vous signaler à
votre enseignant.
