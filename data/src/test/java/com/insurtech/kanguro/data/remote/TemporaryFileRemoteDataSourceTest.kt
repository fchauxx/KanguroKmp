package com.insurtech.kanguro.data.remote

import com.insurtech.kanguro.data.remote.fakes.FakeUploadFileApiService
import com.insurtech.kanguro.data.source.TemporaryFileDataSource
import com.insurtech.kanguro.networking.api.KanguroTemporaryFileApiService
import com.insurtech.kanguro.networking.dto.TemporaryFileDto
import com.insurtech.kanguro.networking.error.RemoteServiceIntegrationError
import com.insurtech.kanguro.testing.BaseUnitTest
import com.insurtech.kanguro.testing.extensions.mapJsonToModel
import com.insurtech.kanguro.testing.files.FileSystemSupport
import com.insurtech.kanguro.testing.rest.RestInfrastructureRule
import com.insurtech.kanguro.testing.rest.wireRestApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.File
import java.io.FileOutputStream
import kotlin.test.assertEquals

class TemporaryFileRemoteDataSourceTest : BaseUnitTest() {

    @get:Rule
    val restInfrastructureRule = RestInfrastructureRule()

    private lateinit var temporaryFileRemoteDataSource: TemporaryFileDataSource

    private lateinit var fakeUploadFileApiService: FakeUploadFileApiService

    private lateinit var fileToUpload: File

    @Before
    fun setUp() {
        val kanguroTemporaryFileApiService = restInfrastructureRule.server
            .wireRestApi<KanguroTemporaryFileApiService>()

        val fakeUploadFileApiService = FakeUploadFileApiService()

        temporaryFileRemoteDataSource =
            TemporaryFileRemoteDataSource(
                kanguroTemporaryFileApiService = kanguroTemporaryFileApiService,
                uploadFileApiService = fakeUploadFileApiService
            )

        fileToUpload = createRandomFile()
    }

    @After
    fun tearDown() {
        fileToUpload.delete()
    }

    @Test
    fun `Post temporary file, when it is requested to post temporary file and there is no issue, then return the file id`() =
        runTest {
            // ARRANGE
            val assetFileName = "200_get_get_temporary_file_successfully.json"

            restInfrastructureRule.restScenario(
                statusCode = 200,
                response = FileSystemSupport.loadFile(assetFileName)
            )

            val expectedResponse = assetFileName.mapJsonToModel<TemporaryFileDto>()

            // ACT
            val response = temporaryFileRemoteDataSource.postTemporaryFile(fileToUpload)

            // ASSERT
            assertEquals(
                expected = expectedResponse,
                actual = response
            )
        }

    @Test
    fun `Post temporary file, when it is requested to post temporary file and there is some issue incoming from client, then return client system error`() =
        runTest {
            // ARRANGE
            restInfrastructureRule.restScenario(
                statusCode = 400
            )

            // ACT
            val result = kotlin.runCatching {
                temporaryFileRemoteDataSource.postTemporaryFile(fileToUpload)
            }

            // ASSERT
            val unwrappedError = unwrapCaughtError(result)

            assertEquals(
                expected = RemoteServiceIntegrationError.ClientOrigin,
                actual = unwrappedError
            )
        }

    @Test
    fun `Post temporary file, when it is requested to post temporary file and there is some issue incoming from server, then return remote system error`() =
        runTest {
            // ARRANGE
            restInfrastructureRule.restScenario(
                statusCode = 500
            )

            // ACT
            val result = kotlin.runCatching {
                temporaryFileRemoteDataSource.postTemporaryFile(fileToUpload)
            }

            // ASSERT
            val unwrappedError = unwrapCaughtError(result)

            assertEquals(
                expected = RemoteServiceIntegrationError.RemoteSystem,
                actual = unwrappedError
            )
        }

    private fun createRandomFile(): File {
        val fileName = "testFile"
        val fileContent = "This is a test file for Retrofit Upload."

        val tempFile = File.createTempFile(fileName, ".txt")

        val fileOutputStream = FileOutputStream(tempFile)
        fileOutputStream.write(fileContent.toByteArray())
        fileOutputStream.flush()
        fileOutputStream.close()

        return tempFile
    }
}
