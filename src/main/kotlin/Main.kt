import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.FileWriter
import java.io.IOException

fun getWjxPage(): Document {
    val ua =
        "Mozilla/5.0 (Linux; Android............EBID/8237 MicroMessenger/8.0.38.2400(0x28002652) WeChat/arm64 Weixin NetType/WIFI Language/zh_CN ABI/arm64 MiniProgramEnv/android"
    val connect = Jsoup.connect("https://ks.wjx.top/vm/xxxx.aspx")
    connect.header("User-Agent", ua)
    return connect.get()
}

fun main() {
    val count = 200 // 测试次数
    val allQuestion = mutableListOf<String>()
    for (i in 1..count) {

        val s = Jsoup.parse(getWjxPage().text()).text()
        val q = Question(s).qList // all question&answer list
        println("循环到第 $i 次")
        q.forEach {
            if (it !in allQuestion) allQuestion.add(it)
        }
    }
    val result = allQuestion.toSet().toList()
    println(result)
    println("共有 ${result.size} 道题。")
    writeToFile(result)
}

fun writeToFile(l: List<String>) {
    var count = 1
    val fw = FileWriter("out/${l.size}-${System.currentTimeMillis()}.txt", true)
    for (i in l) {
        try {
            fw.write("${count}. $i\n")
        } catch (e: IOException) {
            e.printStackTrace()
        }
        count++
    }
    fw.close()
}
