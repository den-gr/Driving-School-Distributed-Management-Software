package dsdms.dossier.model.valueObjects

import dsdms.dossier.model.valueObjects.TheoreticalExamState.PASSED
import dsdms.dossier.model.valueObjects.TheoreticalExamState.TO_DO
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalStateException
import kotlin.test.assertEquals

class ExamsStatusTest {

    @Test
    fun checkDefaultState() {
        assertEquals(ExamsStatus().practicalExamState, PracticalExamState.TO_DO)
        assertEquals(ExamsStatus().theoreticalExamState, TO_DO)
    }

    @Test
    fun setCorrectStates() {
        assertDoesNotThrow {
            val tmp = ExamsStatus().registerTheoreticalExamPassed()
            assertEquals(tmp.theoreticalExamState, PASSED)
            tmp.registerPracticalExamPassed()
        }
        val intermediateState = ExamsStatus()
            .registerTheoreticalExamPassed()
            .registerProvisionalLicenceInvalidation()
        assertEquals(
            intermediateState.theoreticalExamState,
            TO_DO
        )
        assertDoesNotThrow {
            intermediateState.registerTheoreticalExamPassed().registerProvisionalLicenceInvalidation()
        }
        assertDoesNotThrow {
            intermediateState.registerTheoreticalExamPassed().registerPracticalExamPassed()
        }
    }

    @Test
    fun setIncorrectStates() {
        assertThrows<IllegalStateException> {
            ExamsStatus().registerPracticalExamPassed()
        }

        assertThrows<IllegalStateException> {
            ExamsStatus()
                .registerTheoreticalExamPassed()
                .registerProvisionalLicenceInvalidation()
                .registerPracticalExamPassed()
        }

        assertThrows<IllegalStateException> {
            var exams = ExamsStatus().registerTheoreticalExamPassed().registerProvisionalLicenceInvalidation()
            assertEquals(exams.practicalExamState, PracticalExamState.FIRST_PROVISIONAL_LICENCE_INVALID)
            exams = exams.registerTheoreticalExamPassed().registerProvisionalLicenceInvalidation()
            assertEquals(exams.practicalExamState, PracticalExamState.SECOND_PROVISIONAL_LICENCE_INVALID)
            exams.registerTheoreticalExamPassed().registerPracticalExamPassed()
        }
    }
}
