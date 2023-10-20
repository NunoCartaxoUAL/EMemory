package delda.ememory

import android.content.Context
import java.io.File

fun deleteMemoFile(context: Context, title: String){
    val fileName = "${title.replace("[^a-zA-Z0-9]".toRegex(), "")}_memo.md"
    val directory = File(context.getExternalFilesDir(null), "Ememo")
    val file = File(directory, fileName)
    if(file.exists()){
        file.delete()
    }
}