package com.mungkive.application.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mungkive.application.R
import com.mungkive.application.viewmodels.ApiTestViewModel

@Composable
fun WelcomeView(
    modifier: Modifier = Modifier,
    viewModel: ApiTestViewModel,
    onLoginClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(
            modifier = Modifier.weight(1f)
        )

        // 로고 이미지
        Image(
            painterResource(R.drawable.dummylogo),
            contentDescription = ""
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 메시지
        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("멍카이브")
                }
                append("에 오신 것을\n환영합니다!")
            },
            modifier = Modifier.fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally),
            fontSize = 28.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.weight(1f))

        // 로그인 버튼
        Button(
            onClick = {
                // TODO: Login Process
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3378F6), // 파란색
                contentColor = Color.White // 흰색 텍스트
            ),
            shape = RoundedCornerShape(50.dp), // 둥근 모서리
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(50.dp)
        ) {
            Text(
                text = "로그인",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 회원 가입 버튼
        Button(
            onClick = {
                // TODO: Register Process
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF8A8A8E), // 회색
                contentColor = Color.White // 흰색 텍스트
            ),
            shape = RoundedCornerShape(50.dp), // 둥근 모서리
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(50.dp)
        ) {
            Text(
                text = "회원가입",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}
