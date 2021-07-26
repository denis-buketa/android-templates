package denisbuketa.android.progressbar

data class ChallengeProgressViewModel(
    val progress: List<ChallengeProgress>
)

enum class ChallengeProgress {
    EMPTY,
    CORRECT,
    INCORRECT,
    NO_INFO
}