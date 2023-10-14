package delda.ememory

import android.content.Context
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

fun loadMemosFromFile(context: Context): List<Memo> {
    val fileName = "memos.md"
    val directory = File(context.getExternalFilesDir(null), "Ememo")

    if (!directory.exists() || !directory.isDirectory) {
        return emptyList()
    }

    val file = File(directory, fileName)

    if (!file.exists()) {
        return emptyList()
    }

    val memos = mutableListOf<Memo>()
    try {
        val reader = BufferedReader(FileReader(file))
        var line: String?
        var title = ""
        var content = StringBuilder()

        while (reader.readLine().also { line = it } != null) {
            if (line!!.startsWith("- ")) {
                if (title.isNotEmpty() && content.isNotEmpty()) {
                    memos.add(Memo(title, content.toString()))
                }
                val memoText = line!!.substring(2).trim()
                title = memoText
                content = StringBuilder()
            } else {
                content.append(line).append("\n")
            }
        }

        if (title.isNotEmpty() && content.isNotEmpty()) {
            memos.add(Memo(title, content.toString()))
        }

        reader.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return memos
}