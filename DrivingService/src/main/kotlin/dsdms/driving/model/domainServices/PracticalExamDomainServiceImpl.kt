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

import dsdms.driving.database.Repository
import dsdms.driving.model.valueObjects.PracticalExamDay
import kotlinx.datetime.LocalDate

/**
 * @param repository that allows access to data storage
 */
class PracticalExamDomainServiceImpl(private val repository: Repository) : PracticalExamDomainService {
    override suspend fun registerPracticalExamDay(practicalExamDay: PracticalExamDay): DomainResponseStatus {
        if (!isPracticalExamDay(practicalExamDay.date)) {
            repository.registerPracticalExamDay(practicalExamDay)
            return DomainResponseStatus.OK
        }
        return DomainResponseStatus.ALREADY_DEFINED_AS_PRACTICAL_EXAM_DAY
    }

    override suspend fun getPracticalExamDays(): PracticalExamDaysResponse {
        return PracticalExamDaysResponse(DomainResponseStatus.OK, repository.getPracticalExamDays())
    }

    private suspend fun isPracticalExamDay(date: LocalDate): Boolean {
        return repository.getPracticalExamDays().map { elem -> elem.date }.contains(date)
    }
}
