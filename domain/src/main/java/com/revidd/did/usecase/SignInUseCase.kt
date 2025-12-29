package com.revidd.did.usecase

import com.revidd.did.model.SignInData

interface SignInUseCase  : SuspendingUseCase<SignInData,Result<Boolean>>