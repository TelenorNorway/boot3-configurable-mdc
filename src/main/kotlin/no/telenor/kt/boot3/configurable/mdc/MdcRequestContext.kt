package no.telenor.kt.boot3.configurable.mdc

import jakarta.servlet.AsyncContext
import jakarta.servlet.DispatcherType
import jakarta.servlet.RequestDispatcher
import jakarta.servlet.ServletConnection
import jakarta.servlet.ServletContext
import jakarta.servlet.ServletInputStream
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.http.HttpSession
import jakarta.servlet.http.HttpUpgradeHandler
import jakarta.servlet.http.Part
import no.telenor.kt.boot3.configurable.mdc.util.getField
import org.springframework.beans.factory.BeanFactory
import org.springframework.context.expression.BeanFactoryResolver
import org.springframework.expression.BeanResolver
import org.springframework.expression.ConstructorResolver
import org.springframework.expression.EvaluationContext
import org.springframework.expression.MethodResolver
import org.springframework.expression.OperatorOverloader
import org.springframework.expression.PropertyAccessor
import org.springframework.expression.TypeComparator
import org.springframework.expression.TypeConverter
import org.springframework.expression.TypeLocator
import org.springframework.expression.TypedValue
import org.springframework.expression.spel.support.StandardEvaluationContext
import java.io.BufferedReader
import java.security.Principal
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

