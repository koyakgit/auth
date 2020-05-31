package com.example.demo.utils;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

/**
 * 認証トークン構築クラス
 * @see https://qiita.com/rubytomato@github/items/eb595303430b35f4773d
 */
public class TokenBuilder {
    /**
     * 有効期間（ms）
     */
    private static final Long EXPIRATION_TIME = 1000L * 60L * 10L;

    /**
     * 認証トークンの構築
     * @return トークン
     */
    public static String build() {
        String secretKey = "secret";
        Date issuedAt = new Date(); 
        Date notBefore = new Date(issuedAt.getTime());
        Date expiresAt = new Date(issuedAt.getTime() + EXPIRATION_TIME);

        final Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
            // registered claims
            //.withJWTId("jwtId")        //"jti" : JWT ID
            //.withAudience("audience")  //"aud" : Audience
            //.withIssuer("issuer")      //"iss" : Issuer
            .withSubject("test")         //"sub" : Subject
            .withIssuedAt(issuedAt)      //"iat" : Issued At
            .withNotBefore(notBefore)    //"nbf" : Not Before
            .withExpiresAt(expiresAt)    //"exp" : Expiration Time
            //private claims
            .withClaim("X-AUTHORITIES", "aaa")
            .withClaim("X-USERNAME", "bbb")
            .sign(algorithm);
    }
}
