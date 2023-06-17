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

package dsdms.dossier.database
import dsdms.dossier.database.utils.RepositoryResponseStatus
import dsdms.dossier.model.entities.Dossier

/**
 * Layer between domain and data storage.
 */
interface Repository {

    /**
     * @param newDossier to be inserted
     * @return id of the created dossier (could be null in case of errors)
     */
    suspend fun createDossier(newDossier: Dossier): String?

    /**
     * @param id of a specific dossier
     * @return founded dossier (could be null if dossier not exists)
     */
    suspend fun readDossierFromId(id: String): Dossier?

    /**
     * @param cf: fiscal code to read a dossier from
     * @return the list of dossiers valid and invalid with that fiscal code
     */
    suspend fun readDossierFromCf(cf: String): List<Dossier>

    /**
     * @param dossier in its new state
     * @return Repository response status:
     *  - UPDATE_ERROR
     *  - OK
     */
    suspend fun updateDossier(dossier: Dossier): RepositoryResponseStatus

    /**
     * @param id of the dossier to be updated
     * @return Repository response status:
     *  - DELETE_ERROR
     *  - OK
     */
    suspend fun deleteDossier(id: String): RepositoryResponseStatus
}
