package org.example.services.generic

import org.example.dto.account.AuthResponseDTO
import org.example.dto.account.LoginDTO
import org.example.dto.account.RegisterDTO

interface AccountService {
    fun login(request: LoginDTO): AuthResponseDTO
    fun register(request: RegisterDTO)
}
