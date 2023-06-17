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

package dsdms.exam.model.valueObjects

import dsdms.exam.model.entities.ProvisionalLicense
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

/**
 * Holder class for provisional license.
 * @param provisionalLicense
 * @param practicalExamAttempts -> set to 0 by default, must be minor than MAX_PRACTICAL_EXAM_ATTEMPTS
 */
@Serializable
data class ProvisionalLicenseHolder(val provisionalLicense: ProvisionalLicense, val practicalExamAttempts: Int = 0) {
    companion object {
        private const val MAX_PRACTICAL_EXAM_ATTEMPTS = 3
    }

    init {
        checkPracticalExamAttempts()
    }

    /**
     * Increment the number of practical exam failures.
     * @return new ProvisionalLicenseHolder with incremented number of failures
     * @throws IllegalStateException if there are already max number of practical exam attempts
     */
    fun registerPracticalExamFailure(): ProvisionalLicenseHolder {
        return this.copy(practicalExamAttempts = practicalExamAttempts + 1)
    }

    /**
     * Is this provisional license valid in a particular day.
     * @param date
     * @return true if provisional license is valid in indicated date
     */
    fun isValidOn(date: LocalDate): Boolean {
        return date in provisionalLicense.startValidity..provisionalLicense.endValidity
    }

    /**
     * @return true if a subscriber with given provisional license has reached max practical exam attempts.
     */
    fun hasMaxAttempts(): Boolean {
        return practicalExamAttempts == MAX_PRACTICAL_EXAM_ATTEMPTS
    }

    private fun checkPracticalExamAttempts() {
        if (practicalExamAttempts < 0 || practicalExamAttempts > MAX_PRACTICAL_EXAM_ATTEMPTS) {
            error("Illegal number of practical exam attempts")
        }
    }
}
