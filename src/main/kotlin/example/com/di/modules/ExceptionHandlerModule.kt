package example.com.di.modules

import example.com.utils.exception.ExceptionHandler
import example.com.utils.exception.ExceptionHandlerImplementation
import org.koin.dsl.module

val exceptionHandlerModule = module {
    single<ExceptionHandler> { ExceptionHandlerImplementation() }
}