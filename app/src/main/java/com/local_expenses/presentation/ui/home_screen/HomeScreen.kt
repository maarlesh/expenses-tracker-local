package com.local_expenses.presentation.ui.home_screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.local_expenses.data.local.entity.AccountEntity
import com.local_expenses.presentation.theme.AppGradientBrush
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.local_expenses.presentation.theme.AppGradientBrush2

@Composable
fun HomeScreen(
    accounts: List<AccountEntity>,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppGradientBrush2)
            .systemBarsPadding()
    ) {
        Column(
            Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
        ) {
            AccountCarousel(accounts)
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            BottomNavBar()
        }
    }
}



@Composable
fun AccountCarousel(
    accounts: List<AccountEntity>,
    modifier: Modifier = Modifier
) {
    Log.d("Accounts for the user", accounts.toString())
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp)
    ) {
        items(accounts) { account ->
            AccountCard(account = account)
        }
        item {
            Box(
                modifier = Modifier
                    .width(220.dp)
                    .height(110.dp)
                    .background(Color.White.copy(alpha = 0.25f), RoundedCornerShape(28.dp))
                    .border(
                        1.dp,
                        Brush.linearGradient(
                            listOf(Color.White.copy(alpha = 0.55f), Color.White.copy(alpha = 0.12f))
                        ),
                        RoundedCornerShape(28.dp)
                    )
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(44.dp)
                            .shadow(10.dp, shape = CircleShape)
                            .background(MaterialTheme.colorScheme.secondaryContainer, shape = CircleShape)
                    ) {
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add",
                                tint = Color.White,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                    Spacer(
                        modifier = Modifier.width(
                            12.dp
                        )
                    )
                    Text(
                        text = "Add an account",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Composable
fun AccountCard(account: AccountEntity) {
    Box(
        modifier = Modifier
            .width(220.dp)
            .background(Color.White.copy(alpha = 0.25f), RoundedCornerShape(28.dp))
            .border(
                1.dp,
                Brush.linearGradient(
                    listOf(Color.White.copy(alpha = 0.55f), Color.White.copy(alpha = 0.12f))
                ),
                RoundedCornerShape(28.dp)
            )
            .padding(20.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = account.accountName,
                color = Color.White
            )
            Spacer(Modifier.height(14.dp))
            Text(
                text = "₹ %.2f".format(account.balance),
                fontSize = 28.sp,
                color = Color.White,
            )
        }
    }
}


@Composable
fun BottomNavBar(
    selectedIndex: Int = 0,
    onItemSelected: (Int) -> Unit = {}
) {
    Box(
        Modifier
            .fillMaxWidth()
            .background(
                Color.White.copy(alpha = 0.20f),
                shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
            )
            .height(70.dp)
    ) {
        Row(
            Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onItemSelected(0) }) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(60.dp)
                    .shadow(10.dp, shape = CircleShape)
                    .background(MaterialTheme.colorScheme.secondaryContainer, shape = CircleShape)
            ) {
                IconButton(onClick = { onItemSelected(1) }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        tint = Color.White,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }

            // Account item
            IconButton(onClick = { onItemSelected(2) }) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Account",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}