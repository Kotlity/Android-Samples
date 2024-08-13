package com.kotlity.network.view.composables.specific.items.album_photos_item

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.dimensionResource
import com.kotlity.network.R
import com.kotlity.network.model.data.AlbumPhoto

@Composable
fun AlbumPhotosItemContent(
    modifier: Modifier = Modifier,
    albumPhoto: AlbumPhoto,
    shape: Shape = CardDefaults.shape,
    colors: CardColors = CardDefaults.cardColors(),
    elevation: CardElevation = CardDefaults.cardElevation(),
    border: BorderStroke? = null,
) {
    Card(
        modifier = modifier,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border
    ) {
        AlbumPhotosItem(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen._2dp)),
            albumPhoto = albumPhoto
        )
    }
}