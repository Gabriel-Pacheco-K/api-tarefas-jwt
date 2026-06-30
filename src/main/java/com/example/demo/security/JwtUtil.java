package com.example.demo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    // Chave secreta usada para assinar o token (em produção, isso vem de variável de ambiente)
    private final SecretKey chave = Keys.hmacShaKeyFor(
            "minha-chave-super-secreta-com-pelo-menos-32-caracteres".getBytes()
    );

    private final long EXPIRACAO = 1000 * 60 * 60 * 2; // 2 horas

    // Gera o token a partir do email do usuário
    public String gerarToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRACAO))
                .signWith(chave)
                .compact();
    }

    // Extrai o email contido no token
    public String extrairEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(chave)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Verifica se o token é válido (assinatura correta e não expirado)
    public boolean tokenValido(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(chave).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}