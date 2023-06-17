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

package dsdms.driving.model.domainServices

import dsdms.driving.model.valueObjects.PracticalExamDay

/**
 * @param status response of practical exam days request
 * @param result if there were no error contains a list of practical exam days
 */
data class PracticalExamDaysResponse(
    val status: DomainResponseStatus,
    val result: List<PracticalExamDay>? = null,
)

/**
 * Manage practical exam day logic.
 */
interface PracticalExamDomainService {
    /**
     * Set a day as a practical exam day.
     * @param practicalExamDay exam day details
     * @return
     * - ALREADY_DEFINED_AS_PRACTICAL_EXAM_DAY -> day is already set as practical exam day
     * - OK -> otherwise
     */
    suspend fun registerPracticalExamDay(practicalExamDay: PracticalExamDay): DomainResponseStatus

    /**
     * Get list of all practical exam days.
     * @return an object with domain status code that always OK and a list with practical exam days
     */
    suspend fun getPracticalExamDays(): PracticalExamDaysResponse
}
