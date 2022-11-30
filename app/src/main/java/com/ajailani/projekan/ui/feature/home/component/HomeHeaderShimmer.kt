package com.ajailani.projekan.ui.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.ajailani.projekan.ui.theme.BackgroundShimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun HomeHeaderShimmer() {
    val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.View)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shimmer(shimmerInstance),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Box(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .background(color = BackgroundShimmer)
                    .size(width = 100.dp, height = 20.dp)
                    .shimmer(shimmerInstance)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Box(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .background(color = BackgroundShimmer)
                    .size(width = 120.dp, height = 20.dp)
                    .shimmer(shimmerInstance)
            )
        }
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(color = BackgroundShimmer)
                .size(55.dp)
                .shimmer(shimmerInstance)
        )
    }
}