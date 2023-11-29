import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.helutales.databinding.ActivityQuizBinding

class QuizActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuizBinding
    private val viewModel: QuizViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Observe changes in the quiz state
        viewModel.quizState.observe(this, Observer { state ->
            when (state) {
                is QuizState.ShowQuestion -> showQuestion(state.quiz)
                is QuizState.ShowResult -> showResult(state.score)
                else -> {}
            }
        })

        // Handle user clicks on options
        binding.option1.setOnClickListener { viewModel.onOptionSelected(1) }
        binding.option2.setOnClickListener { viewModel.onOptionSelected(2) }
        binding.option3.setOnClickListener { viewModel.onOptionSelected(3) }
        binding.option4.setOnClickListener { viewModel.onOptionSelected(4) }

        // Start the quiz when the activity is created
        viewModel.startQuiz()
    }

    // Replace with your actual logic to display a question
    private fun showQuestion(quiz: Quiz) {
        // Update UI to display the current question
        binding.questionTextView.text = quiz.description
        binding.option1.text = quiz.options[0]
        binding.option2.text = quiz.options[1]
        binding.option3.text = quiz.options[2]
        binding.option4.text = quiz.options[3]

        // Additional logic to handle UI changes for question display
        // ...
    }

    // Replace with your actual logic to display the result
    private fun showResult(score: Int) {
        // Update UI to display the quiz result
        binding.resultTextView.text = "Your score: $score"

        // Additional logic to handle UI changes for result display
        // ...
    }
}


//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.ViewModelProvider
//import com.example.helutales.databinding.ActivityQuizBinding
//import com.google.firebase.firestore.FirebaseFirestore
//
//class QuizActivity : AppCompatActivity() {
//
//    private lateinit var quizViewModel: QuizViewModel
//    private lateinit var binding: ActivityQuizBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityQuizBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        // Initialize ViewModel
//        quizViewModel = ViewModelProvider(this).get(QuizViewModel::class.java)
//
//        // Retrieve the quiz from Firestore based on the selected quiz
//        // (Assuming you have a way to get the selected quiz title)
//        val selectedQuizTitle = "YourSelectedQuizTitle"
//        retrieveQuizFromFirestore(selectedQuizTitle)
//
//        // Initialize UI components and set up listeners
//        initializeUI()
//    }
//
//    private fun retrieveQuizFromFirestore(quizTitle: String) {
//        // Use Firebase Firestore to retrieve the quiz based on the title
//        // Populate the 'quiz' variable in the ViewModel
//        val db = FirebaseFirestore.getInstance()
//        val quizRef = db.collection("quizzes").document(quizTitle)
//
//        quizRef.get().addOnSuccessListener { document ->
//            if (document != null && document.exists()) {
//                val quiz = document.toObject(Quiz::class.java)
//                quiz?.let {
//                    quizViewModel.setQuiz(it)
//                    displayQuestion()
//                }
//            } else {
//                // Handle the case where the quiz is not found
//            }
//        }
//    }
//
//    private fun initializeUI() {
//        // Set up UI components and set listeners
//        // Observe LiveData from ViewModel
//        quizViewModel.quiz.observe(this, { quiz ->
//            // Update UI with the current quiz
//            // (You may need to update your UI components accordingly)
//        })
//
//        quizViewModel.currentQuestionIndex.observe(this, { index ->
//            // Update UI with the current question index
//            // (You may need to update your UI components accordingly)
//        })
//
//        quizViewModel.userAnswers.observe(this, { answers ->
//            // Update UI with the user's answers
//            // (You may need to update your UI components accordingly)
//        })
//
//        // Set up Next button click listener
//        binding.nextButton.setOnClickListener {
//            // Check if the user has selected an option
//            val selectedOption = getSelectedOption()
//            if (selectedOption != -1) {
//                // Save the user's answer and display the next question
//                quizViewModel.addUserAnswer(selectedOption)
//                quizViewModel.incrementQuestionIndex()
//
//                if (quizViewModel.currentQuestionIndex.value ?: 0 < quizViewModel.quiz.value?.getTotalQuestions() ?: 0) {
//                    displayQuestion()
//                } else {
//                    // Display the result page
//                    displayResult()
//                }
//            } else {
//                // Display a message asking the user to select an option
//            }
//        }
//    }
//
//    private fun displayQuestion() {
//        // Display the current question on the UI
//        val currentQuestion = quizViewModel.getCurrentQuestion()
//
//        // Update UI components with the current question details
//        // (You may need to update your UI components accordingly)
//        binding.questionTextView.text = currentQuestion?.description
//        binding.option1Button.text = currentQuestion?.options?.get(0)
//        binding.option2Button.text = currentQuestion?.options?.get(1)
//        binding.option3Button.text = currentQuestion?.options?.get(2)
//        binding.option4Button.text = currentQuestion?.options?.get(3)
//
//        // Reset UI for a new question (clear selected option, hide explanation, etc.)
//        // (You might want to add the necessary UI components in your layout)
//        resetUIForNewQuestion()
//    }
//
//    private fun resetUIForNewQuestion() {
//        // Reset UI components for a new question
//        // (For example, clear the selected option, hide the explanation, etc.)
//    }
//
//    private fun getSelectedOption(): Int {
//        // Implement this method to get the index of the selected option
//        // Return -1 if no option is selected
//        // (You might want to add the necessary UI components in your layout)
//    }
//
//    private fun displayResult() {
//        // Calculate points and display the result page
//        // (You can implement a separate ResultActivity or handle it in this activity)
//        val totalPoints = quizViewModel.calculateTotalPoints(quizViewModel.userAnswers.value ?: emptyList())
//        // Start the ResultActivity or display the result in the current activity
//    }
//}
