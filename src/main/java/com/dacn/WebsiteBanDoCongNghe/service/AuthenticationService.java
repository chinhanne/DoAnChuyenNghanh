package com.dacn.WebsiteBanDoCongNghe.service;

import com.dacn.WebsiteBanDoCongNghe.dto.request.AuthenticationRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.request.IntrospectRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.request.LogoutRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.request.RefreshTokenRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.response.AuthenticationResponse;
import com.dacn.WebsiteBanDoCongNghe.dto.response.IntrospectResponse;
import com.dacn.WebsiteBanDoCongNghe.entity.InvalidatedToken;
import com.dacn.WebsiteBanDoCongNghe.entity.User;
import com.dacn.WebsiteBanDoCongNghe.exception.AppException;
import com.dacn.WebsiteBanDoCongNghe.exception.ErrorCode;
import com.dacn.WebsiteBanDoCongNghe.reponsitory.InvalidatedTokenReponsitory;
import com.dacn.WebsiteBanDoCongNghe.reponsitory.UserReponsitory;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AuthenticationService {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);
    UserReponsitory userReponsitory;
//    PasswordEncoder passwordEncoder;
    InvalidatedTokenReponsitory invalidatedTokenReponsitory;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;

//    Xac thuc nguoi dung
    public AuthenticationResponse authenticate(AuthenticationRequest request){
//        Kiem tra xac thuc nguoi dung
        var user = userReponsitory.findByUsername(request.getUsername()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if(!authenticated){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        var token = generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(authenticated)
                .build();

    }


//    Tao token
    private String generateToken(User user){
//        Header
       JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

//       Set claim
       JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
               .subject(user.getUsername()) // đại dien cho user dang dang nhap
               .issuer("21b4.com") // nguoi phat hanh
               .issueTime(new Date()) // time tao
               .claim("gender",user.getGender())
               .claim("address", user.getAddress())
               .claim("numberPhone", user.getNumberPhone())
               .expirationTime(new Date(Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli())) // Thoi gian het han
               .jwtID(UUID.randomUUID().toString())
               .claim("scope",buildScope(user))
               .build();

//      Payload
       Payload payload = new Payload(jwtClaimsSet.toJSONObject());

       JWSObject jwsObject = new JWSObject(jwsHeader,payload);

       try{
//           Tạo chữ ký bí mat và ký vào đối tượng jwsObject bằng khóa bí mật và thuật toán MAC
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
       } catch (JOSEException e) {
           throw new RuntimeException(e);
       }
    }


//    Xac minh token
    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();

        boolean isValue = true;
        try{
            verifyToken(token,false);
        }catch(AppException e) {
            isValue = false;
        }
        return IntrospectResponse.builder()
                .valid(isValue)
                .build();
    }


//    Kiểm tra token có được ký đúng và còn hạn không
    private SignedJWT verifyToken(String token, boolean isRefresh) throws ParseException, JOSEException {
//        Tạo 1 đối tượng JWSVerifier để kt chữ ký với trình xác minh HMAC
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

//        Chuyen token thanh doi tuong SignedJWT
        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expirytime = (isRefresh)
        ? new Date(signedJWT.getJWTClaimsSet().getIssueTime().toInstant().plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS).toEpochMilli())
        : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);
        if(!(verified && expirytime.after(new Date())))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

//        Kiểm tra nếu id của token có trong bảng invalidatedToken thì xuất lỗi
        if(invalidatedTokenReponsitory.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        return signedJWT;
    }

//    Logout token
    public void logoutToken(LogoutRequest request) throws ParseException, JOSEException {
        try{
            var signToken = verifyToken(request.getToken(), true);

            String jit = signToken.getJWTClaimsSet().getJWTID();
            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

            InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                    .id(jit)
                    .expiryTime(expiryTime)
                    .build();

            invalidatedTokenReponsitory.save(invalidatedToken);
        }catch(AppException e){
            log.info("Token hết hạn");
        }

    }

//    refresh token
    public AuthenticationResponse reFreshToken(RefreshTokenRequest request) throws ParseException, JOSEException {
//        B1: xác minh token
        var signJwt = verifyToken(request.getToken(), true);

//        B2: lấy id và thời hạn để logout refresh cũ đi
        var jit = signJwt.getJWTClaimsSet().getJWTID();
        var expiryTime = signJwt.getJWTClaimsSet().getExpirationTime();

//        lưu vào db của logout
        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jit)
                .expiryTime(expiryTime)
                .build();

        invalidatedTokenReponsitory.save(invalidatedToken);

//        B3: tạo mới token
        var username = signJwt.getJWTClaimsSet().getSubject();
        var user = userReponsitory.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));

        var token = generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }


//    build claim có scope bằng giá trị của role theo từng user
    private String buildScope(User user){
        StringJoiner stringJoiner = new StringJoiner(" ");
        if(!CollectionUtils.isEmpty(user.getRoles())){
            user.getRoles().forEach(role -> stringJoiner.add(role.getName()));
        }
        return stringJoiner.toString();
    }

}
