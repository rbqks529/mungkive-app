data class NaverReverseGeocodeResponse(
    val results: List<Result>
) {
    data class Result(
        val name: String?,
        val code: Code?,
        val region: Region?,
        val land: Land?
    )

    data class Code(val id: String?, val type: String?, val mappingId: String?)

    data class Region(
        val area1: Area?,
        val area2: Area?,
        val area3: Area?,
        val area4: Area?
    )

    data class Area(val name: String?)

    data class Land(
        val name: String?, val number1: String?, val number2: String?,
        val type: String?,
        val addition0: AdditionInfo?,
        val addition1: AdditionInfo?,
        val addition2: AdditionInfo?
    )

    data class AdditionInfo(
        val type: String?,
        val value: String?
    )
}
