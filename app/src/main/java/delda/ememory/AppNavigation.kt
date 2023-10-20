package delda.ememory

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument


@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()
    val viewModel: MemosListModel = viewModel()
    val context = LocalContext.current
    viewModel.readMemos(context)

    NavHost(navController, startDestination = "memoList") {
        composable("memoList") { MemoListScreen(navController,viewModel) }
        composable(
            route = "memoScreen/id={id}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            MemoScreen(navController, viewModel, id)
        }
    }
}
