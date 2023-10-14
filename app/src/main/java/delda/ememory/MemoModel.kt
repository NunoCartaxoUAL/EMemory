package delda.ememory

import android.content.Context

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MemoModel : ViewModel() {
    private val _memos = MutableLiveData<List<Memo>>()

    // Expose LiveData to observe changes
    val memos: LiveData<List<Memo>> = _memos

    init {
        //initialize memos
        _memos.value = emptyList()

    }
    //read memos from file
    fun readMemo(context: Context) {
        print("sdasdadsadsadadsadads1a")
        val memosFromFile = loadMemosFromFile(context)
        print("sdasdadsadsadadsadadsa2")
        val currentMemos = _memos.value.orEmpty().toMutableList()
        print("sdasdadsadsadadsadadsa3")
        currentMemos.addAll(memosFromFile)
        _memos.value = currentMemos
    }

    fun saveMemo(context: Context, title: String, content: String) {
        val memo = Memo(title, content)
        val currentMemos = _memos.value.orEmpty().toMutableList()
        currentMemos.add(memo)
        _memos.value = currentMemos
        //save all memos to file
        //print return value of saveMemosToFile
        println("Memo2")
        println(saveMemosToFile(context,memos.value.orEmpty()))
    }
}


data class Memo(val title: String, val content: String)
