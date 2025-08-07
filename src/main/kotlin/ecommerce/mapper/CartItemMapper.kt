package ecommerce.mapper

import ecommerce.dto.CartItemResponse
import ecommerce.model.CartItem

class CartItemMapper

fun CartItem.toDto(): CartItemResponse =
    CartItemResponse(
        id = id,
        productName = option.product!!.name,
        optionName = option.name,
        quantity = quantity,
        productPrice = option.product!!.price.toDouble(),
        productImageUrl = option.product!!.imageUrl,
    )
