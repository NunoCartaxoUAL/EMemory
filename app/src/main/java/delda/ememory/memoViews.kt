package delda.ememory

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoScreen(navController: NavController, viewModel: MemoModel) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    val context = LocalContext.current
    val requestCode = 1001 // You can use any unique integer value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") }
        )

        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Memo") }
        )

        Button(
            onClick = {
                // Check if the app has permission to write to external storage
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // Permission is not granted, request the permission
                    ActivityCompat.requestPermissions(
                        context as Activity,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        requestCode
                    )
                } else {
                    // Permission has already been granted, save the memo
                    viewModel.saveMemo(context,title, content)
                    // Print the memo and the return value of saveMemosToFile
                    println("Memo1")
                    println("Memo: $title Content: $content")
                    // Reset the text fields
                    title = ""
                    content = ""
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)

        ) {
            Text("Save Memo")
        }

        // Button to navigate to MemoListScreen
        Button(
            onClick = {
                navController.navigate("memoList")
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Show Memos")
        }
    }
}

@Composable
fun MemoListScreen(navController: NavHostController, viewModel: MemoModel) {
    val memos = viewModel.memos.value.orEmpty()
    Column {
        Button(
            onClick = {
                // Optionally, navigate to MemoListScreen after saving the memo
                navController.navigate("memoScreen")
            }
        ) {
            Text("Back")
        }
        LazyColumn {
            items(memos) { memo ->
                Text(
                    text = "Title: ${memo.title}\nContent: ${memo.content}",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
