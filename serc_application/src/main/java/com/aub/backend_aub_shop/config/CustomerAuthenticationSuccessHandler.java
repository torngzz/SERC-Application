// /*
//  * Copyright 2002-2016 the original author or authors.
//  *
//  * Licensed under the Apache License, Version 2.0 (the "License");
//  * you may not use this file except in compliance with the License.
//  * You may obtain a copy of the License at
//  *
//  *      https://www.apache.org/licenses/LICENSE-2.0
//  *
//  * Unless required by applicable law or agreed to in writing, software
//  * distributed under the License is distributed on an "AS IS" BASIS,
//  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  * See the License for the specific language governing permissions and
//  * limitations under the License.
//  */

// package com.aub.backend_aub_shop.config;

// import java.io.IOException;

// import org.springframework.security.core.Authentication;
// import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;

// /**
//  * Strategy used to handle a successful user authentication.
//  * <p>
//  * Implementations can do whatever they want but typical behaviour would be to control the
//  * navigation to the subsequent destination (using a redirect or a forward). For example,
//  * after a user has logged in by submitting a login form, the application needs to decide
//  * where they should be redirected to afterwards (see
//  * {@link AbstractAuthenticationProcessingFilter} and subclasses). Other logic may also be
//  * included if required.
//  *
//  * @author Luke Taylor
//  * @since 3.0
//  */
// public interface CustomerAuthenticationSuccessHandler extends AuthenticationSuccessHandler {

// 	/**
// 	 * Called when a user has been successfully authenticated.
// 	 * @param request the request which caused the successful authentication
// 	 * @param response the response
// 	 * @param chain the {@link FilterChain} which can be used to proceed other filters in
// 	 * the chain
// 	 * @param authentication the <tt>Authentication</tt> object which was created during
// 	 * the authentication process.
// 	 * @since 5.2.0
// 	 */
// 	default void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
// 			Authentication authentication) throws IOException, ServletException {
// 		onAuthenticationSuccess(request, response, authentication);
// 		chain.doFilter(request, response);
// 	}

// 	/**
// 	 * Called when a user has been successfully authenticated.
// 	 * @param request the request which caused the successful authentication
// 	 * @param response the response
// 	 * @param authentication the <tt>Authentication</tt> object which was created during
// 	 * the authentication process.
// 	 */
// 	void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
// 			Authentication authentication) throws IOException, ServletException;
		
// }
