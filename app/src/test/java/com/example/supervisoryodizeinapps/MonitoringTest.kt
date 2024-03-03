package com.example.supervisoryodizeinapps
import com.example.supervisoryodizeinapps.core.viewModel.RegisterViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations


class MonitoringTest {
    private lateinit var viewModel: RegisterViewModel // Replace with the actual name of your ViewModel class

    @Before
    fun setup() {
//        MockitoAnnotations.initMocks(this)
//        viewModel = RegisterViewModel(/* your dependencies, if any */) // Initialize your ViewModel with the mocked dependencies
    }

    @Test
    fun `onEmailChanged should update UI state correctly`() = runTest {
        // Arrange
        val newValue = "test@example.com"

        // Act
        viewModel.onEmailChanged(newValue)

        // Assert
        val updatedState = viewModel.uiState.value
        assertEquals(newValue, updatedState?.email)
    }

    @Test
    fun `onEmailChanged should update isValidForm in UI state`() = runTest {
        // Arrange
        val newValue = "test@example.com"

        // Act
        viewModel.onEmailChanged(newValue)

        // Assert
        val updatedState = viewModel.uiState.value
        assertEquals(isValidForm(newValue), updatedState?.isValidForm)
    }

    private fun isValidForm(email: String): Boolean {
        // Implement your logic for checking if the form is valid based on the email
        // For example, you can use a regular expression or any other validation method
        return email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}




