# ProductExplorer — TP4 à TP11 (Jetpack Compose)

Application Android de catalogue de produits développée en **Kotlin** avec **Jetpack Compose** et **Material Design 3** dans le cadre des travaux pratiques de développement Android.

L'écran principal présente **quatre produits locaux**. Il permet de rechercher des produits, de filtrer ceux en stock et de gérer des favoris. Depuis le TP11, les informations affichées et les actions utilisateur sont gérées par un **ViewModel** qui expose un **StateFlow**.

## Fonctionnalités par TP

### TP4 — Fiche détaillée d'un produit

- Affichage du nom, de la marque, de la catégorie, du prix, de la remise et de la note.
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
- Catégories sur une ligne horizontale avec `LazyRow`.
- Cartes réutilisables `ProductListItem`.
- Preview du catalogue.

### TP7 — Material Design 3

- Palettes personnalisées **claire et sombre** dans `ui/theme/Theme.kt`.
- Typographie dans `ui/theme/Type.kt`.
- Formes arrondies, couleurs du thème et élévation des cartes.
- Previews du catalogue en thèmes clair et sombre.
- Thème de l'application adapté au réglage de l'appareil.

### TP8 — Interactions locales

- Recherche par **nom, marque ou catégorie**, sans distinction entre majuscules et minuscules.
- Filtre « En stock uniquement » (stock strictement supérieur à zéro).
- Recherche et filtre de stock combinables.
- Compteur du nombre de produits affichés.
- Ajout et retrait des favoris à partir de l'identifiant du produit.
- Première gestion locale de ces états avec `rememberSaveable` ; cette implémentation a évolué dans les TP suivants.

### TP9 — State Hoisting

- Séparation entre `ProductCatalogContainer` (**Stateful**) et `ProductCatalogScreen` (**Stateless**).
- Remontée des événements grâce aux callbacks.
- `ProductListItem` reste un composant Stateless.
- Principe appliqué : les données descendent, les événements remontent.
- Conservation des fonctionnalités du TP8.

### TP10 — ViewModel

- Introduction de `ProductCatalogViewModel` pour déplacer les états et les actions hors des composables.
- `ProductCatalogContainer` relie le ViewModel à `ProductCatalogScreen`.
- Gestion initiale des états avec `mutableStateOf`, remplacée au TP11 par `StateFlow`.
- Conservation de la recherche, du filtre et des favoris lors de la rotation de l'écran.

### TP11 — StateFlow et état d'interface

- Création de `ProductCatalogUiState`, une `data class` qui regroupe les produits affichés, les catégories, la recherche, le filtre stock et les identifiants des favoris.
- Utilisation de `MutableStateFlow` **privé** dans `ProductCatalogViewModel`.
- Exposition d'un `StateFlow` en lecture seule avec `asStateFlow()`.
- Modification de l'état par `_uiState.update { currentState -> currentState.copy(...) }`.
- Filtrage recalculé dans le ViewModel lorsque la recherche ou le filtre stock change.
- Observation de l'état dans `ProductCatalogContainer` grâce à `collectAsState()`.
- Interface inchangée : `ProductCatalogScreen` et `ProductListItem` continuent d'afficher les données et de transmettre les interactions.

## Architecture actuelle

```text
MainActivity
    └── ProductCatalogContainer (Stateful)
            │
            ├── ProductCatalogViewModel
            │       └── StateFlow<ProductCatalogUiState>
            │               ├── products
            │               ├── categories
            │               ├── searchQuery
            │               ├── showOnlyInStock
            │               ├── favoriteProductIds
            │               └── selectedProduct
            │
            ├── ProductCatalogScreen (Stateless)
            │       ├── CategoryRow
            │       │       └── CategoryChip
            │       └── ProductListItem (Stateless)
            │
            └── ProductDetailScreen
                    ├── ProductImage
                    ├── ProductHeader
                    ├── ProductPriceCard
                    ├── ProductRating
                    ├── ProductAvailabilityCard
                    ├── ProductDescription
                    ├── ProductWarrantyField
                    └── AddToCartButton
```

### Rôle des composants

- **`MainActivity`** : lance l'application et affiche le conteneur principal.
- **`ProductCatalogViewModel`** : centralise les données, la recherche, le filtre stock, les favoris et le produit sélectionné.
- **`ProductCatalogUiState`** : regroupe les informations de l'interface dans un état observable.
- **`ProductCatalogContainer`** : observe le StateFlow avec `collectAsState()` et affiche le catalogue ou la fiche produit.
- **`ProductCatalogScreen`** : affiche le catalogue et transmet les actions utilisateur grâce aux callbacks.
- **`ProductListItem`** : affiche chaque produit avec ses boutons « Voir le produit » et « Ajouter aux favoris ».
- **`ProductDetailScreen`** : présente les informations détaillées du produit sélectionné.

### Gestion de la navigation

La navigation entre le catalogue et la fiche produit repose sur `selectedProduct`, une propriété de `ProductCatalogUiState`.

