package by.dmitry.domain.usecase

abstract class BaseUseCase<P, Q> {

    abstract suspend fun execute(request: P): Q
}