package delda.ememory

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController



@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()
    val viewModel: MemoModel = viewModel()
    val context = LocalContext.current
    viewModel.readMemo(context)

    NavHost(navController, startDestination = "memoList") {
        composable("memoList") { MemoListScreen(navController,viewModel) }
        composable("memoScreen") { MemoScreen(navController, viewModel ) }
    }
}
