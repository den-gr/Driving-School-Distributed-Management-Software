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

package dsdms.doctor.database

import com.mongodb.client.result.DeleteResult
import dsdms.doctor.database.utils.RepositoryResponseStatus
import dsdms.doctor.model.entities.DoctorSlot
import dsdms.doctor.model.valueObjects.DoctorResult
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import java.time.LocalDate

/**
 * @param doctorServiceDB connection with database
 */
class RepositoryImpl(doctorServiceDB: CoroutineDatabase) : Repository {
    private val doctorSlots = doctorServiceDB.getCollection<DoctorSlot>("DoctorSlot")
    private val doctorResults = doctorServiceDB.getCollection<DoctorResult>("DoctorResult")

    override suspend fun saveDoctorSlot(doctorSlot: DoctorSlot): String {
        return doctorSlot.apply { doctorSlots.insertOne(doctorSlot) }.date
    }

    override suspend fun getOccupiedDoctorSlots(date: String): List<DoctorSlot> {
        return doctorSlots.find(DoctorSlot::date eq date).toList()
    }

    override suspend fun deleteDoctorSlot(dossierId: String): RepositoryResponseStatus {
        return handleDeleteResult(doctorSlots.deleteOne(DoctorSlot::dossierId eq dossierId))
    }

    override suspend fun getAllDoctorSlots(dossierId: String, today: LocalDate?): List<DoctorSlot> {
        val result = doctorSlots.find(DoctorSlot::dossierId eq dossierId).toList()
        return if (today != null) {
            result.filter { el -> LocalDate.parse(el.date) >= today }
        } else {
            result
        }
    }

    override suspend fun registerDoctorResult(document: DoctorResult): RepositoryResponseStatus {
        return if (doctorResults.insertOne(document).wasAcknowledged()) {
            RepositoryResponseStatus.OK
        } else {
            RepositoryResponseStatus.INSERT_ERROR
        }
    }

    private fun handleDeleteResult(deleteResult: DeleteResult): RepositoryResponseStatus {
        return if (!deleteResult.wasAcknowledged() || deleteResult.deletedCount.toInt() == 0) {
            RepositoryResponseStatus.DELETE_ERROR
        } else {
            RepositoryResponseStatus.OK
        }
    }
}
