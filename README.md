# ProductExplorer — TP4 à TP8 (Jetpack Compose)

Application Android de catalogue de produits, développée en **Kotlin** avec **Jetpack Compose** et **Material Design 3** dans le cadre des travaux pratiques de développement Android.

L'écran principal présente **quatre produits locaux** et permet de les rechercher, de limiter l'affichage aux articles en stock et de les ajouter ou retirer des favoris.

## Fonctionnalités par TP

### TP4 — Fiche détaillée d'un produit

- Nom, marque, catégorie, prix, remise et note du produit.
- Illustration de remplacement, description, disponibilité et informations de livraison.
- Garantie affichée dans un `OutlinedTextField` en lecture seule.
- Bouton « Ajouter au panier » avec callback (panier non implémenté).
- Previews d'un produit disponible et d'un produit en rupture de stock.

### TP5 — Écran d'accueil

- En-tête `HomeHeader` et aperçu de barre de recherche `SearchPreviewBar`.
- Produit mis en avant avec récapitulatif du prix, de la note et du stock.
- Catégories et encart « Offre du jour ».
- Preview de l'écran d'accueil.

### TP6 — Catalogue local

- Quatre produits d'exemple dans `sampleProducts()`, chacun avec un identifiant unique.
- Défilement vertical du catalogue avec `LazyColumn` et `items`.
- Catégories disposées horizontalement avec `LazyRow`.
- Cartes réutilisables `ProductListItem`.
- Preview du catalogue.

### TP7 — Material Design 3

- Palettes personnalisées **claire et sombre** dans `ui/theme/Theme.kt`.
- Typographie personnalisée dans `ui/theme/Type.kt`.
- Formes arrondies, couleurs du thème et élévation des cartes.
- Previews `ProductCatalogScreenLightPreview` et `ProductCatalogScreenDarkPreview`.
- Thème de l'application adapté au réglage clair/sombre de l'appareil.

### TP8 — Interactions locales

- Recherche interactive par **nom, marque ou catégorie**, sans distinction entre majuscules et minuscules.
- Filtre « En stock uniquement » (stock strictement supérieur à zéro).
- Recherche et filtre de stock combinables.
- Compteur du nombre de produits affichés.
- Ajout et retrait des favoris pour chaque produit, à partir de son identifiant.
- Mise à jour automatique de l'interface quand la recherche, le filtre ou les favoris changent.
- Conservation de ces états simples lors d'une recréation de l'écran, notamment lors d'une rotation.

## Concepts Compose utilisés

`@Composable`, `@Preview`, `Modifier`, `Text`, `Button`, `Card`, `Surface`, `Icon`, `Image`, `OutlinedTextField`, `Column`, `Row`, `Box`, `Spacer`, `LazyColumn`, `LazyRow`, `items`, `MaterialTheme`, `mutableStateOf`, `remember` et `rememberSaveable`.

- **`mutableStateOf`** : crée un état observable par Compose.
- **`remember`** : mémorise le résultat du filtrage tant que ses dépendances ne changent pas.
- **`rememberSaveable`** : conserve la recherche, l'activation du filtre et les identifiants des favoris lors de certaines recréations de l'activité.

## Données d'exemple

| Produit | Catégorie | Stock initial |
|---|---|---:|
| Smartphone Toto X | Smartphones | 34 |
| Casque Audio Pulse | Audio | 0 |
| Montre Connectée FitTime | Wearables | 18 |
| Enceinte Mini Boom | Audio | 52 |

Ces données sont définies dans le code. **Aucune API, base de données ou authentification** n'est utilisée.

## Tester le TP8

1. Laisser le champ vide : les **4 produits** sont visibles.
2. Rechercher `audio` : **2 produits** correspondent.
3. Activer « En stock uniquement » : **3 produits** restent visibles avec la recherche vide.
4. Rechercher `audio` avec ce filtre : **1 produit** apparaît.
5. Ajouter un favori puis cliquer à nouveau pour le retirer.
6. Modifier la recherche, activer/désactiver le filtre et vérifier que les favoris restent sélectionnés.
7. Faire pivoter l'émulateur et vérifier la conservation des états.

**Limites actuelles :** les boutons « Voir le produit » du catalogue et « Ajouter au panier » ne déclenchent pas encore de navigation ou de gestion du panier. Les favoris sont stockés dans l'état Compose, et non de manière permanente après fermeture complète de l'application.

## Structure principale

- `app/src/main/java/com/example/productexplorer/MainActivity.kt` : activité, données locales, écrans et composants Compose.
- `app/src/main/java/com/example/productexplorer/ui/theme/Theme.kt` : palettes de couleurs et formes.
- `app/src/main/java/com/example/productexplorer/ui/theme/Type.kt` : typographie.
- `app/src/main/res/` : ressources Android.

## Lancer le projet

1. Cloner le dépôt :
   ```bash
   git clone https://github.com/AlexTng44000/ProductExplorer.git
   ```
2. Ouvrir le dossier `ProductExplorer` avec Android Studio.
3. Synchroniser Gradle.
4. Choisir un appareil virtuel Android, puis lancer la configuration **app** avec **Run**.

## Dépôt GitHub

https://github.com/AlexTng44000/ProductExplorer

Les fonctionnalités ont été ajoutées progressivement, avec des commits séparés pour les différentes étapes des TP.
