package com.insurtech.kanguro.testing

abstract class BaseUnitTest {

    protected fun unwrapCaughtError(result: Result<*>): Throwable =
        result.exceptionOrNull() ?: throw IllegalArgumentException("Not an error")
}
