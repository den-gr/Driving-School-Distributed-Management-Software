package dsdms.dossier.model.valueObjects

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.assertEquals
import dsdms.dossier.model.valueObjects.PracticalExamState.*
import dsdms.dossier.model.valueObjects.TheoreticalExamState.DONE
import dsdms.dossier.model.valueObjects.TheoreticalExamState.TO_DO
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalStateException

class ExamsProgressTest {

    @Test
    fun checkDefaultState(){
        assertEquals(ExamsProgress().practicalExamState, NOT_DONE)
        assertEquals(ExamsProgress().theoreticalExamState, TO_DO)
    }

    @Test
    fun setCorrectStates(){
        assertDoesNotThrow {
            val tmp =  ExamsProgress().registerTheoreticalExamPassed()
            assertEquals(tmp.theoreticalExamState, DONE)
            tmp.registerPracticalExamPassed()
        }
        val intermediateState = ExamsProgress()
            .registerTheoreticalExamPassed()
            .registerProvisionalLicenceInvalidation()
        assertEquals(intermediateState.theoreticalExamState, TO_DO)
        assertDoesNotThrow {
            intermediateState.registerTheoreticalExamPassed().registerProvisionalLicenceInvalidation()
        }
        assertDoesNotThrow {
            intermediateState.registerTheoreticalExamPassed().registerPracticalExamPassed()
        }
    }

    @Test
    fun setIncorrectStates(){
        assertThrows<IllegalStateException> {
            ExamsProgress().registerPracticalExamPassed()
        }

        assertThrows<IllegalStateException> {
            ExamsProgress()
                .registerTheoreticalExamPassed()
                .registerProvisionalLicenceInvalidation()
                .registerPracticalExamPassed()
        }

        assertThrows<IllegalStateException> {
            var exams = ExamsProgress().registerTheoreticalExamPassed().registerProvisionalLicenceInvalidation()
            assertEquals(exams.practicalExamState,FIRST_PROVISIONAL_LICENCE_INVALID)
            exams = exams.registerTheoreticalExamPassed().registerProvisionalLicenceInvalidation()
            assertEquals(exams.practicalExamState, SECOND_PROVISIONAL_LICENCE_INVALID)
            exams.registerTheoreticalExamPassed().registerPracticalExamPassed()
        }
    }
}