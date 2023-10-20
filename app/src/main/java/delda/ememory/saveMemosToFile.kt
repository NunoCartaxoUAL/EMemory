package delda.ememory

import android.content.Context
import java.io.File
import java.io.FileWriter
import java.io.IOException



fun saveMemoToFile(context: Context,memo: Memo): Boolean {
    //save memo to file with name (title)_memo.md , remove all spaces and special characters from title
    val fileName = "${memo.title.replace("[^a-zA-Z0-9]".toRegex(), "")}_memo.md"
    val directory = File(context.getExternalFilesDir(null), "Ememo")

    if (!directory.exists()) {
        if (!directory.mkdirs()) {
            print("Directory creation failed")
            return false
        }
        print("Directory creation unfailed")
    }

    val file = File(directory, fileName)

    try {
        val writer = FileWriter(file)
        val memoText = "${memo.title}\n---\n${memo.content}"
        writer.write(memoText)
        writer.flush()
        writer.close()
        return true
    } catch (e: IOException) {
        e.printStackTrace()
        return false
    }
}