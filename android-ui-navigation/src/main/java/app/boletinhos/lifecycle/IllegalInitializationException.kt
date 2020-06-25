package app.boletinhos.lifecycle

internal object IllegalInitializationException : IllegalStateException(
    """
        LifecycleCoroutineScope failed to initialize because
        SimpleStack.Navigator isn't attached to the target Activity.
    """.trimIndent()
)
