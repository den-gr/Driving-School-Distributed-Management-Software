package dsdms.dossier.model.valueObjects
import dsdms.dossier.model.valueObjects.PracticalExamState.*
import dsdms.dossier.model.valueObjects.TheoreticalExamState.DONE
import dsdms.dossier.model.valueObjects.TheoreticalExamState.TO_DO
import kotlinx.serialization.Serializable

@Serializable
enum class PracticalExamState{
    NOT_DONE, FIRST_PROVISIONAL_LICENCE_INVALID, SECOND_PROVISIONAL_LICENCE_INVALID, PASSED
}
@Serializable
enum class TheoreticalExamState{
    TO_DO, DONE
}

@Serializable
data class ExamsProgress(
    val theoreticalExamState: TheoreticalExamState = TO_DO,
    val practicalExamState: PracticalExamState = NOT_DONE) {

    fun registerProvisionalLicenceInvalidation(): ExamsProgress{
        if(theoreticalExamState != DONE){
            throw IllegalStateException("Theoretical exam must be passed before invalidating provisional licence")
        }
        return when(practicalExamState){
            NOT_DONE ->
                setNewPracticalExamState(FIRST_PROVISIONAL_LICENCE_INVALID)
            FIRST_PROVISIONAL_LICENCE_INVALID -> setNewPracticalExamState(SECOND_PROVISIONAL_LICENCE_INVALID)
            else -> throw IllegalStateException("The provisional licence can not be invalidated tree or more times")
        }
    }

    fun registerPracticalExamPassed(): ExamsProgress{
        if(theoreticalExamState != DONE){
            throw IllegalStateException("Theoretical exam must be passed before practical exam")
        }
        return when(practicalExamState){
            NOT_DONE -> setNewPracticalExamState(PASSED)
            FIRST_PROVISIONAL_LICENCE_INVALID -> setNewPracticalExamState(PASSED)
            else -> throw IllegalStateException("Exam can not be passed if the current state is $practicalExamState")
        }
    }

    fun registerTheoreticalExamPassed(): ExamsProgress{
        return when(theoreticalExamState){
            DONE -> throw IllegalStateException("Theoretical exam is already passed")
            else -> this.copy(theoreticalExamState = DONE)
        }
    }

    private fun setNewPracticalExamState(newState: PracticalExamState): ExamsProgress{
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
            NOT_DONE -> newState == FIRST_PROVISIONAL_LICENCE_INVALID || newState == PASSED
            FIRST_PROVISIONAL_LICENCE_INVALID -> newState == SECOND_PROVISIONAL_LICENCE_INVALID || newState == PASSED
            else -> false
        }
    }
}