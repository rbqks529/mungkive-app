package com.mungkive.application.ui.profile

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter.State.Empty.painter
import com.mungkive.application.R
import com.mungkive.application.models.ProfileViewStatus
import com.mungkive.application.viewmodels.ApiTestViewModel

@Composable
fun AddProfileView(
    modifier: Modifier = Modifier,
    viewModel: ApiTestViewModel,
    onProfileRegistered: () -> Unit
) {
    val context = LocalContext.current

    val pickImageLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            uri ?: return@rememberLauncherForActivityResult
            // URI -> base64
            val bytes = context.contentResolver.openInputStream(uri)?.use { it.readBytes() }
            bytes?.let {
                val base64 = android.util.Base64.encodeToString(it, android.util.Base64.NO_WRAP)
                viewModel.profilePicture = base64
            }
        }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 40.dp)
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "애견 프로필을 등록해주세요",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(50.dp))

        val hasPhoto = viewModel.profilePicture.isNotBlank()

        if (hasPhoto) {
            val dataUri = "data:image/*;base64,${viewModel.profilePicture}"
            AsyncImage(
                model = dataUri,
                contentDescription = null,
                modifier = Modifier.size(180.dp)
                    .clip(CircleShape)
                    .clickable { viewModel.clearProfilePicture() }
            )
        } else {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(180.dp)
                    .clip(CircleShape)
                    .clickable { pickImageLauncher.launch("image/*") }
                    .background(Color(0xFFE5E5E5))
            ) {
                Text(
                    text = "+",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(80.dp))

        // 이름
        OutlinedTextField(
            value = viewModel.name,
            onValueChange = viewModel::onNameChange,
            label = { Text("이름*") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 56.dp),
            shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 견종
        OutlinedTextField(
            value = viewModel.breed,
            onValueChange = viewModel::onBreedChange,
            label = { Text("견종") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 56.dp),
            shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 생년
        OutlinedTextField(
            value = viewModel.age,
            onValueChange = viewModel::onAgeChange,
            label = { Text("생년") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 56.dp),
            shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "이름은 필수로 입력해야 합니다",
            fontSize = 12.sp,
            textAlign = TextAlign.Left,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                viewModel.editProfile { success ->
                    if (success) {
                        onProfileRegistered()
                    } else {
                        Toast.makeText(context, "프로필 등록 실패", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            shape = RoundedCornerShape(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3378F6),
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "완료", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}
