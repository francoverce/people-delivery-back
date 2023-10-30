package com.example.userint.services

import com.example.userint.repositories.sql.RegisterRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class JwtUserDetailsService : UserDetailsService {

    @Autowired
    lateinit var registerRepository: RegisterRepository

    override fun loadUserByUsername(username: String): UserDetails {

        val user = registerRepository.findByEmail(username)
            ?: throw UsernameNotFoundException("Usuario no encontrado: $username")

        return User.withUsername(user.email)
            .password(user.password)
            .roles("USER").build()
    }
}
