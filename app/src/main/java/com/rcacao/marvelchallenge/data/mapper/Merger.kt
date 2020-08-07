package com.rcacao.marvelchallenge.data.mapper

interface Merger<I, L, O> : Mapper<I, O> {
    fun mapAndMerge(input: I, list: List<L>): O

}