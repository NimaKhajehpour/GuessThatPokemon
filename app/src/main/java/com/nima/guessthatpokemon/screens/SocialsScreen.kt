package com.nima.guessthatpokemon.screens

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nima.guessthatpokemon.R

@Composable
fun SocialsScreen(
    navController: NavController
){

    val context = LocalActivity.current

    Box{

        Column (
            modifier = Modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = stringResource(R.string.guessthatpokemon),
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = stringResource(R.string.community_prompt),
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        Column (
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                IconButton(
                    onClick = {
                        val browserIntent = Intent(Intent.ACTION_VIEW)
                        browserIntent.data = Uri.parse("https://www.github.com/NimaKhajehpour")
                        context!!.startActivity(browserIntent)
                    },
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 32.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.github_mark),
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp),
                    )
                }

                IconButton(
                    onClick = {
                        val browserIntent = Intent(Intent.ACTION_VIEW)
                        browserIntent.data =
                            Uri.parse("https://t.me/+bwYZeynt5JNkMDdk")
                        context!!.startActivity(browserIntent)
                    },
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 32.dp),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.telegram_logo),
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp),
                    )
                }

                IconButton(
                    onClick = {
                        val browserIntent = Intent(Intent.ACTION_VIEW)
                        browserIntent.data =
                            Uri.parse("https://discord.gg/6fq6MvX3fG")
                        context!!.startActivity(browserIntent)
                    },
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 32.dp),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.discord_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp),
                    )
                }
            }
        }
    }
}