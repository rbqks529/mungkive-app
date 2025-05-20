package com.mungkive.application.models.tip

import com.mungkive.application.R

object TipDataFactory {
    fun getTips(): List<TipData> {
        return listOf(
            TipData(
                title = "규칙적인 산책은 필수입니다",
                image = R.drawable.tip1,
                content = "강아지에게 산책은 단순한 운동 이상의 의미를 가집니다. 외부 환경을 탐색하며 정신적 자극을 받고 스트레스도 해소됩니다. 특히 에너지가 많은 견종은 산책을 통해 에너지를 발산하지 않으면 문제 행동을 보일 수 있습니다. 매일 같은 시간에 산책을 나가면 강아지가 일과를 예측하고 안정감을 느낄 수 있습니다. 배변 습관 형성에도 도움이 되며, 반려인과의 유대감도 강화됩니다. 단, 날씨나 건강 상태에 따라 산책 강도와 시간은 조절해야 합니다."
            ),
            TipData(
                title = "올바른 식사 시간 유지",
                image = R.drawable.tip2,
                content = "반려견에게 일정한 시간에 식사를 제공하는 것은 건강한 식습관 형성에 매우 중요합니다. 일정하지 않은 식사 시간은 스트레스를 유발할 수 있으며, 소화 장애로 이어질 수 있습니다."
            ),
            TipData(
                title = "정기적인 건강 검진",
                image = R.drawable.tip3,
                content = "반려견의 건강을 위해 6개월 또는 1년에 한 번씩 동물병원을 방문해 건강 검진을 받는 것이 좋습니다. 예방접종, 구충제 투약 등은 정기적으로 챙겨야 합니다."
            )
        )
    }
}
