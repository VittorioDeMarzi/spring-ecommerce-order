package ecommerce.mapper

import ecommerce.dto.CartItemResponse
import ecommerce.model.CartItem

class CartItemMapper

fun CartItem.toDto(): CartItemResponse =
    CartItemResponse(
        option.product!!.id,
        productName = option.product!!.name,
        quantity = quantity,
        productPrice = option.product!!.price.toDouble(),
        productImageUrl = option.product!!.imageUrl,
    )
