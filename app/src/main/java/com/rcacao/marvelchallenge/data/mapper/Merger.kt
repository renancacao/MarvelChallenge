package com.rcacao.marvelchallenge.data.mapper

interface Merger<I, L, O> {
    fun mapAndMerge(input: I, list: List<L>?): O
}