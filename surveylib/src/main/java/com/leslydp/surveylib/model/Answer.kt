package com.leslydp.surveylib.model

data class Answer(
    val ans: Array<String> = arrayOf<String>()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Answer

        if (!ans.contentEquals(other.ans)) return false

        return true
    }

    override fun hashCode(): Int {
        return ans.contentHashCode()
    }
}