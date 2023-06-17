/*******************************************************************************
 * MIT License
 *
 * Copyright 2023 DSDMS
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE
 ******************************************************************************/

package dsdms.dossier.model.valueObjects
import dsdms.dossier.model.valueObjects.PracticalExamState.FIRST_PROVISIONAL_LICENCE_INVALID
import dsdms.dossier.model.valueObjects.PracticalExamState.SECOND_PROVISIONAL_LICENCE_INVALID
import dsdms.dossier.model.valueObjects.TheoreticalExamState.PASSED
import dsdms.dossier.model.valueObjects.TheoreticalExamState.TO_DO
import kotlinx.serialization.Serializable

/**
 * Global state of important events related to practical exam and provisional license invalidation.
 */
@Serializable
enum class PracticalExamState {
    TO_DO, FIRST_PROVISIONAL_LICENCE_INVALID, SECOND_PROVISIONAL_LICENCE_INVALID, PASSED
}

/**
 * Theoretical exam state of the dossier.
 */
@Serializable
enum class TheoreticalExamState {
    TO_DO, PASSED
}

/**
 * Manage exam states.
 * @param theoreticalExamState of dossier
 * @param practicalExamState of dossier
 */
@Serializable
data class ExamsStatus(
    val theoreticalExamState: TheoreticalExamState = TO_DO,
    val practicalExamState: PracticalExamState = PracticalExamState.TO_DO,
) {

    /**
     * Elaborate the case when provisional license was invalidated.
     * @return object in updated state
     * @throws IllegalStateException if practical exam is already passed
     * or provisional license has already been invalidated twice
     */
    fun registerProvisionalLicenceInvalidation(): ExamsStatus {
        if (theoreticalExamState != PASSED) {
            error("Theoretical exam must be passed before invalidating provisional licence")
        }
        return when (practicalExamState) {
            PracticalExamState.TO_DO ->
                setNewPracticalExamState(FIRST_PROVISIONAL_LICENCE_INVALID)
            FIRST_PROVISIONAL_LICENCE_INVALID -> setNewPracticalExamState(SECOND_PROVISIONAL_LICENCE_INVALID)
            else -> error("The provisional licence can not be invalidated tree or more times")
        }
    }

    /**
     * Register practical exam success.
     * @return object in updated state
     * @throws IllegalStateException if from current state the exam can not be passed
     */
    fun registerPracticalExamPassed(): ExamsStatus {
        if (theoreticalExamState != PASSED) {
            error("Theoretical exam must be passed before practical exam")
        }
        return when (practicalExamState) {
            PracticalExamState.TO_DO -> setNewPracticalExamState(PracticalExamState.PASSED)
            FIRST_PROVISIONAL_LICENCE_INVALID -> setNewPracticalExamState(PracticalExamState.PASSED)
            else -> error("Exam can not be passed if the current state is $practicalExamState")
        }
    }

    /**
     * Register theoretical exam success.
     * @return object in updated state
     */
    fun registerTheoreticalExamPassed(): ExamsStatus {
        return when (theoreticalExamState) {
            PASSED -> error("Theoretical exam is already passed")
            else -> this.copy(theoreticalExamState = PASSED)
        }
    }

    private fun setNewPracticalExamState(newState: PracticalExamState): ExamsStatus {
        if (isStateOrderConsented(newState).not()) {
            error("The state $newState can not be after $practicalExamState")
        }
        return when (newState) {
            FIRST_PROVISIONAL_LICENCE_INVALID -> this.copy(practicalExamState = newState, theoreticalExamState = TO_DO)
            else -> this.copy(practicalExamState = newState)
        }
    }

    private fun isStateOrderConsented(newState: PracticalExamState): Boolean {
        return when (practicalExamState) {
            PracticalExamState.TO_DO ->
                newState == FIRST_PROVISIONAL_LICENCE_INVALID ||
                    newState == PracticalExamState.PASSED
            FIRST_PROVISIONAL_LICENCE_INVALID ->
                newState == SECOND_PROVISIONAL_LICENCE_INVALID ||
                    newState == PracticalExamState.PASSED
            else -> false
        }
    }
}
