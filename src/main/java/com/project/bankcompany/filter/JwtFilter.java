package com.project.bankcompany.filter;


import com.project.bankcompany.dto.UserDto;
import com.project.bankcompany.service.JWTService;
import com.project.bankcompany.service.UserService;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter(filterName = "jwtFilter1", urlPatterns = {"/*"}, dispatcherTypes = {DispatcherType.REQUEST})
public class JwtFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    @Qualifier("JWTServiceImpl")
    private JWTService jwtService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    @Qualifier("UserServiceImpl")
    private UserService userService;

//    private String AUTH_URI = "/auth";
//
//    private String AUTH_URI_EXTERNAL = "/demo2023/auth";
    @Value("${AUTH_URI}")
    private String AUTH_URI;

    @Value("${EXTERNAL_AUTH_URI}")
    private String AUTH_URI_EXTERNAL;

    @Override
    public void initFilterBean() throws ServletException{
//        WebApplicationContext webApplicationContext=
//                WebApplicationContextUtils.getWebApplicationContext(getServletContext());
//        //reference to bean from app context
//        jwtService = webApplicationContext.getBean(JWTServiceImpl.class);
//        userService = webApplicationContext.getBean(UserService.class);

        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, getServletContext());

        //do something with your bean
        //propertyValue = yourBeanToInject.getValue("propertyName");
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        int statusCode = authorization(request);
        logger.info("statusCode ========================== {}", statusCode);
        if(statusCode == HttpServletResponse.SC_ACCEPTED){
            filterChain.doFilter(request,response);
        }else{
            response.sendError(statusCode);
        }
    }

    private int authorization(HttpServletRequest request) {
        int statusCode = HttpServletResponse.SC_UNAUTHORIZED;

        String incomingUri = request.getRequestURI();
        logger.info("============, within authorization(...), incomingUri = {}", incomingUri);
        if(incomingUriRequestForAuth(incomingUri)){
            return HttpServletResponse.SC_ACCEPTED;
        }

        try{
            String wholeTokenString = request.getHeader("Authorization").replaceAll("^(.*?) ", "");
            if(retrievedWholeTokenExist(wholeTokenString)){
                String token = wholeTokenString.trim();
                logger.info("=======, within authorization, token = {}",token);
                if(token == null || token.trim().isEmpty()){
                    return HttpServletResponse.SC_UNAUTHORIZED;
                }

//                //validate token
//                boolean isTokenValid = jwtService.validateAccessToken(token);
//                if(isTokenValid){
//                    statusCode = HttpServletResponse.SC_ACCEPTED;
//                }

                //parse the token and verify user info
                Claims claims = jwtService.decryptJwtToken(token);
                if(!isClaimsValid(claims))
                    return HttpServletResponse.SC_UNAUTHORIZED;
                logger.info("======, within authorization(...), parsed claims = {}",claims);

                //May not be necessary
                if(!isUserRetrievedByUserIdValid(claims.getId()))
                    return HttpServletResponse.SC_UNAUTHORIZED;

                String httpMethodValue = request.getMethod();
                boolean isRequestUriAllowedToBeAccessed = checkRequestUriForAuthorization(claims, httpMethodValue, incomingUri);
                if (isRequestUriAllowedToBeAccessed){
                    return HttpServletResponse.SC_ACCEPTED;
                }
            }
        }catch(Exception e){
            logger.error("Exception is thrown then parsing JWT token, error = {}",e.getMessage());
        }
        return statusCode;
    }

    private boolean checkRequestUriForAuthorization(Claims claims, String httpMethodValue, String requestUri) {
        boolean isAuthorized = false;
        String allowedResources = findAllowedResourcesUsingHttpMethodValueWithClaims(claims,httpMethodValue);
        String[] allowedResourcesArray = allowedResources.split(",");
        for (String eachAllowedResources:allowedResourcesArray){
            logger.info("======, requestUri = {}, eachAllowedResources = {}",requestUri,eachAllowedResources);
            if(requestUri.trim().toLowerCase().startsWith(eachAllowedResources.trim().toLowerCase())){
                isAuthorized = true;
                break;
            }
        }
        return isAuthorized;
    }


    private String findAllowedResourcesUsingHttpMethodValueWithClaims(Claims claims, String httpMethodValue) {
        String allowedResources = "";
        switch (httpMethodValue){
            case "GET" :
                allowedResources = (String)claims.get("allowedReadResources");
                break;
            case "POST" :
                allowedResources = (String)claims.get("allowedCreateResources");
                break;
            case "PUT" :
                allowedResources = (String)claims.get("allowedUpdateResources");
                break;
            case "PATCH" :
                allowedResources = (String)claims.get("allowedUpdateResources");
                break;
            case "DELETE" :
                allowedResources = (String)claims.get("allowedDeleteResources");
                break;
        }
        return allowedResources;
    }

    private boolean isUserRetrievedByUserIdValid(String id) {
        boolean isUserRetrievedByUserIdValid = false;
        if (id!=null){
            UserDto userDto = userService.getUserById(Long.valueOf(id));
            if (userDto!=null){
                isUserRetrievedByUserIdValid = true;
                logger.info("======, Now using userID ={}, userDto ={}",id,userDto);
            }
        }
        return isUserRetrievedByUserIdValid;
    }

    private boolean isClaimsValid(Claims claims) {
        boolean isClaimsValid = true;
        if (claims == null || claims.isEmpty())
            return false;
        return isClaimsValid;
    }

    private boolean incomingUriRequestForAuth(String incomingUri) {
        boolean isUriRequestForAuth = false;
        if(incomingUri.equalsIgnoreCase(AUTH_URI)||incomingUri.equalsIgnoreCase(AUTH_URI_EXTERNAL)){
            isUriRequestForAuth = true;
        }
        return isUriRequestForAuth;
    }

    private boolean retrievedWholeTokenExist(String wholeTokenString) {
        boolean isStringExist = false;
        if(wholeTokenString != null && !wholeTokenString.trim().isEmpty()) {
            isStringExist = true;
        }
        return isStringExist;
    }

}
