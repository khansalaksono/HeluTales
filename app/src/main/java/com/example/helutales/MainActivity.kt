import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.helutales.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Button click listener to start the quiz
        binding.startQuizButton.setOnClickListener {
            // Start the QuizActivity
            startActivity(Intent(this, QuizActivity::class.java))
        }
    }
}


//import android.os.Bundle
//import androidx.activity.viewModels
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.helutales.QuizAdapter
//import com.example.helutales.databinding.ActivityMainBinding
//
//class MainActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityMainBinding
//    private val quizViewModel: QuizViewModel by viewModels()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val adapter = QuizAdapter(emptyList()) { quiz ->
//            // Handle item click, e.g., navigate to QuizDetailActivity with the selected quiz
//        }
//
//        binding.recyclerView.layoutManager = LinearLayoutManager(this)
//        binding.recyclerView.adapter = adapter
//
//        // Observe changes in the list of quizzes
//        quizViewModel.quizzes.observe(this, { quizzes ->
//            adapter.updateQuizzes(quizzes)
//        })
//    }
//}
