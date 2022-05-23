package by.dmitry.domain.usecase

abstract class CompletableUseCase<P> {

    abstract suspend fun execute(request: P)
}