- `onProductSelected()` sélectionne un produit et ouvre sa fiche.
- `onBackToCatalog()` réinitialise la sélection pour revenir au catalogue.
- `BackHandler` permet également d'utiliser le bouton Retour Android.

L'application utilise `ViewModel` et `StateFlow` pour gérer les états, sans Navigation Compose.


## Données d'exemple

| Produit | Catégorie | Stock initial |
|---|---|---:|
| Smartphone Toto X | Smartphones | 34 |
| Casque Audio Pulse | Audio | 0 |
| Montre Connectée FitTime | Wearables | 18 |
| Enceinte Mini Boom | Audio | 52 |

Ces données sont définies localement dans le code. **Aucune API, base de données ou authentification** n'est utilisée.

## Tests fonctionnels (TP8 à TP11)

1. Démarrer l'application avec le champ vide : **4 produits** visibles.
2. Rechercher `audio` : **2 produits** affichés.
3. Effacer la recherche : retour à **4 produits**.
4. Activer « En stock uniquement » avec la recherche vide : **3 produits**.
5. Rechercher `audio` en conservant ce filtre : **1 produit**, « Enceinte Mini Boom ».
6. Ajouter un produit aux favoris, puis cliquer à nouveau pour le retirer.
7. Combiner recherche, filtre et favoris : le compteur et les boutons se mettent à jour.
8. Faire pivoter l'émulateur avec les filtres et un favori actifs : l'état est conservé par le ViewModel.
9. Vérifier les previews du catalogue en mode clair et sombre.
10. Vérifier que la compilation se termine par `BUILD SUCCESSFUL`.

### Fonctionnalité supplémentaire — Navigation vers la fiche produit

Cette évolution permet de consulter les informations détaillées
d'un produit depuis le catalogue.
**Limites actuelles :** la navigation vers la fiche détaillée est
fonctionnelle. En revanche, le bouton « Ajouter au panier »
ne permet pas encore de gérer les achats.

Les favoris sont conservés en mémoire par le ViewModel,
mais ne sont pas sauvegardés après la fermeture complète
du processus de l'application.

#### Fonctionnalités ajoutées

- Ouverture de la fiche en cliquant sur « Voir le produit ».
- Affichage du nom, de la marque, du prix et de la remise.
- Affichage de la note, du stock, de la description et de la garantie.
- Retour au catalogue avec un bouton dédié ou le bouton Retour Android.
- Conservation de la recherche, du filtre stock et des favoris.
- Défilement vertical de la fiche produit.

#### Fonctionnement technique

La navigation repose sur le ViewModel et StateFlow.

La propriété `selectedProduct` de `ProductCatalogUiState`
contient le produit sélectionné.

- `onProductSelected()` ouvre la fiche du produit.
- `onBackToCatalog()` permet de revenir au catalogue.
- `ProductCatalogContainer` affiche l'écran correspondant.
- `BackHandler` gère le bouton Retour Android.

Cette navigation simple ne nécessite pas Navigation Compose.

#### Captures d'écran

| Catalogue produits | Fiche détaillée |
| --- | --- |
| ![Catalogue](screenshots/catalogue-produits.png) | ![Fiche produit](screenshots/fiche-produit.png) |

#### Tests réalisés

- Ouverture d'une fiche depuis le catalogue.
- Affichage des informations du produit sélectionné.
- Retour au catalogue.
- Conservation des filtres et des favoris au retour.

**Prochaine fonctionnalité :** gestion du panier avec
ajout de produits, quantités et calcul du montant total.

## Notions et technologies utilisées

- **Interface :** Jetpack Compose, `@Composable`, `@Preview`, `Modifier`, `LazyColumn`, `LazyRow`, `Card`, `Button`, `OutlinedTextField`.
- **Apparence :** Material Design 3, couleurs, typographie, formes, thèmes clair et sombre.
- **États des premiers TP :** `remember`, `rememberSaveable`, `mutableStateOf`, State Hoisting.
- **Architecture actuelle :** `ViewModel`, `ProductCatalogUiState`, `MutableStateFlow`, `StateFlow`, `asStateFlow()`, `update`, `copy`, `collectAsState()`.

## Structure principale

- `app/src/main/java/com/example/productexplorer/MainActivity.kt` : activité, données locales, ViewModel, écrans et composants Compose.
- `app/src/main/java/com/example/productexplorer/ui/theme/Theme.kt` : palettes de couleurs et formes.
- `app/src/main/java/com/example/productexplorer/ui/theme/Type.kt` : typographie.
- `app/src/main/res/` : ressources Android.
- `gradle/libs.versions.toml` et `app/build.gradle.kts` : configuration des dépendances.

## Lancer le projet

1. Cloner le dépôt :
   ```bash
   git clone https://github.com/AlexTng44000/ProductExplorer.git
   ```
2. Ouvrir le dossier `ProductExplorer` dans Android Studio.
3. Synchroniser Gradle.
4. Sélectionner un émulateur Android.
5. Exécuter la configuration **app** avec **Run**.

## Dépôt GitHub

https://github.com/AlexTng44000/ProductExplorer

Les fonctionnalités ont été ajoutées progressivement, avec des commits séparés pour les différentes étapes des TP.
