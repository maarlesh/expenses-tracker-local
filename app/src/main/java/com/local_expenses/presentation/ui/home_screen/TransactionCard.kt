package com.local_expenses.presentation.ui.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.DomainVerification
import androidx.compose.material.icons.filled.DragIndicator
import androidx.compose.material.icons.filled.MoveUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun TransactionCard(
    isIncome: Boolean,
    amount: Double,
    accountName: String,
    date: String,
    category: String,
    description: String,
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White.copy(alpha = 0.25f), RoundedCornerShape(5.dp))
            .border(
                1.dp,
                Brush.linearGradient(
                    listOf(Color.White.copy(alpha = 0.55f), Color.White.copy(alpha = 0.12f))
                ),
                RoundedCornerShape(5.dp)
            )
            .padding(
                horizontal = 2.dp,
                vertical = 4.dp,
            ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            if(isIncome){
                Icon(
                    imageVector = Icons.Default.ArrowDropUp,
                    contentDescription = "Income",
                    tint = Color.Green,
                    modifier = Modifier.size(40.dp)
                )
            }else{
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Expense",
                    tint = Color.Red,
                    modifier = Modifier.size(40.dp)
                )
            }
            Box(
                Modifier.width(100.dp)
            ) {
                Text(
                    text = "${if (isIncome) "+" else "-"}  ₹ ${amount.toString()}",
//                    text = "${if (isIncome) "+" else "-"}  ₹ ${2000000000}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (isIncome) Color.Green else Color.Red,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
//                    Icon(
//                        imageVector = Icons.Default.CalendarMonth,
//                        contentDescription = "Date",
//                        tint = Color.White,
//                        modifier = Modifier.size(18.dp)
//                    )
//                    Spacer(
//                        modifier = Modifier.width(8.dp)
//                    )
//                    Text(
//                        text = date,
//                        style = MaterialTheme.typography.bodyLarge,
//                        color = Color.White,
//                    )
//                    Spacer(
//                        modifier = Modifier.width(16.dp)
//                    )

                    Icon(
                        imageVector = Icons.Default.AccountBalanceWallet,
                        contentDescription = "Account",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(
                        modifier = Modifier.width(4.dp)
                    )
                    Box (
                        Modifier.width(100.dp)
                    ){
                        Text(
                            text = accountName,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White,
                            //                        overflow = TextOverflow.Ellipsis,
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.DragIndicator,
                        contentDescription = "Income",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(
                        modifier = Modifier.width(4.dp)
                    )
                    Text(
                        text = category,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                if(description != "")
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.DomainVerification,
                        contentDescription = "Income",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )

                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFF969696),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

            }
        }
    }
}

@Composable
fun TransferCard(
    date: String,
    fromAccountName: String,
    toAccountName: String,
    amount: Double,
    description: String?,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White.copy(alpha = 0.25f), RoundedCornerShape(5.dp))
            .border(
                1.dp,
                Brush.linearGradient(
                    listOf(Color.White.copy(alpha = 0.55f), Color.White.copy(alpha = 0.12f))
                ),
                RoundedCornerShape(5.dp)
            )
            .padding(horizontal = 10.dp, vertical = 4.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Icon(
                imageVector = Icons.Default.MoveUp,
                contentDescription = "Transfer",
                tint = Color.Cyan,
                modifier = Modifier.size(20.dp)
            )
            Spacer(Modifier.width(10.dp))
            Box(Modifier.width(100.dp)) {
                Text(
                    text = "₹ ${amount.toString()}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Cyan,
                    maxLines = 1,
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "From: ",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White,
                    )
                    Box(
                        modifier = Modifier.width(75.dp)
                    ) {
                        Text(
                            text = fromAccountName,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Text(
                        text = "To: ",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White,
                    )
                    Box (
                        modifier = Modifier.width(75.dp)
                    ){
                        Text(
                            text = toAccountName,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                if (!description.isNullOrBlank()) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.DomainVerification,
                            contentDescription = "Description",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = description,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color(0xFF969696),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}
