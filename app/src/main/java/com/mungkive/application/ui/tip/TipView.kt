package com.mungkive.application.ui.tip

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mungkive.application.R

@Composable
fun TipView(
    modifier: Modifier = Modifier,
    title: String,
    image: Int,
    content: String
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp), // 둥근 모서리
        border = BorderStroke(1.dp, Color.Gray), // 회색 테두리
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Image(
            painter = painterResource(image),
            contentDescription = "",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )

        Text(
            text = content,
            fontSize = 10.sp,
            modifier = Modifier.padding(10.dp)
        )
    }
}

@Preview
@Composable
private fun TipViewPreview() {
    TipView(
        title = "규칙적인 산책은 필수입니다",
        image = R.drawable.tip1,
        content = "강아지에게 산책은 단순한 운동 이상의 의미를 가집니다. 외부 환경을 탐색하며 정신적 자극을 받고 스트레스도 해소됩니다. 특히 에너지가 많은 견종은 산책을 통해 에너지를 발산하지 않으면 문제 행동을 보일 수 있습니다. 매일 같은 시간에 산책을 나가면 강아지가 일과를 예측하고 안정감을 느낄 수 있습니다. 배변 습관 형성에도 도움이 되며, 반려인과의 유대감도 강화됩니다. 단, 날씨나 건강 상태에 따라 산책 강도와 시간은 조절해야 합니다."
    )
}
