Bonjour et bienvenue sur le code de Blokus, 

pour pouvoir le lancer sur une machine de l'école veuillez ouvrir putty
 ensuite suivez les lignes de commande suivante : 
 
 -git clone https://git.esi-bru.be/ATLG3/atlg3-c111-49262.git
 -entrer les identifiants et mot de passe de gitLab
 -javac -cp esi.atlg3.49262.blokus/src blokus.viewFx.BlokusMain 
 -java Blokus.viewFx.BlokusMain
 
 Mon code est divisé en plusieurs package afin de gérer la vue javaFx , la vue console
 et l'algorithme de mon code. dans blokus.model il y aura donc toutes les classes 
 qui gèrent l'algorithme de mon jeux. celle ci sera reliés à ma vue javaFx grace 
 aux Observer Observable qui est un design pattern utilisé afin de pouvoir notifier 
 la vue si changement il y a eu. J'ai fais 2 façades , Model qui me permet de 
 gérer le jeu et une Move qui permet de savoir si on est dans le premier placement de pièces
 ou bien un mouvement de pièce classique. J'ai également ajouter des bots qui prendront 
 une pièce aléatoirement et la placeront si ils peuvent sinon ils reprendront une autre pièce 
 et essayeront de la placer. J'espère que ce code sera assez lisible pour vous.