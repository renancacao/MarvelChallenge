package com.rcacao.marvelchallenge.domain.usecases

interface UseCase<I, O> {
    suspend operator fun invoke(requestData: I): O
}