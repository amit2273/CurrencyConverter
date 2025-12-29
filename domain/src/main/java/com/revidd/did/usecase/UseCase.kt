package com.revidd.did.usecase

interface UseCase<in InputT, out OutputT>{
    fun execute(inputT: InputT) : OutputT
}

interface SuspendingUseCase<in InputT, out OutputT>{
    suspend fun execute(inputT: InputT) : OutputT
}