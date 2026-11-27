package com.example.productexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.productexplorer.ui.theme.ProductExplorerTheme
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ProductExplorerTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    ProductCatalogContainer(
                        onProductClick = { product ->
                            // Navigation vers le détail prévue plus tard
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ProductHomeScreen(
    featuredProduct: ProductUi,
    onFeaturedProductClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        HomeHeader()

        Spacer(modifier = Modifier.height(24.dp))

        SearchPreviewBar()

        Spacer(modifier = Modifier.height(24.dp))

        FeaturedProductSection(
            product = featuredProduct,
            onClick = onFeaturedProductClick
        )

        Spacer(modifier = Modifier.height(24.dp))

        CategoriesSection()

        Spacer(modifier = Modifier.height(24.dp))

        DailyOfferBox()

    }
}

@Composable
fun HomeHeader(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Product Explorer",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Découvrez les produits du moment",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
@Composable
fun SearchPreviewBar(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 2.dp
    ) {
        Text(
            text = "Rechercher un produit...",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
@Preview(showBackground = true)
@Composable
fun ProductHomeScreenPreview() {
    ProductExplorerTheme {
        ProductHomeScreen(
            featuredProduct = sampleProduct(),
            onFeaturedProductClick = {}
        )
    }
}

@Composable
fun FeaturedProductSection(
    product: ProductUi,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        tonalElevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Produit mis en avant",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = product.title,
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = product.brand,
                style = MaterialTheme.typography.bodyMedium
            )


            Spacer(modifier = Modifier.height(12.dp))

            ProductQuickInfoRow(
                price = product.price,
                rating = product.rating,
                stock = product.stock
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(onClick = onClick) {
                Text(text = "Voir le produit")
            }

        }
    }
}

@Composable
fun ProductQuickInfoRow(
    price: Double,
    rating: Double,
    stock: Int,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "$price €",
            modifier = Modifier.weight(1f)
        )

        Text(
            text = "★ $rating",
            modifier = Modifier.weight(1f)
        )

        Text(
            text = "$stock en stock",
            modifier = Modifier.weight(1f)
        )
    }
}
@Composable
fun CategoryChip(
    label: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.extraLarge,
        color = MaterialTheme.colorScheme.secondary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(
                horizontal = 14.dp,
                vertical = 8.dp
            ),
            style = MaterialTheme.typography.labelLarge
        )
    }
}
@Composable
fun CategoriesSection(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Catégories",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            CategoryChip(label = "Smartphones")
            Spacer(modifier = Modifier.weight(1f))
            CategoryChip(label = "Audio")
            Spacer(modifier = Modifier.weight(1f))
            CategoryChip(label = "Maison")
        }
    }
}
@Composable
fun DailyOfferBox(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = 2.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Offre du jour",
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "Jusqu’à -20 % sur une sélection de produits",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Surface(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp),
            shape = MaterialTheme.shapes.small,
            tonalElevation = 6.dp
        ) {
            Text(
                text = "Nouveau",
                modifier = Modifier.padding(
                    horizontal = 8.dp,
                    vertical = 4.dp
                ),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductDetailScreenPreview() {
    ProductExplorerTheme {
        ProductDetailScreen(
            product = sampleProduct(),
            onAddToCartClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProductDetailScreenOutOfStockPreview() {
    ProductExplorerTheme {
        ProductDetailScreen(
            product = sampleProductOutOfStock(),
            onAddToCartClick = {}
        )
    }
}

@Composable
fun ProductDetailScreen(
    product: ProductUi,
    onAddToCartClick: () -> Unit,
    canAddToCart: Boolean = true,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

    ProductImage(
            productTitle = product.title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        ProductHeader(
            title = product.title,
            brand = product.brand,
            category = product.category
        )


        ProductPriceCard(
            price = product.price,
            discountPercentage = product.discountPercentage,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )

        ProductRating(
            rating = product.rating,
            modifier = Modifier.padding(top = 16.dp)
        )

        ProductAvailabilityCard(
            stock = product.stock,
            shippingInformation = product.shippingInformation,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )

        ProductDescription(
            description = product.description,
            modifier = Modifier.padding(top = 16.dp)
        )

        ProductWarrantyField(
            warrantyInformation = product.warrantyInformation,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )


        AddToCartButton(
            onClick = onAddToCartClick,
            enabled = canAddToCart && product.stock > 0,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
    }
}

@Composable
fun ProductRating(
    rating: Double,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = "Note du produit"
        )

        Text(
            text = "$rating / 5",
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
fun ProductAvailabilityCard(
    stock: Int,
    shippingInformation: String,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Disponibilité",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Stock : $stock unités",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = shippingInformation,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun ProductDescription(
    description: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Description",
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun ProductWarrantyField(
    warrantyInformation: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = warrantyInformation,
        onValueChange = {},
        readOnly = true,
        label = {
            Text(text = "Garantie")
        },
        modifier = modifier
    )
}

@Composable
fun AddToCartButton(
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
    ) {
        Text(
            text = if (enabled) {
                "Ajouter au panier"
            } else {
                "Ajout indisponible"
            }
        )
    }
}

@Composable
fun ProductHeader(
    title: String,
    brand: String,
    category: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = brand,
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = category,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun ProductPriceCard(
    price: Double,
    discountPercentage: Double,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "$price €",
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = "Remise : $discountPercentage %",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun ProductImage(
    productTitle: String,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = android.R.drawable.ic_menu_gallery),
        contentDescription = "Image du produit $productTitle",
        modifier = modifier
    )
}


data class ProductUi(
    val id: Int,
    val title: String,
    val brand: String,
    val category: String,
    val description: String,
    val price: Double,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Int,
    val warrantyInformation: String,
    val shippingInformation: String
)

fun sampleProduct(): ProductUi {
    return ProductUi(
        id =1,
        title = "Smartphone Toto X",
        brand = "TotoTech",
        category = "Smartphones",
        description = "Un smartphone léger avec un écran lumineux, une bonne autonomie et un design moderne.",
        price = 699.99,
        discountPercentage = 12.5,
        rating = 4.6,
        stock = 34,
        warrantyInformation = "Garantie constructeur : 2 ans",
        shippingInformation = "Livraison estimée : 3 à 5 jours ouvrés"
    )
}

fun sampleProducts(): List<ProductUi> {
    return listOf(
        sampleProduct(),
        sampleProductOutOfStock(),

        ProductUi(
            id = 3,
            title = "Montre Connectée FitTime",
            brand = "FitTime",
            category = "Wearables",
            description = "Une montre connectée simple pour suivre l'activité quotidienne.",
            price = 89.99,
            discountPercentage = 15.0,
            rating = 4.4,
            stock = 18,
            warrantyInformation = "Garantie constructeur : 2 ans",
            shippingInformation = "Livraison estimée : 2 à 4 jours ouvrés"
        ),

        ProductUi(
            id = 4,
            title = "Enceinte Mini Boom",
            brand = "BoomSound",
            category = "Audio",
            description = "Une enceinte compacte pour écouter de la musique.",
            price = 59.99,
            discountPercentage = 5.0,
            rating = 4.1,
            stock = 52,
            warrantyInformation = "Garantie constructeur : 1 an",
            shippingInformation = "Livraison estimée : 3 jours ouvrés"
        )
    )
}

fun sampleProductOutOfStock(): ProductUi {
    return ProductUi(
        id=2,
        title = "Casque Audio Pulse",
        brand = "SoundPeak",
        category = "Audio",
        description = "Un casque confortable conçu pour écouter de la musique, " +
                "suivre des cours en ligne et travailler dans de bonnes conditions.",
        price = 129.99,
        discountPercentage = 8.0,
        rating = 4.2,
        stock = 0,
        warrantyInformation = "Garantie constructeur : 1 an",
        shippingInformation = "Produit temporairement indisponible"
    )
}

fun sampleCategories(): List<String> {
    return listOf("Smartphones", "Audio", "Wearables", "Maison")
}

@Composable
fun CategoryRow(
    categories: List<String>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { category ->
            CategoryChip(label = category)
        }
    }
}


@Composable
fun ProductListItem(
    product: ProductUi,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = product.title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = product.brand,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${product.price} € • ★ ${product.rating}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(onClick = onClick) {
                Text(text = "Voir le produit")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = onFavoriteClick) {
                Text(
                    text = if (isFavorite) {
                        "Retirer des favoris"
                    } else {
                        "Ajouter aux favoris"
                    }
                )
            }
        }
    }
}

data class CartItem(
    val product: ProductUi,
    val quantity: Int = 1
)

data class ProductCatalogUiState(
    val products: List<ProductUi> = emptyList(),
    val categories: List<String> = emptyList(),
    val searchQuery: String = "",
    val showOnlyInStock: Boolean = false,
    val favoriteProductIds: List<Int> = emptyList(),
    val selectedProduct: ProductUi? = null,
    val cartItems: List<CartItem> = emptyList(),
    val isCartOpen: Boolean = false
)

class ProductCatalogViewModel : ViewModel() {

    private val allProducts: List<ProductUi> = sampleProducts()
    private val allCategories: List<String> = sampleCategories()

    private val _uiState = MutableStateFlow(
        ProductCatalogUiState(
            products = allProducts,
            categories = allCategories
        )
    )

    val uiState: StateFlow<ProductCatalogUiState> =
        _uiState.asStateFlow()

    private fun filterProducts(
        searchQuery: String,
        showOnlyInStock: Boolean
    ): List<ProductUi> {
        return allProducts.filter { product ->

            val matchesSearch =
                product.title.contains(searchQuery, ignoreCase = true) ||
                        product.brand.contains(searchQuery, ignoreCase = true) ||
                        product.category.contains(searchQuery, ignoreCase = true)

            val matchesStock =
                !showOnlyInStock || product.stock > 0

            matchesSearch && matchesStock
        }
    }

    fun onSearchQueryChange(newValue: String) {
        _uiState.update { currentState ->
            currentState.copy(
                searchQuery = newValue,
                products = filterProducts(
                    searchQuery = newValue,
                    showOnlyInStock = currentState.showOnlyInStock
                )
            )
        }
    }

    fun onToggleStockFilter() {
        _uiState.update { currentState ->

            val newShowOnlyInStock =
                !currentState.showOnlyInStock

            currentState.copy(
                showOnlyInStock = newShowOnlyInStock,
                products = filterProducts(
                    searchQuery = currentState.searchQuery,
                    showOnlyInStock = newShowOnlyInStock
                )
            )
        }
    }

    fun onFavoriteClick(productId: Int) {
        _uiState.update { currentState ->

            val newFavoriteIds =
                if (currentState.favoriteProductIds.contains(productId)) {
                    currentState.favoriteProductIds - productId
                } else {
                    currentState.favoriteProductIds + productId
                }

            currentState.copy(
                favoriteProductIds = newFavoriteIds
            )
        }
    }

    fun onProductSelected(product: ProductUi) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedProduct = product
            )
        }
    }

    fun onBackToCatalog() {
        _uiState.update { currentState ->
            currentState.copy(
                selectedProduct = null
            )
        }
    }

    fun onAddToCart(product: ProductUi) {
        _uiState.update { currentState ->
            val existingItem = currentState.cartItems
                .firstOrNull { it.product.id == product.id }

            val currentQuantity = existingItem?.quantity ?: 0

            if (product.stock <= 0 || currentQuantity >= product.stock) {
                currentState
            } else {
                val newCartItems = if (existingItem == null) {
                    currentState.cartItems + CartItem(product)
                } else {
                    currentState.cartItems.map { item ->
                        if (item.product.id == product.id) {
                            item.copy(quantity = item.quantity + 1)
                        } else {
                            item
                        }
                    }
                }

                currentState.copy(cartItems = newCartItems)
            }
        }
    }

    fun onIncreaseQuantity(productId: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                cartItems = currentState.cartItems.map { item ->
                    if (
                        item.product.id == productId &&
                        item.quantity < item.product.stock
                    ) {
                        item.copy(quantity = item.quantity + 1)
                    } else {
                        item
                    }
                }
            )
        }
    }

    fun onDecreaseQuantity(productId: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                cartItems = currentState.cartItems.mapNotNull { item ->
                    when {
                        item.product.id != productId -> item
                        item.quantity > 1 ->
                            item.copy(quantity = item.quantity - 1)
                        else -> null
                    }
                }
            )
        }
    }

    fun onRemoveFromCart(productId: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                cartItems = currentState.cartItems.filterNot {
                    it.product.id == productId
                }
            )
        }
    }

    fun onOpenCart() {
        _uiState.update { currentState ->
            currentState.copy(isCartOpen = true)
        }
    }

    fun onCloseCart() {
        _uiState.update { currentState ->
            currentState.copy(isCartOpen = false)
        }
    }
}