class MdcRequestContext(
	beanFactory: BeanFactory,
	private val request: HttpServletRequest,
) : EvaluationContext, MutableMap<String, Any?>, HttpServletRequest {
	private val ctx = StandardEvaluationContext()
	private val map = getField<MutableMap<String, Any?>>("variables", ctx)

	init {
		val beanFactoryResolver = BeanFactoryResolver(beanFactory)
		ctx.beanResolver = beanFactoryResolver
	}

	// @formatter:off
	override fun getRootObject(): TypedValue = ctx.rootObject
	override fun getPropertyAccessors(): MutableList<PropertyAccessor> = ctx.propertyAccessors
	override fun getConstructorResolvers(): MutableList<ConstructorResolver> = ctx.constructorResolvers
	override fun getMethodResolvers(): MutableList<MethodResolver> = ctx.methodResolvers
	override fun getBeanResolver(): BeanResolver? = ctx.beanResolver
	override fun getTypeLocator(): TypeLocator = ctx.typeLocator
	override fun getTypeConverter(): TypeConverter = ctx.typeConverter
	override fun getTypeComparator(): TypeComparator = ctx.typeComparator
	override fun getOperatorOverloader(): OperatorOverloader = ctx.operatorOverloader
	override fun lookupVariable(name: String): Any? = ctx.lookupVariable(name)
	override fun setVariable(name: String, value: Any?) = ctx.setVariable(name, value)

	override val entries: MutableSet<MutableMap.MutableEntry<String, Any?>> get() = map.entries
	override val keys: MutableSet<String> get() = map.keys
	override val size: Int get() = map.size
	override val values: MutableCollection<Any?> get() = map.values
	override fun isEmpty(): Boolean = map.isEmpty()
	override fun get(key: String): Any? = map[key]
	override fun containsValue(value: Any?): Boolean = map.containsValue(value)
	override fun containsKey(key: String): Boolean = map.containsKey(key)
	override fun putAll(from: Map<out String, Any?>) = map.putAll(from)
	override fun put(key: String, value: Any?): Any? = map.put(key, value)
	override fun remove(key: String): Any? = map.remove(key)
	override fun clear() = map.clear()

	override fun getAttribute(name: String?): Any = request.getAttribute(name)
	override fun getAttributeNames(): Enumeration<String> = request.attributeNames
	override fun getCharacterEncoding(): String = request.characterEncoding
	override fun setCharacterEncoding(encoding: String?) { request.characterEncoding = encoding }
	override fun getContentLength(): Int = request.contentLength
	override fun getContentLengthLong(): Long = request.contentLengthLong
	override fun getContentType(): String = request.contentType
	override fun getInputStream(): ServletInputStream = request.inputStream
	override fun getParameter(name: String?): String = request.getParameter(name)
	override fun getParameterNames(): Enumeration<String> = request.parameterNames
	override fun getParameterValues(name: String?): Array<String> = request.getParameterValues(name)
	override fun getParameterMap(): MutableMap<String, Array<String>> = request.parameterMap
	override fun getProtocol(): String = request.protocol
	override fun getScheme(): String = request.scheme
	override fun getServerName(): String = request.serverName
	override fun getServerPort(): Int = request.serverPort
	override fun getReader(): BufferedReader = request.reader
	override fun getRemoteAddr(): String = request.remoteAddr
	override fun getRemoteHost(): String = request.remoteHost
	override fun setAttribute(name: String?, o: Any?) = request.setAttribute(name, o)
	override fun removeAttribute(name: String?) = request.removeAttribute(name)
	override fun getLocale(): Locale = request.locale
	override fun getLocales(): Enumeration<Locale> = request.locales
	override fun isSecure(): Boolean = request.isSecure
	override fun getRequestDispatcher(path: String?): RequestDispatcher = request.getRequestDispatcher(path)
	override fun getRemotePort(): Int = request.remotePort
	override fun getLocalName(): String = request.localName
	override fun getLocalAddr(): String = request.localAddr
	override fun getLocalPort(): Int = request.localPort
	override fun getServletContext(): ServletContext = request.servletContext
	override fun startAsync(): AsyncContext = request.startAsync()
	override fun startAsync(servletRequest: ServletRequest?, servletResponse: ServletResponse?): AsyncContext = request.startAsync(servletRequest, servletResponse)
	override fun isAsyncStarted(): Boolean = request.isAsyncStarted
	override fun isAsyncSupported(): Boolean = request.isAsyncSupported
	override fun getAsyncContext(): AsyncContext = request.asyncContext
	override fun getDispatcherType(): DispatcherType = request.dispatcherType
	override fun getRequestId(): String = request.requestId
	override fun getProtocolRequestId(): String = request.protocolRequestId
	override fun getServletConnection(): ServletConnection = request.servletConnection
	override fun getAuthType(): String = request.authType
	override fun getCookies(): Array<Cookie> = request.cookies
	override fun getDateHeader(name: String?): Long = request.getDateHeader(name)
	override fun getHeader(name: String?): String = request.getHeader(name)
	override fun getHeaders(name: String?): Enumeration<String> = request.getHeaders(name)
	override fun getHeaderNames(): Enumeration<String> = request.headerNames
	override fun getIntHeader(name: String?): Int = request.getIntHeader(name)
	override fun getMethod(): String = request.method
	override fun getPathInfo(): String = request.pathInfo
	override fun getPathTranslated(): String = request.pathTranslated
	override fun getContextPath(): String = request.contextPath
	override fun getQueryString(): String = request.queryString
	override fun getRemoteUser(): String = request.remoteUser
	override fun isUserInRole(role: String?): Boolean = request.isUserInRole(role)
	override fun getUserPrincipal(): Principal = request.userPrincipal
	override fun getRequestedSessionId(): String = request.requestedSessionId
	override fun getRequestURI(): String = request.requestURI
	override fun getRequestURL(): StringBuffer = request.requestURL
	override fun getServletPath(): String = request.servletPath
	override fun getSession(create: Boolean): HttpSession = request.getSession(create)
	override fun getSession(): HttpSession = request.session
	override fun changeSessionId(): String = request.changeSessionId()
	override fun isRequestedSessionIdValid(): Boolean = request.isRequestedSessionIdValid
	override fun isRequestedSessionIdFromCookie(): Boolean = request.isRequestedSessionIdFromCookie
	override fun isRequestedSessionIdFromURL(): Boolean = request.isRequestedSessionIdFromURL
	override fun authenticate(response: HttpServletResponse?): Boolean = request.authenticate(response)
	override fun login(username: String?, password: String?) = request.login(username, password)
	override fun logout() = request.logout()
	override fun getParts(): MutableCollection<Part> = request.parts
	override fun getPart(name: String?): Part = request.getPart(name)
	override fun <T : HttpUpgradeHandler?> upgrade(httpUpgradeHandlerClass: Class<T>?): T = request.upgrade(httpUpgradeHandlerClass)
	// @formatter:on

	@Suppress("UNCHECKED_CAST")
	fun <T> get(
		key: String,
		@Suppress("UNUSED_PARAMETER") clazz: Class<T>
	) = get(key) as T

	fun <T : Any> get(key: String, klass: KClass<T>) = get(key, klass.java)

	companion object {
		private val current = ConcurrentHashMap<HttpServletRequest, MdcRequestContext>()

		internal fun push(request: HttpServletRequest, beanFactory: BeanFactory) {
			val context = MdcRequestContext(beanFactory, request)
			context["request"] = request
			current[request] = context
		}

		internal fun pop(request: HttpServletRequest) {
			current.remove(request)
		}

		fun from(request: HttpServletRequest) = current[request]
	}
}
