# 📅 Roadmap de Développement - FarmMyFarm

## 🟢 Phase 1 : Architecture & Fondations (Haute Priorité)

_C'est le "moteur" sous le capot. Sans cela, ajouter du contenu sera un enfer de bugs._

- [ ] **Système de Sauvegarde (Sérialisation)** : Indispensable pour ne pas perdre sa progression à chaque test. Utiliser `Serializable` ou un format `JSON`.
- [ ] **Panel de Debug complet** : Créer une interface pour manipuler l'argent, le temps, et forcer les récoltes (essentiel pour tester les phases suivantes).
- [ ] **Gestionnaire de Temps Global** : Centraliser l'écoulement des minutes/heures pour que les saisons et la météo soient synchronisées.
- [ ] **Système d'Inventaire** : Créer une classe pour stocker les récoltes (différent du portefeuille) pour permettre les contrats et les crafts.

## 🟡 Phase 2 : Économie & Progression (Moyenne Priorité)

_Ce qui rend le jeu addictif et donne un but au joueur._

- [ ] **Plusieurs cultures (Data-driven)** : Externaliser les données des graines (prix, temps, saisons) pour en ajouter facilement sans toucher au code.
- [ ] **Système de Niveau & XP** : Formule de calcul d'expérience par récolte et déblocage de nouvelles graines (Max LVL 20).
- [ ] **Contrats PNJ** : Système de quêtes temporaires avec bonus financier (utilise l'Inventaire).
- [ ] **Économie variable** : Algorithme modifiant les prix de vente selon l'offre, la demande et la saison actuelle.

## 🟠 Phase 3 : Mécaniques Avancées & Environnement

_L'univers du jeu devient vivant._

- [ ] **Saisons & Météo** : Impact visuel et technique (ex: la pluie arrose automatiquement, l'hiver tue certaines plantes).
- [ ] **Taille de grille évolutive** : Agrandir la ferme (ex: 4x4 -> 6x6) lors des passages de paliers de niveau.
- [ ] **Outils & Automatisation** : Arrosage auto, outils de zone (faux améliorée) et enclos pour animaux.
- [ ] **Nuisibles** : Implémenter les événements aléatoires (taupes, corbeaux) qui ralentissent la production.

## 🔵 Phase 4 : Contenu "End-game" & Polissage

_Le contenu pour les joueurs avancés et le visuel final._

- [ ] **Système de Crafting** : Transformer les matières premières (blé + lait -> gâteau) pour maximiser les profits.
- [ ] **Système de Mondes (Onglets)** : Navigation entre différentes zones (Ferme, Garage/Voitures) une fois le niveau 20 atteint.
- [ ] **Dashboard de Statistiques** : Historique des ventes, temps de jeu, et graphiques de progression.
- [ ] **Sprites Sheets & Design Final** : Remplacer les boutons par des images et appliquer le CSS final.
- [ ] **Golden Farm** : Mode bonus rare ou cosmétique spécial pour les parcelles parfaites.
