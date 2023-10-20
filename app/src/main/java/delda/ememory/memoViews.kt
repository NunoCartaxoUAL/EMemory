package delda.ememory


import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.navigation.NavController
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp


@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun MemoScreen(navController: NavController, viewModel: MemosListModel, id : Int) {

    val argtitle = viewModel.memos.value.orEmpty().find { it.id == id }?.title ?: ""
    val argcontent = viewModel.memos.value.orEmpty().find { it.id == id }?.content ?: ""

    var title by remember { mutableStateOf(TextFieldValue(argtitle)) }
    var content by remember { mutableStateOf(TextFieldValue(argcontent)) }

    val context = LocalContext.current
    val isDarkTheme = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        context.resources.configuration.isNightModeActive
    } else {
        context.resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK == android.content.res.Configuration.UI_MODE_NIGHT_YES
    }

    val softPurpleColor = if (isDarkTheme) {
        Color(0xFF5F5969) // Dark Purple
    } else {
        Color(0xFFD1C4E9) // Light Soft Purple
    }

    val titleTextStyle = TextStyle.Default.copy(
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row {
            IconButton(
                onClick = {
                       if (title.text.isNotEmpty() || content.text.isNotEmpty()) {
                            if (id == 0) {
                                viewModel.saveNewMemo(context, title.text, content.text)
                            } else {
                                viewModel.updateMemo(context, title.text, content.text, id)
                            }
                    }

                    navController.navigate("memoList")
                },
                modifier = Modifier
                    .padding(2.dp)
                    .padding(top = 2.dp, start = 2.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            BasicTextField(
                value = title,
                onValueChange = { title = it},
                textStyle = titleTextStyle,
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 56.dp)
                    .padding(2.dp)
                    .background(softPurpleColor)


            )
        }


        BasicTextField(
            value = content,
            onValueChange = { content = it },
            textStyle = TextStyle.Default,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .heightIn(min = 500.dp)
                .padding(2.dp)
                .background(softPurpleColor)
        )


    }
}

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun MemoListScreen(navController: NavController, viewModel: MemosListModel) {

    val memos = viewModel.memos.value.orEmpty()
    // fake memos for testing
    /*val memos = listOf(
        Memo("title1", "content1", 1),
        Memo("title2", "content2", 2),
        Memo("title3", "content3", 3))
    */
    val context = LocalContext.current

    val isDarkTheme = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        context.resources.configuration.isNightModeActive
    } else {
        context.resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK == android.content.res.Configuration.UI_MODE_NIGHT_YES
    }

    val softPurpleColor = if (isDarkTheme) {
        Color(0xFF5F5969) // Dark Purple
    } else {
        Color(0xFFD1C4E9) // Light Soft Purple
    }
    
    Column() {
        LazyColumn {
            items(memos) { memo ->
                Row (
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier
                            .padding(2.dp)
                            .background(softPurpleColor)
                            .heightIn(min = 56.dp)
                            .weight(0.9f)
                            .clickable {

                                navController.navigate("memoScreen/id=${memo.id}")
                            }

                    ) {
                        Text(
                            text = memo.title,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            style = TextStyle.Default.copy(
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                color = Color.White
                            )
                        )
                    }
                    IconButton(
                        onClick = {
                            viewModel.deleteMemo(context,memo.id)
                            navController.navigate("memoList")
                        },
                        modifier = Modifier
                            .weight(0.1f)
                            .fillMaxHeight()

                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                

            }



        }
        IconButton(

            onClick = {
                navController.navigate("memoScreen/id=0")
            },
            modifier = Modifier
                .padding(2.dp)

        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "add",
                modifier = Modifier
                    .background(softPurpleColor)
            )
        }
    }
}

//make a preview for MemoScreen
@RequiresApi(Build.VERSION_CODES.R)
@Composable
@Preview(showBackground = true)
fun MemoScreenPreview() {
    //MemoScreen(navController = NavController(Activity()), viewModel = MemoModel())

    MemoListScreen(navController = NavController(Activity()), viewModel = MemosListModel())

}
