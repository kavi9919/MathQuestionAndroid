package com.example.mathapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mathapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val questions = listOf(
        "1. What is the result of 20 + 77?",
        "2. Is 20 + 77 = 19? (True or False)",
        "3. What is the result of 50 - 30?",
        "4. Is 15 * 3 = 45? (True or False)",
        "5. What is the result of 100 / 5?"
    )
    private val answers = listOf(97, false, 20, true, 20)
    private var currentQuestionIndex = 0
    private var score=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showQuestion()

        binding.checkAnswerButton.setOnClickListener {
            checkAnswer()
        }

        binding.resetButton.setOnClickListener {
            resetAnswer()
        }

        binding.nextButton.setOnClickListener {
            showNextQuestion()
        }
    }

    private fun showQuestion() {
        binding.questionTextView.text = questions[currentQuestionIndex]
        binding.answerEditText.text.clear()
        binding.resultTextView.visibility = TextView.GONE
        binding.nextButton.visibility = Button.GONE
    }

    private fun checkAnswer() {
        val userAnswer = binding.answerEditText.text.toString()
        val correctAnswer = answers[currentQuestionIndex]

        val isCorrect = if (correctAnswer is Boolean) {
            val userAnswerBoolean = userAnswer.equals("true", ignoreCase = true) || userAnswer == "1"
            correctAnswer == userAnswerBoolean
        } else {
            correctAnswer.toString() == userAnswer
        }

        if (isCorrect) {
            score++
            binding.resultTextView.text = "Correct!"
            binding.resultTextView.setTextColor(resources.getColor(android.R.color.holo_green_dark))
        } else {
            binding.resultTextView.text = "Wrong! The correct answer is $correctAnswer."
            binding.resultTextView.setTextColor(resources.getColor(android.R.color.holo_red_dark))
        }
        binding.resultTextView.visibility = TextView.VISIBLE
        binding.nextButton.visibility = Button.VISIBLE
    }

    private fun resetAnswer() {
        binding.answerEditText.text.clear()
        binding.resultTextView.visibility = TextView.GONE
    }

    private fun showNextQuestion() {
        currentQuestionIndex++
        if (currentQuestionIndex < questions.size) {
            showQuestion()
        } else {
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("SCORE", score)
            intent.putExtra("TOTAL_QUESTIONS", questions.size)
            startActivity(intent)
            finish()
        }
    }
}
