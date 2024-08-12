package com.smattme.springboot.hmacsignature.filters;

import com.smattme.springboot.hmacsignature.config.CachingHttpRequestWrapper;
import com.smattme.springboot.hmacsignature.config.HmacSignatureFilterConfig;
import com.smattme.springboot.hmacsignature.helpers.HmacHelper;
import com.smattme.springboot.hmacsignature.repositories.UserSecretKeyRepository;
import io.vavr.control.Try;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@Slf4j
public class HmacSignatureFilter extends OncePerRequestFilter {

    private final UserSecretKeyRepository userSecretKeyRepository;
    private final HmacSignatureFilterConfig config;

    public HmacSignatureFilter(UserSecretKeyRepository userSecretKeyRepository,
            HmacSignatureFilterConfig config) {
        this.userSecretKeyRepository = userSecretKeyRepository;
        this.config = config;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request)
            throws ServletException {
        var path = request.getRequestURI();
        return config.getPathPrefix()
                .stream()
                .noneMatch(path::startsWith);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        var clientId = request.getHeader("X-ClientId");
        var clientSignature = request.getHeader("X-Signature");
        long clientTimestamp = Objects.nonNull(request.getHeader("X-TimestampMillis"))
                               ? Long.parseLong(request.getHeader("X-TimestampMillis"))
                               : 0;

        var userSecretKey = userSecretKeyRepository.findFirstByClientId(clientId);
        if (Objects.isNull(userSecretKey)) {
            var errMsg = "Invalid Client Identifier";
            response.sendError(HttpStatus.UNAUTHORIZED.value(), errMsg);
            return;
        }

        long timeDiff = System.currentTimeMillis() - clientTimestamp;
        if (timeDiff > config.getAllowedTimeDiffInMillis()) {
            var errMsg = "Invalid Client Timestamp";
            response.sendError(HttpStatus.UNAUTHORIZED.value(), errMsg);
            return;
        }

        var cachingRequestWrapper = new CachingHttpRequestWrapper(request);

        var payload = cachingRequestWrapper.getRequestBodyString()
                + clientTimestamp;

        //generate our own signature
        var hmacSignatureEither = Try.of(() -> HmacHelper.generateHMACSignature(
                payload,
                userSecretKey.getHmacSecretKey())
        ).toEither();

        if (hmacSignatureEither.isLeft()) {
            var throwable = hmacSignatureEither.getLeft();
            logger.error("Error generating Signature", throwable);
            var clientErrMsg = "Invalid Request Signature";
            response.sendError(HttpStatus.UNAUTHORIZED.value(), clientErrMsg);
            return;
        }

        var serverSignature = hmacSignatureEither.get();
        if (!serverSignature.equals(clientSignature)) {
            var errMsg = "Invalid Request Signature";
            response.sendError(HttpStatus.UNAUTHORIZED.value(), errMsg);
            return;
        }


        //signature checks out, proceed to process request
        filterChain.doFilter(cachingRequestWrapper, response);

    }
}
