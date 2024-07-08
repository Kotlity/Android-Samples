package com.kotlity.network.view.composables.specific.items.album_photos_item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import coil.compose.AsyncImage
import com.kotlity.network.R
import com.kotlity.network.model.data.AlbumPhoto

@Composable
fun AlbumPhotosItem(
    modifier: Modifier = Modifier,
    albumPhoto: AlbumPhoto,
    placeHolder: Painter = painterResource(id = R.drawable.icon_empty)
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.albumId, albumPhoto.albumId),
            fontWeight = FontWeight.Bold
        )
        Text(text = stringResource(R.string.id, albumPhoto.id))
        Text(
            text = stringResource(R.string.title, albumPhoto.title),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        AsyncImage(
            modifier = Modifier
                .size(dimensionResource(R.dimen._80dp))
                .align(Alignment.CenterHorizontally),
            model = albumPhoto.url,
            placeholder = placeHolder,
            contentDescription = null
        )
    }
}