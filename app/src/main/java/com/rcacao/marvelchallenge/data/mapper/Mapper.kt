package com.rcacao.marvelchallenge.data.mapper

interface Mapper<I, O> {
    fun map(input: I): O
}
