package com.webapp.auth.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.webapp.auth.dto.request.AuthenticationRequest;
import com.webapp.auth.dto.request.IntrospectRequest;
import com.webapp.auth.dto.request.LogoutRequest;
import com.webapp.auth.dto.response.AuthenticationResponse;
import com.webapp.auth.dto.response.IntrospectResponse;
import com.webapp.auth.entity.InvalidatedToken;
import com.webapp.auth.repository.InvalidatedTokenRepository;
import com.webapp.common.exception.AppException;
import com.webapp.common.exception.ErrorCode;
import com.webapp.user.entity.User;
import com.webapp.user.repository.UserRepository;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
  UserRepository userRepo;
  InvalidatedTokenRepository invalidatedTokenRepo;

  @NonFinal
  @Value("${jwt.signedKey}")
  protected String SIGNER_KEY;

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
    var user =
        userRepo
            .findByUsername(request.getUsername())
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

    if (!authenticated) {
      throw new AppException(ErrorCode.UNAUTHENTICATED);
    }
    var token = generateToken(user);
    return AuthenticationResponse.builder().token(token).authenticated(true).build();
  }

  private String generateToken(User user) {
    JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
    JWTClaimsSet jwtClaimsSet =
        new JWTClaimsSet.Builder()
            .subject(user.getUsername())
            .issuer("cq")
            .issueTime(new Date())
            .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
            .jwtID(UUID.randomUUID().toString())
            .claim("scope", buildScope(user))
            .build();
    Payload payload = new Payload(jwtClaimsSet.toJSONObject());
    JWSObject jwsObject = new JWSObject(header, payload);
    try {
      jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
      return jwsObject.serialize();
    } catch (KeyLengthException e) {
      e.printStackTrace();
    } catch (JOSEException e) {
      e.printStackTrace();
    }
    return null;
  }

  public IntrospectResponse introspect(IntrospectRequest request)
      throws JOSEException, ParseException {
    var token = request.getToken();
    boolean isValid = true;
    try {
      verifyToken(token);
    } catch (AppException e) {
      isValid = false;
    }
    return IntrospectResponse.builder().valid(isValid).build();
  }

  public void logout(LogoutRequest request) throws JOSEException, ParseException {
    var signedToken = verifyToken(request.getToken());
    String jit = signedToken.getJWTClaimsSet().getJWTID();
    Date expiryTime = signedToken.getJWTClaimsSet().getExpirationTime();
    InvalidatedToken invalidatedToken =
        InvalidatedToken.builder().id(jit).expiryTime(expiryTime).build();
    invalidatedTokenRepo.save(invalidatedToken);
  }

  private SignedJWT verifyToken(String token) throws JOSEException, ParseException {
	JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
    SignedJWT signedJWT = SignedJWT.parse(token);
    Date expireTime = signedJWT.getJWTClaimsSet().getExpirationTime();
    var verified = signedJWT.verify(verifier);
    if (!(verified && expireTime.after(new Date())))
      throw new AppException(ErrorCode.UNAUTHENTICATED);
    if (invalidatedTokenRepo.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
      throw new AppException(ErrorCode.UNAUTHENTICATED);
    return signedJWT;
  }

  private String buildScope(User user) {
    StringJoiner stringJoiner = new StringJoiner(" ");
    if (!CollectionUtils.isEmpty(user.getRoles())) {
      user.getRoles()
          .forEach(
              role -> {
                stringJoiner.add("ROLE_" + role.getName());
                if (!CollectionUtils.isEmpty(role.getPermissions())) {
                  role.getPermissions()
                      .forEach(permission -> stringJoiner.add(permission.getName()));
                }
              });
    }
    return stringJoiner.toString();
  }
}
