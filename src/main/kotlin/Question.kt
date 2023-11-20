class Question(private val text: String) {
    val qList: List<String> = processQuestionStr()
    private fun processQuestionStr(): List<String> {
        val page = convertToFullWidth(text)
            .replace(" 二、单选题 三、多选题", "")
            .split(" 评价对象得分")[0]
                .split(" * ")
            .toMutableList()
        page.removeAt(0)
        return page.map {
            it.replace(Regex("^\\d+\\.\\s*"), "")
        }
    }

    private fun convertToFullWidth(input: String): String {
        val fullWidth = StringBuilder()
        input.forEach { char ->
            fullWidth.append(
                when (char) {
                    '?' -> '？'
                    '!' -> '！'
                    ',' -> '，'
                    '(' -> '（'
                    ')' -> '）'
                    ':' -> '：'
                    ';' -> '；'
                    '"' -> '“'
                    else -> char
                }
            )
        }
        return fullWidth.toString()
    }
}
