import com.example.helutales.Quiz
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class QuizRepository {

    private val firestore = FirebaseFirestore.getInstance()

    suspend fun getQuizzes(): List<Quiz> {
        // Replace "quizzes" with your actual Firestore collection name
        val querySnapshot = firestore.collection("quizzes").get().await()

        // Convert the query snapshot to a list of Quiz objects
        return querySnapshot.documents.map { document ->
            val quiz = document.toObject(Quiz::class.java)
            quiz ?: throw IllegalStateException("Failed to convert document to Quiz")
        }
    }
}
