
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mungkive.application.R
import com.mungkive.application.models.ProfileViewStatus
import com.mungkive.application.viewmodels.ApiTestViewModel
import com.mungkive.application.viewmodels.ProfileViewModel

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun ProfileView(
    modifier: Modifier = Modifier,
    viewModel: ApiTestViewModel,
    profileViewModel: ProfileViewModel = ProfileViewModel(),
    onProfileRegistered: () -> Unit
) {
    var nameText by remember { mutableStateOf("") }
    var dogTypeText by remember { mutableStateOf("") }
    var yearText by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        profileViewModel.fetchProfileData()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 40.dp).padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        if (profileViewModel.status.value == ProfileViewStatus.REGISTER) {
            Text(
                text = "애견 프로필을 등록해주세요",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "${profileViewModel.profile.value.name}의 프로필",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Left
                )

                if (profileViewModel.status.value == ProfileViewStatus.EDIT) {
                    Box(
                        modifier = Modifier.clickable {
                            profileViewModel.setStatus(ProfileViewStatus.VIEW)
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
                            profileViewModel.setStatus(ProfileViewStatus.EDIT)
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
        }

        Spacer(modifier = Modifier.height(50.dp))

        Image(
            painter = painterResource(R.drawable.dummyprofile),
            contentDescription = "",
            modifier = Modifier.clip(CircleShape).width(180.dp).height(180.dp)
        )

        Spacer(modifier = Modifier.height(80.dp))

        // 이름
        OutlinedTextField(
            value = nameText,
            onValueChange = { nameText = it },
            label = { Text("이름*") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(8.dp),
            enabled = (profileViewModel.status.value != ProfileViewStatus.VIEW)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 견종
        OutlinedTextField(
            value = dogTypeText,
            onValueChange = { dogTypeText = it },
            label = { Text("견종") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(8.dp),
            enabled = (profileViewModel.status.value != ProfileViewStatus.VIEW)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 생년
        OutlinedTextField(
            value = yearText,
            onValueChange = { yearText = it },
            label = { Text("생년") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(8.dp),
            enabled = (profileViewModel.status.value != ProfileViewStatus.VIEW)
        )

        if (profileViewModel.status.value != ProfileViewStatus.VIEW) {
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
        if (profileViewModel.status.value == ProfileViewStatus.EDIT) {
            Button(
                onClick = {
                    // TODO: Profile Update Process
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
