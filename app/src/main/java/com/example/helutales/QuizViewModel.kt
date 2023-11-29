import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel() {
    // LiveData to observe changes in the quiz state
    private val _quizState = MutableLiveData<QuizState>()
    val quizState: LiveData<QuizState>
        get() = _quizState

    // Replace with your actual Firestore data retrieval logic
    private val quizData: List<Quiz> = getQuizDataFromFirestore()

    // Current question index
    private var currentQuestionIndex = 0
    private var totalScore = 0

    // Function to handle user selection
    fun onOptionSelected(option: Int) {
        val currentQuestion = quizData[currentQuestionIndex]
        if (option == currentQuestion.answer) {
            totalScore++
        }
        showNextQuestion()
    }

    // Function to move to the next question
    private fun showNextQuestion() {
        currentQuestionIndex++
        if (currentQuestionIndex < quizData.size) {
            _quizState.value = QuizState.ShowQuestion(quizData[currentQuestionIndex])
        } else {
            _quizState.value = QuizState.ShowResult(totalScore)
        }
    }

    // Call this method to start the quiz
    fun startQuiz() {
        currentQuestionIndex = 0
        totalScore = 0
        _quizState.value = QuizState.ShowQuestion(quizData[currentQuestionIndex])
    }
}

// Sealed class to represent different states of the quiz
sealed class QuizState {
    data class ShowQuestion(val quiz: Quiz) : QuizState()
    data class ShowResult(val score: Int) : QuizState()
}

// Replace with your actual data model for a quiz
data class Quiz(
    val description: String,
    val answer: Int,
    val options: List<String>,
    val explanation: String
)

//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.example.helutales.Quiz
//import com.google.firebase.firestore.FirebaseFirestore
//import com.google.firebase.firestore.QuerySnapshot
//
//class QuizViewModel : ViewModel() {
//    private val _quizzes = MutableLiveData<List<Quiz>>()
//    val quizzes: LiveData<List<Quiz>>
//        get() = _quizzes
//
//    init {
//        // Load quizzes when the ViewModel is initialized
//        loadQuizzes()
//    }
//
//    private fun loadQuizzes() {
//        val db = FirebaseFirestore.getInstance()
//        val quizzesCollection = db.collection("quizzes")
//
//        quizzesCollection.get()
//            .addOnSuccessListener { querySnapshot ->
//                val quizList = mutableListOf<Quiz>()
//                for (document in querySnapshot.documents) {
//                    val quiz = document.toObject(Quiz::class.java)
//                    quiz?.let {
//                        quizList.add(it)
//                    }
//                }
//                _quizzes.value = quizList
//            }
//            .addOnFailureListener { e ->
//                // Handle the error
//            }
//    }
//}
