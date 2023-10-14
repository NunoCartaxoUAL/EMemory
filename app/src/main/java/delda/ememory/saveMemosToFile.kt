package delda.ememory

import android.content.Context
import java.io.File
import java.io.FileWriter
import java.io.IOException

fun saveMemosToFile(context: Context,memos: List<Memo>): Boolean {
    val fileName = "memos.md"
    val directory = File(context.getExternalFilesDir(null), "Ememo")
    //print the path of the directory as text
    print(directory)
    if (!directory.exists()) {
        if (!directory.mkdirs()) {
            // Directory creation failed, handle the error
            print("Directory creation failed")
            return false
        }
        print("Directory creation unfailed")
    }

    val file = File(directory, fileName)

    try {
        val writer = FileWriter(file)
        for (memo in memos) {
            val memoText = "- ${memo.title}\n${memo.content}\n\n"
            writer.append(memoText)
        }
        writer.flush()
        writer.close()
        return true
    } catch (e: IOException) {
        e.printStackTrace()
        return false
    }
}