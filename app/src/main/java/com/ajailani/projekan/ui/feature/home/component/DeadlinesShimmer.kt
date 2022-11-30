package com.ajailani.projekan.ui.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Card
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
fun DeadlinesShimmer() {
    val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.View)

    Row {
        LazyRow(contentPadding = PaddingValues(horizontal = 20.dp)) {
            items(2) {
                Card(
                    modifier = Modifier.shimmer(shimmerInstance),
                    shape = MaterialTheme.shapes.large,
                    elevation = 0.dp
                ) {
                    Column(
                        modifier = Modifier
                            .width(280.dp)
                            .padding(15.dp)
                    ) {
                        Row {
                            Box(
                                modifier = Modifier
                                    .clip(MaterialTheme.shapes.small)
                                    .size(50.dp)
                                    .background(color = BackgroundShimmer)
                                    .shimmer(shimmerInstance)
                            )
                            Spacer(modifier = Modifier.width(15.dp))
                            Column {
                                Box(
                                    modifier = Modifier
                                        .clip(MaterialTheme.shapes.small)
                                        .size(width = 100.dp, height = 20.dp)
                                        .background(color = BackgroundShimmer)
                                        .shimmer(shimmerInstance)
                                )
                                Spacer(modifier = Modifier.height(5.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(MaterialTheme.shapes.small)
                                        .size(width = 120.dp, height = 20.dp)
                                        .background(color = BackgroundShimmer)
                                        .shimmer(shimmerInstance)
                                )
                                Spacer(modifier = Modifier.height(30.dp))
                                Row {
                                    Box(
                                        modifier = Modifier
                                            .clip(MaterialTheme.shapes.small)
                                            .size(width = 40.dp, height = 15.dp)
                                            .background(color = BackgroundShimmer)
                                            .shimmer(shimmerInstance)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Box(
                                        modifier = Modifier
                                            .clip(MaterialTheme.shapes.small)
                                            .size(width = 40.dp, height = 15.dp)
                                            .background(color = BackgroundShimmer)
                                            .shimmer(shimmerInstance)
                                    )
                                }
                                Spacer(modifier = Modifier.height(20.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(MaterialTheme.shapes.small)
                                        .size(width = 100.dp, height = 20.dp)
                                        .background(color = BackgroundShimmer)
                                        .shimmer(shimmerInstance)
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.width(15.dp))
            }
        } 
    }
}