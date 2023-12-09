<!-- LTeX: language=fr -->
# Rendu du Mini Projet "ELIZA GPT"

**Votre travail devra être rendu sous forme d’un projet déposé sur la Forge Lyon 1, les dates limites sont dans le fichier README.md de ce projet.**

Le mini-projet noté est le fil rouge de tous les TPs. Vous commencez à
travailler sur la base de code au [TP1](TP1-java/), et vous ajoutez
des fonctionnalités tout en améliorant la qualité du code dans la
suite.

Les consignes ci-dessous sont à respecter **impérativement** pour le rendu.

## Fraude

On vous demande un travail original et non une copie. Vous n'êtes pas autorisés
à fournir votre code à d'autres équipes ni à utiliser le code d'autres équipes
(y compris le code écrit les années passées, y compris par vous-même si vous
êtes redoublant). Si vous êtes en difficulté, demandez de l'aide à vos
enseignants, mais n'utilisez pas vos difficultés comme excuse pour frauder.

## Rapport

Votre rendu inclura un rapport, au format PDF (consignes pour le rendu
ci-dessous), qui doit comprendre obligatoirement :

- une présentation globale du projet (rapide : ne répétez pas
  l'énoncé),

- Une section « design patterns », donnant une motivation des choix
  d’architecture (et des patterns choisis), et leur explication en s’aidant de
  diagrammes appropriés et adaptés au degré de précision et au type
  d’explication. Donc des diagrammes de classe, mais pas que cela, et pas de
  plats de spaghettis générés automatiquement représentant tout le code.
  
* Une section « éthique ». Cette section devra discuter de la problématique des
  IA conversationnelles comme ChatGPT, Bard, etc. Quels sont les
  enjeux ? Quels sont les risques et les bénéfices, pour la société qui édite un
  tel programme, et pour ses utilisateurs ? Quelles sont les mesures, légales et
  techniques, pour limiter ou éliminer les risques ? Lesquels sont mis en œuvre
  dans la réalité ? En avez-vous mis en place dans votre TP, si oui, lesquelles
  (il s'agit d'un petit projet scolaire, on ne vous demande pas une application
  vraiment sécurisée, mais vous devriez être capable de discuter des limites de
  votre implémentation. Vous pouvez aussi mettre en place des mesures simplistes
  et discuter de ce qu'il faudrait faire dans une vraie application) ?

  L'objectif n'est pas de donner un avis subjectif (la question «
  ChatGPT/Bard/... est-il bien ? » est hors sujet ici), mais de présenter les
  questions importantes et les éléments objectifs de réponse autour de la
  question des IA conversationnelles. Appuyez-vous autant que possible sur des
  articles existants, en citant vos sources. Il s'agit donc avant tout d'un
  travail de bibliographie de votre part.

  Pour vous aider, voici quelques références intéressantes sur le sujet :

  * [ChatGPT](https://fr.wikipedia.org/wiki/ChatGPT) et [Bard](https://fr.wikipedia.org/wiki/Bard_(chatbot)) sur Wikipedia

  * [Sept choses à savoir sur la suspension de ChatGPT en Italie](https://www.liberation.fr/economie/economie-numerique/sept-choses-a-savoir-sur-la-suspension-de-chatgpt-en-italie-20230331_L6YEOWWJU5HWDL3GF5P7QM5LFE/)

  * [Un ingénieur de Google mis à pied après avoir affirmé que l’intelligence artificielle était "sensible"](https://www.radiofrance.fr/franceinter/un-ingenieur-de-google-mis-a-pied-apres-avoir-affirme-que-l-intelligence-artificielle-etait-sensible-5250635)

  * [Un Belge se suicide après avoir trouvé refuge auprès d'un robot conversationnel](https://www.lefigaro.fr/actualite-france/chatgpt-un-belge-se-suicide-apres-avoir-trouve-refuge-aupres-d-un-robot-conversationnel-20230329)

  * [Prompt engineer : quel est ce nouveau métier qui rapporte jusqu’à 300 000 € ?](https://www.presse-citron.net/prompt-engineer-quel-est-ce-nouveau-metier-qui-rapporte-jusqua-300-000-e/)

  * [Elon Musk et des centaines d’experts réclament une « pause » dans le développement de l’intelligence artificielle](https://www.lemonde.fr/economie/article/2023/03/29/elon-musk-et-des-centaines-d-experts-reclament-une-pause-dans-le-developpement-de-l-ia_6167461_3234.html)

  * [ChatGPT est-il devenu plus “éthique” grâce à l’exploitation de travailleurs kényans ?](https://www.journaldugeek.com/2023/01/19/chatgpt-est-il-devenu-plus-ethique-grace-a-lexploitation-de-travailleurs-kenyans/)

  La liste n'est bien entendu pas exhaustive. Pensez à vos enseignants qui
  liront des dizaines de rapports, surprenez-nous, apprenez-nous des choses ! Si
  votre relecteur se dit « Ah tiens, je ne savais pas » ou « Ah tiens, je n'y
  avais pas pensé » en lisant votre rapport, vous avez atteint l'objectif !

  Vous pouvez utiliser une IA conversationnelle pour écrire cette section, mais
  si vous le faites vous devez le dire explicitement dans votre rapport et
  donner les requêtes (prompt) que vous avez utilisé pour arriver au texte
  final.
  
* Une section « tests » où vous décrirez les tests manuels que vous avez
  réalisés. Vos tests automatiques (le code Java des tests et les commentaires
  associés) devraient se suffire à eux-mêmes, il n'est pas nécessaire de les
  re-documenter dans le rapport (sauf si vous avez fait des choses
  extraordinaires qui méritent une documentation externe).

On vous demande d'appuyer votre rapport sur des références bibliographiques existantes, mais il ne s'agit en aucun cas de vous contenter de copier-coller. Il est rappelé ici que le [droit de courte citation](https://fr.wikipedia.org/wiki/Droit_de_courte_citation) impose entre autres de citer vos sources quand vous utilisez un extrait d'un texte existant (il est interdit, pas seulement dans le cadre d'un travail scolaire, de laisser entendre que vous êtes l'auteur d'un texte que vous n'avez pas écrit vous-même).

## Qualité du code

### Style

Assurez-vous que votre programme respecte toujours le style imposé
(`mvn test`, qui doit lancer checkstyle).

Bien sûr, respecter le style doit se faire en corrigeant (si besoin)
votre code, mais *pas* en modifiant le fichier de configuration de
checkstyle.

### Design-pattern

Assurez-vous d'avoir appliqué toutes les consignes du
[TP 3](TP3-patterns/).

### Tests et intégration continue

Vérifiez que l'intégration continue mise en place au
[TP 2](TP2-outils/) fonctionne toujours.

Les tests automatisés tels que décrits au [TP 4](TP4-tests/) doivent
être lancés automatiquement par `mvn test`, et doivent tous passer
avec succès.

### Portabilité

Clonez, compilez et exécutez votre code **sur une machine vierge**
(c'est-à-dire sur laquelle vous n'avez installé aucune dépendance, ni
configuré le compte utilisateur de façon particulière). Une grande
partie du barème est liée à l'exécution de votre travail. Il est
important que nous arrivions à l'exécuter **directement**. "Ça marche
chez moi" n'est pas une excuse et une démo *a posteriori* ne permet
pas de remonter une note de TP.

## Projet Forge et TOMUSS

Les projets seront rendus en binômes. La date limite est indiquée sur
la page d'accueil du cours.

**Ajoutez les utilisateurs @matthieu.moy, @LIONEL.MEDINI, avec le niveau de privilège
"reporter" (ou plus, mais *pas* "guest") à votre projet sur la forge**

Dans la feuille TOMUSS M1 Informatique, indiquez l'URL de votre projet dans la case URL_Projet_MIF01 (l'URL doit ressembler à
`https://forge.univ-lyon1.fr/<login>/mif01`). Il faut impérativement
**que la commande `git clone <url>` fonctionne, et que cette commande récupère la dernière version de votre projet.**
Autrement dit la branche par défaut doit contenir la dernière version du projet si vous avez
plusieurs branches. Tous les membres du binôme doivent entrer **exactement** la même URL (au caractère près).

Pensez à remplir dès à présent TOMUSS indiquant votre URL.
Le dépôt ne sera relevé qu’après la date de rendu.

Votre dépôt sur la Forge devra contenir :

- un répertoire `eliza-gpt/` (le répertoire doit impérativement avoir exactement ce nom)
- un fichier maven (`eliza-gpt/pom.xml`) pour le build du projet
- les sources (fichiers Java)
- le rapport en PDF (6 pages maximum, format libre. La limitation de pages est indicative, si vous avez vraiment besoin de plus vous pouvez dépasser un peu, mais restez raisonnables et concis), dans un fichier qui doit impérativement s'appeler `rapport.pdf` à la racine du dépôt Git.

Vous pouvez laisser les autres fichiers et répertoires.

## Barème indicatif (le barème sera ramené à 20), à utiliser comme checklist pour vérifier que vous avez tout fait

| Points | Critère |
|--------|---------|
| malus -3 si absent | Présence des bons fichiers (rapport.pdf, eliza-gpt/pom.xml, .gitlab-ci.yml) |
| 1 | Le projet est compilable (mvn compile) |
| 1 | Au moins une issue dans GitLab |
| 1 | Au moins une merge-request fermée |
| 1 | L'intégration continue est OK sur Gitlab |
| 1 | Les tests passent (mvn test -Dcheckstyle.skip) |
| 2 | Aucun warning checkstyle |
| 1 | Fichier .gitignore (pas de "untracked files" après "mvn test") |
| Malus si retard | Rendu avant la deadline |
| Malus jusqu'à -5 | Malus éventuel pour non-respect des consignes (en plus de la note automatique) |
| 1 | Le programme se lance correctement (mvn exec:java) |
| 1 | Qualité et structure globale du code, utilisation de Packages |
| 2 | Interface (UI) propre y compris pour les extensions (pas de point si l'UI du squelette est rendue, note max si l'interface est belle et ergonomique) |
| 1 | Extension : Un autre type de réponse prédéfinie (Ici, c'est moi qui pose les questions) |
| 1 | Extension : Ajout de règles de conjugaisons de verbes du 3ième groupe (vouloir, pouvoir) |
| 1 | Extension : Suppression d'un message d'ELIZA |
| 2 | Extension : Ajout de deux autres réponses pré-définie (Au revoir + au choix) |
| 1 | Extension : Stratégies de recherche (sous-chaîne, expression régulière) |
| 1 | Extension : Une troisième stratégie de recherche (mot complet) |
| 1 | Extension : Règles de conjugaison |
| 1 | Extension : Modification de l'interface pour les messages (bouton pour supprimer) |
| 3 | Autres extensions |
| 3 | Tests automatiques |
| 1 | Principes GRASP bien appliqués |
| 1 | Design pattern MVC : la deuxième vue fonctionne, les listes se mettent à jour automatiquement (plus de bouton « refresh ») |
| 3 | Design pattern MVC : qualité et organisation du code |
| 5 | Design-patterns (création, structure, SOLID, ...) : au moins 3 autres patterns que MVC |
| 6 | Rapport : partie « design patterns » |
| 4 | Rapport : partie « éthique » |
| 1 | Rapport : partie « tests » |
| 3 | Rapport : qualité globale des explications |
| Malus jusqu'à 5 | Rapport : malus éventuel pour mauvaise forme et orthographe (0 si la forme est OK, pas de note positive) |
| Bonus jusqu'à +3 | Bonus éventuel pour choses en plus (pas de note négative) |
