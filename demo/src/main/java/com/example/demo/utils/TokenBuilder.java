package com.example.demo.utils;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

/**
 * <p>認証トークン構築クラス</p>
 * 発行トークンの内容確認は<a href="https://jwt.io/">こちら</a>で可能
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
            //.withAudience("audience")  //"aud" : Audience JWTの利用者
            //.withIssuer("issuer")      //"iss" : Issuer   JWTの発行者
            .withSubject("test")         //"sub" : Subject  JWTの主体. JWTの発行者のコンテキスト内でユニークまたはグローバルユニークな値
            .withIssuedAt(issuedAt)      //"iat" : Issued At JWTの発行時間
            .withNotBefore(notBefore)    //"nbf" : Not Before JWTの有効期間（開始）
            .withExpiresAt(expiresAt)    //"exp" : Expiration Time JWTの有効期間（終了）
            //private claims
            .withClaim("X-AUTHORITIES", "aaa")
            .withClaim("X-USERNAME", "bbb")
            .sign(algorithm);
    }

    /**
     * トークンの検証
     */
    public static void verify(String token) {
        String secretKey = "secret";
    
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();
    
        DecodedJWT jwt = verifier.verify(token);
    
        // registered claims
        String subject = jwt.getSubject();
        Date issuedAt = jwt.getIssuedAt();
        Date notBefore = jwt.getNotBefore();
        Date expiresAt = jwt.getExpiresAt();
        System.out.println("subject : [" + subject + "] issuedAt : [" + issuedAt.toString() + "] notBefore : [" + notBefore.toString() + "] expiresAt : [" + expiresAt.toString() + "]");
        // subject : [test] issuedAt : [Thu Apr 12 13:19:00 JST 2018] notBefore : [Thu Apr 12 13:19:00 JST 2018] expiresAt : [Thu Apr 12 13:29:00 JST 2018]
    
        // private claims
        String authorities = jwt.getClaim("X-AUTHORITIES").asString();
        String username = jwt.getClaim("X-USERNAME").asString();
        System.out.println("private claim  X-AUTHORITIES : [" + authorities + "] X-USERNAME : [" + username + "]");
        // private claim  X-AUTHORITIES : [aaa] X-USERNAME : [bbb]
    }
}
