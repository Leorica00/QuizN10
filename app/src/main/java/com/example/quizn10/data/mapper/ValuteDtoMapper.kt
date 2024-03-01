package com.example.quizn10.data.mapper

import com.example.quizn10.data.model.ValuteDto
import com.example.quizn10.domain.model.GetValute

fun ValuteDto.toDomain() = GetValute(
    course = course
)