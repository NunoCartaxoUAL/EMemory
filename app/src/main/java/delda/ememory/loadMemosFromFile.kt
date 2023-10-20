package delda.ememory

import android.content.Context
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

fun loadMemosFromFiles(context: Context): List<Memo> {
    //load all memos from files in the directory Ememo that have the extension .md
    val directory = File(context.getExternalFilesDir(null), "Ememo")
    if (!directory.exists() || !directory.isDirectory) {
        return emptyList()
    }

    val memos = mutableListOf<Memo>()
    val files = directory.listFiles()
    var id = 0

    if (files != null) {
        for (file in files) {
            println(file.name)
            if (file.name.endsWith(".md")) {
                id += 1
                //read memo from file
                val memo = makeMemoFromFile(file,id)
                if(memo.id == -1){
                    continue
                }
                memos.add(memo)
            }
        }
    }

    return memos
}

fun makeMemoFromFile(file: File, id: Int): Memo {
    //read the file and return a memo
    try {
        val reader = BufferedReader(FileReader(file))
        var line: String?
        var title = ""
        val content = StringBuilder()
        //read the file line by line, add it do the title , if the line is --- then the title is finished and the content starts
        while (reader.readLine().also { line = it } != null) {
            if (line == "---") {
                break
            }
            title += line
        }
        //read the content line by line
        while (reader.readLine().also { line = it } != null) {
            content.append(line)
            content.append("\n")
        }

        if (title.isNotEmpty() || content.isNotEmpty()) {
            return Memo(title, content.toString(), id)
        }

        reader.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return Memo("", "", -1)
}
