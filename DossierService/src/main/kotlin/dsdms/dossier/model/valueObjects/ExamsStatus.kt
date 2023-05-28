package dsdms.dossier.model.valueObjects
import dsdms.dossier.model.valueObjects.PracticalExamState.*
import dsdms.dossier.model.valueObjects.TheoreticalExamState.PASSED
import dsdms.dossier.model.valueObjects.TheoreticalExamState.TO_DO
import kotlinx.serialization.Serializable

@Serializable
enum class PracticalExamState{
    TO_DO, FIRST_PROVISIONAL_LICENCE_INVALID, SECOND_PROVISIONAL_LICENCE_INVALID, PASSED
}
@Serializable
enum class TheoreticalExamState{
    TO_DO, PASSED
}

@Serializable
data class ExamsStatus(
    val theoreticalExamState: TheoreticalExamState = TO_DO,
    val practicalExamState: PracticalExamState = PracticalExamState.TO_DO
) {

    fun registerProvisionalLicenceInvalidation(): ExamsStatus{
        if(theoreticalExamState != PASSED){
            throw IllegalStateException("Theoretical exam must be passed before invalidating provisional licence")
        }
        return when(practicalExamState){
            PracticalExamState.TO_DO ->
                setNewPracticalExamState(FIRST_PROVISIONAL_LICENCE_INVALID)
            FIRST_PROVISIONAL_LICENCE_INVALID -> setNewPracticalExamState(SECOND_PROVISIONAL_LICENCE_INVALID)
            else -> throw IllegalStateException("The provisional licence can not be invalidated tree or more times")
        }
    }

    fun registerPracticalExamPassed(): ExamsStatus{
        if(theoreticalExamState != PASSED){
            throw IllegalStateException("Theoretical exam must be passed before practical exam")
        }
        return when(practicalExamState){
            PracticalExamState.TO_DO -> setNewPracticalExamState(PracticalExamState.PASSED)
            FIRST_PROVISIONAL_LICENCE_INVALID -> setNewPracticalExamState(PracticalExamState.PASSED)
            else -> throw IllegalStateException("Exam can not be passed if the current state is $practicalExamState")
        }
    }

    fun registerTheoreticalExamPassed(): ExamsStatus{
        return when(theoreticalExamState){
            PASSED -> throw IllegalStateException("Theoretical exam is already passed")
            else -> this.copy(theoreticalExamState = PASSED)
        }
    }

    private fun setNewPracticalExamState(newState: PracticalExamState): ExamsStatus{
        if(isStateOrderConsented(newState).not()){
            throw IllegalStateException("The state $newState can not be after $practicalExamState")
        }
        return when(newState){
            FIRST_PROVISIONAL_LICENCE_INVALID -> this.copy(practicalExamState = newState, theoreticalExamState = TO_DO)
            else -> this.copy(practicalExamState = newState)
        }
    }

    private fun isStateOrderConsented(newState: PracticalExamState): Boolean {
        return when (practicalExamState) {
            PracticalExamState.TO_DO -> newState == FIRST_PROVISIONAL_LICENCE_INVALID || newState == PracticalExamState.PASSED
            FIRST_PROVISIONAL_LICENCE_INVALID -> newState == SECOND_PROVISIONAL_LICENCE_INVALID || newState == PracticalExamState.PASSED
            else -> false
        }
    }
}