package com.ajailani.projekan.ui.feature.project_detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.ajailani.projekan.ui.theme.BackgroundShimmer
import com.ajailani.projekan.ui.theme.extraLarge
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun ProjectDetailShimmer() {
    val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.View)

    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colors.surface)
            .padding(20.dp)
            .shimmer(shimmerInstance)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.small)
                        .size(50.dp)
                        .background(color = BackgroundShimmer)
                        .shimmer(shimmerInstance)
                )
                Spacer(modifier = Modifier.width(20.dp))
                Box(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.small)
                        .size(width = 100.dp, height = 20.dp)
                        .background(color = BackgroundShimmer)
                        .shimmer(shimmerInstance)
                )
            }
            Box(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .size(width = 40.dp, height = 20.dp)
                    .background(color = BackgroundShimmer)
                    .shimmer(shimmerInstance)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .size(width = 200.dp, height = 20.dp)
                .background(color = BackgroundShimmer)
                .shimmer(shimmerInstance)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row {
                    Box(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.small)
                            .size(width = 40.dp, height = 20.dp)
                            .background(color = BackgroundShimmer)
                            .shimmer(shimmerInstance)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Box(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.small)
                            .size(width = 40.dp, height = 20.dp)
                            .background(color = BackgroundShimmer)
                            .shimmer(shimmerInstance)
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
                Box(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.small)
                        .size(width = 100.dp, height = 20.dp)
                        .background(color = BackgroundShimmer)
                        .shimmer(shimmerInstance)
                )
            }
            Box(contentAlignment = Alignment.Center) {
                Box(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.extraLarge)
                        .size(60.dp)
                        .background(color = BackgroundShimmer)
                        .shimmer(shimmerInstance)
                )
            }
        }
    }
}