@Composable
fun ProductCatalogContainer(
    onProductClick: (ProductUi) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProductCatalogViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    BackHandler(
        enabled = uiState.isCartOpen || uiState.selectedProduct != null
    ) {
        if (uiState.isCartOpen) {
            viewModel.onCloseCart()
        } else {
            viewModel.onBackToCatalog()
        }
    }

    val cartCount = uiState.cartItems.sumOf { it.quantity }
    val selectedProduct = uiState.selectedProduct

    when {
        uiState.isCartOpen -> {
            CartScreen(
                cartItems = uiState.cartItems,
                onIncreaseQuantity = viewModel::onIncreaseQuantity,
                onDecreaseQuantity = viewModel::onDecreaseQuantity,
                onRemoveItem = viewModel::onRemoveFromCart,
                onBackClick = viewModel::onCloseCart,
                modifier = modifier
            )
        }

        selectedProduct != null -> {
            val quantityInCart = uiState.cartItems
                .firstOrNull { it.product.id == selectedProduct.id }
                ?.quantity ?: 0

            Column(
                modifier = modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = viewModel::onBackToCatalog
                    ) {
                        Text("Retour")
                    }

                    Button(
                        onClick = viewModel::onOpenCart
                    ) {
                        Text("Panier ($cartCount)")
                    }
                }

                ProductDetailScreen(
                    product = selectedProduct,
                    onAddToCartClick = {
                        viewModel.onAddToCart(selectedProduct)
                    },
                    canAddToCart = quantityInCart < selectedProduct.stock,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        else -> {
            ProductCatalogScreen(
                products = uiState.products,
                categories = uiState.categories,
                searchQuery = uiState.searchQuery,
                onSearchQueryChange = viewModel::onSearchQueryChange,
                showOnlyInStock = uiState.showOnlyInStock,
                onToggleStockFilter = viewModel::onToggleStockFilter,
                favoriteProductIds = uiState.favoriteProductIds,
                onFavoriteClick = viewModel::onFavoriteClick,
                onProductClick = { product ->
                    viewModel.onProductSelected(product)
                    onProductClick(product)
                },
                cartItemCount = cartCount,
                onCartClick = viewModel::onOpenCart,
                modifier = modifier
            )
        }
    }
}

@Composable
fun CartScreen(
    cartItems: List<CartItem>,
    onIncreaseQuantity: (Int) -> Unit,
    onDecreaseQuantity: (Int) -> Unit,
    onRemoveItem: (Int) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val total = cartItems.sumOf { item ->
        item.product.price * item.quantity
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Mon panier",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        item {
            Button(onClick = onBackClick) {
                Text("Retour")
            }
        }

        if (cartItems.isEmpty()) {
            item {
                Text("Votre panier est vide.")
            }
        } else {
            items(
                items = cartItems,
                key = { it.product.id }
            ) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = item.product.title,
                            style = MaterialTheme.typography.titleLarge
                        )

                        Text(
                            text = String.format(
                                Locale.FRANCE,
                                "%.2f € × %d",
                                item.product.price,
                                item.quantity
                            )
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = {
                                    onDecreaseQuantity(item.product.id)
                                }
                            ) {
                                Text("−")
                            }

                            Text("${item.quantity}")

                            Button(
                                onClick = {
                                    onIncreaseQuantity(item.product.id)
                                },
                                enabled = item.quantity < item.product.stock
                            ) {
                                Text("+")
                            }
                        }

                        Button(
                            onClick = {
                                onRemoveItem(item.product.id)
                            }
                        ) {
                            Text("Retirer du panier")
                        }
                    }
                }
            }

            item {
                Text(
                    text = String.format(
                        Locale.FRANCE,
                        "Total : %.2f €",
                        total
                    ),
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = "Total calculé avant application des remises.",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun ProductCatalogScreen(
    products: List<ProductUi>,
    categories: List<String>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    showOnlyInStock: Boolean,
    onToggleStockFilter: () -> Unit,
    favoriteProductIds: List<Int>,
    onFavoriteClick: (Int) -> Unit,
    onProductClick: (ProductUi) -> Unit,
    cartItemCount: Int = 0,
    onCartClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Catalogue produits",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        item {
            Button(onClick = onCartClick) {
                Text("Voir mon panier ($cartItemCount)")
            }
        }

        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                label = {
                    Text(text = "Rechercher un produit")
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Button(
                onClick = onToggleStockFilter
            ) {
                Text(
                    text = if (showOnlyInStock) {
                        "Afficher tous les produits"
                    } else {
                        "Afficher uniquement les produits en stock"
                    }
                )
            }
        }

        item {
            Text(
                text = "Explorez une sélection de produits disponibles localement.",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        item {
            CategoryRow(categories = categories)
        }

        item {
            Text(
                text = "${products.size} produit(s) affiché(s)",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        items(
            items = products,
            key = { product -> product.id }
        ) { product ->
            ProductListItem(
                product = product,
                isFavorite = favoriteProductIds.contains(product.id),
                onFavoriteClick = {
                    onFavoriteClick(product.id)
                },
                onClick = {
                    onProductClick(product)
                }
            )
        }
    }
}

@Preview(
    showBackground = true,
    name = "Catalogue - Light Theme"
)
@Composable
fun ProductCatalogScreenLightPreview() {
    ProductExplorerTheme(darkTheme = false) {
        ProductCatalogScreen(
            products = sampleProducts(),
            categories = sampleCategories(),
            searchQuery = "",
            onSearchQueryChange = {},
            showOnlyInStock = false,
            onToggleStockFilter = {},
            favoriteProductIds = listOf(1),
            onFavoriteClick = {},
            onProductClick = {}
        )
    }
}

@Preview(
    showBackground = true,
    name = "Catalogue - Dark Theme"
)
@Composable
fun ProductCatalogScreenDarkPreview() {
    ProductExplorerTheme(darkTheme = true) {
        ProductCatalogScreen(
            products = sampleProducts(),
            categories = sampleCategories(),
            searchQuery = "audio",
            onSearchQueryChange = {},
            showOnlyInStock = true,
            onToggleStockFilter = {},
            favoriteProductIds = listOf(2),
            onFavoriteClick = {},
            onProductClick = {}
        )
    }
}
