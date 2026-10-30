# ProductExplorer – TP4 et TP5 Jetpack Compose

Application Android réalisée en Kotlin avec Jetpack Compose dans le cadre des TP4 et TP5 du cours de développement Android.

Le projet contient deux écrans : une fiche de détail produit (TP4) et un écran d'accueil (TP5). Les données sont définies localement dans `ProductUi` et `sampleProduct()`.

## Fonctionnalités

### TP4 – Détail d'un produit

- Affichage du nom, de la marque et de la catégorie.
- Image de remplacement, prix, pourcentage de remise et note avec une étoile.
- Carte de disponibilité : stock et délai de livraison.
- Description et champ de garantie en lecture seule.
- Bouton « Ajouter au panier » avec un callback.
- Deux previews : un smartphone disponible et un casque audio en rupture de stock.

### TP5 – Écran d'accueil

- En-tête `HomeHeader` et barre de recherche visuelle `SearchPreviewBar`.
- Carte du produit mis en avant `FeaturedProductSection`.
- Ligne récapitulative avec prix, note et stock (`ProductQuickInfoRow`).
- Catégories « Smartphones », « Audio » et « Maison ».
- Bloc « Offre du jour » avec un badge « Nouveau ».
- Preview complète de l'écran d'accueil.

## Notions Jetpack Compose utilisées

`@Composable`, `@Preview`, `Column`, `Row`, `Box`, `Spacer`, `Surface`, `Card`, `Text`, `Image`, `Icon`, `Button`, `OutlinedTextField`, `Modifier`, `MaterialTheme`, paramètres et callbacks.

Les composables sont réutilisables grâce à leurs paramètres et, lorsque c'est pertinent, à `modifier: Modifier = Modifier`.

## Organisation du code

- `app/src/main/java/com/example/productexplorer/MainActivity.kt` : activité principale, modèle `ProductUi`, données d'exemple, écrans et composables.
- `app/src/main/java/com/example/productexplorer/ui/theme/` : couleurs, typographie et thème Compose.
- `app/src/main/res/` : ressources Android.

`ProductHomeScreen` est l'écran affiché au démarrage. `ProductDetailScreen` reste disponible dans le projet et dans ses previews.

## Fonctionnement des interactions

Les boutons « Voir le produit » et « Ajouter au panier » transmettent un clic via un callback, mais aucune navigation ni gestion du panier n'est encore implémentée. La barre de recherche et les catégories sont uniquement visuelles, conformément à l'objectif de ces TP.

L'image produit utilise `contentDescription` pour l'accessibilité. Les composants `Button` fournissent déjà une sémantique adaptée à leur rôle.

## Lancer le projet

1. Cloner le dépôt :

   ```bash
   git clone https://github.com/AlexTng44000/ProductExplorer.git
   ```

2. Ouvrir le dossier `ProductExplorer` dans Android Studio.
3. Attendre la synchronisation Gradle.
4. Choisir un émulateur Android, puis lancer la configuration `app` avec **Run**.

## Dépôt GitHub

https://github.com/AlexTng44000/ProductExplorer

Les TP4 et TP5 ont été développés progressivement avec des commits distincts pour chaque fonctionnalité.