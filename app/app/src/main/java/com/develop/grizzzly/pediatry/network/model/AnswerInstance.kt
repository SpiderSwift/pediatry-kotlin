package com.develop.grizzzly.pediatry.network.model

class AnswerInstance(var correctAnswer: Int, var selectedAnswer: Int){
    companion object{
        var noAnswered = 0
        var correctAnswered = 1
        var incorrectAnswered = 2
    }
}
