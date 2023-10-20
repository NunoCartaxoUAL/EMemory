package delda.ememory

import android.content.Context

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MemosListModel : ViewModel() {
    private val _memos = MutableLiveData<List<Memo>>()
    private var _id = 0

    val memos: LiveData<List<Memo>> = _memos

    init {
        _memos.value = emptyList()

    }
    //read memos from file
    fun readMemos(context: Context) {
        val memosFromFile = loadMemosFromFiles(context)
        val currentMemos = _memos.value.orEmpty().toMutableList()
        currentMemos.addAll(memosFromFile)
        _memos.value = currentMemos
        _id = currentMemos.size+1
    }
    //save new memo
    fun saveNewMemo(context: Context, title: String, content: String) {


        val memo = Memo(title, content, _id)
        val currentMemos = _memos.value.orEmpty().toMutableList()
        currentMemos.add(memo)
        currentMemos.sortBy { it.id }
        _memos.value = currentMemos
        _id += 1
        saveMemoToFile(context, memo)
    }
    //update memo
    fun updateMemo(context: Context, newTitle: String, newContent: String, id: Int) {
        val currentMemos = _memos.value.orEmpty().toMutableList()
        val memo = currentMemos.find { it.id == id }
        if (memo != null) {
            currentMemos.remove(memo)
            val updatedMemo = Memo(newTitle, newContent, id)
            currentMemos.add(updatedMemo)
            currentMemos.sortBy { it.id }
            _memos.value = currentMemos
            saveMemoToFile(context, memo)
        }
    }
    //delete memo
    fun deleteMemo(context: Context,id: Int) {
        val currentMemos = _memos.value.orEmpty().toMutableList()
        val memo = currentMemos.find { it.id == id }
        if (memo != null) {
            currentMemos.remove(memo)
            currentMemos.sortBy { it.id }
            _memos.value = currentMemos
            deleteMemoFile(context, memo.title)

        }
    }


}


data class Memo(val title: String, val content: String,val id : Int)
