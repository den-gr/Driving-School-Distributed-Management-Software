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

package dsdms.exam.model.entities.theoreticalExam

import kotlinx.serialization.Serializable

/**
 * Represents a theoretical exam appeal.
 * @param date of the exam appeal
 * @param numberOfPlaces how many subscribers can be register in the appeal
 * @param registeredDossiers -> list of registered dossiers into this exam, set by default to empty list
 * @param initTime -> set by default to 10:00
 * @param finishTime -> set by default to 13:00
 */
@Serializable
data class TheoreticalExamAppeal(
    val date: String,
    val numberOfPlaces: Int,
    val registeredDossiers: List<String> = listOf(),
    val initTime: String = "10:00",
    val finishTime: String = "13:00",
)
