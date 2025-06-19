
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.mungkive.application.viewmodels.ApiTestViewModel

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun ProfileView(
    modifier: Modifier = Modifier,
    viewModel: ApiTestViewModel
) {
    val context = LocalContext.current
    var isEditing by remember { mutableStateOf(false) }

    val pickImageLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            uri ?: return@rememberLauncherForActivityResult
            // URI -> base64
            val bytes = context.contentResolver.openInputStream(uri)?.use { it.readBytes() }
            bytes?.let {
                val base64 = android.util.Base64.encodeToString(it, android.util.Base64.NO_WRAP)
                viewModel.updateProfilePicture(base64)
            }
        }

    LaunchedEffect(Unit) {
        viewModel.getProfile()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 40.dp).padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "${viewModel.name}의 프로필",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left
            )

            if (isEditing) {
                Box(
                    modifier = Modifier.clickable {
                        viewModel.getProfile()
                        isEditing = false
                    }.padding(vertical = 16.dp).padding(start = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "취소",
                        fontSize = 16.sp
                    )
                }
            } else {
                Box(
                    modifier = Modifier.clickable {
                        isEditing = true
                    }.padding(vertical = 16.dp).padding(start = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "수정",
                        fontSize = 16.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(50.dp))

        val imageModel: Any? = when {
            viewModel.profilePictureBase64.isNotBlank() ->
                "data:image/*;base64,${viewModel.profilePictureBase64}"
            viewModel.profilePictureUrl.isNotBlank() ->
                viewModel.profilePictureUrl
            else -> null
        }

        if (imageModel != null) {
            AsyncImage(
                model = imageModel,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = if (isEditing) {
                    Modifier.size(180.dp)
                        .clip(CircleShape)
                        .clickable { viewModel.clearProfilePicture() }
                } else {
                    Modifier.size(180.dp)
                        .clip(CircleShape)
                }
            )
        } else {
            Box(
                contentAlignment = Alignment.Center,
                modifier = if (isEditing) {
                    Modifier.size(180.dp)
                        .clip(CircleShape)
                        .clickable {
                            if (isEditing) {
                                pickImageLauncher.launch("image/*")
                            }
                        }
                        .background(Color(0xFFE5E5E5))
                } else {
                    Modifier.size(180.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE5E5E5))
                }
            ) {
                if (isEditing) {
                    Text(
                        text = "+",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 40.sp
                    )
                }
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
            shape = RoundedCornerShape(8.dp),
            enabled = (isEditing)
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
            shape = RoundedCornerShape(8.dp),
            enabled = (isEditing)
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
            shape = RoundedCornerShape(8.dp),
            enabled = (isEditing)
        )

        if (isEditing) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "이름은 필수로 입력해야 합니다",
                fontSize = 12.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // 수정 완료 버튼
        if (isEditing) {
            Button(
                onClick = {
                    viewModel.editProfile() { success ->
                        if (success) {
                            viewModel.getProfile()
                            isEditing = false
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
                Text(text = "수정 완료", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}
